<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderRoomFeeConfigForm">
          <a-row>						
						<a-col :span="24">
							<a-form-item label="项目" v-bind="validateInfos.projectId" id="ElderRoomFeeConfigForm-projectId" name="projectId">
								<a-select
                  v-model:value="formData.projectId"
                  :options="projectList"
                  :fieldNames="{ label: 'projectName', value: 'id' }"
                  showSearch
                  placeholder="请选择所属项目"
                />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="房费类型" v-bind="validateInfos.itemType" id="ElderRoomFeeConfigForm-itemType" name="itemType">
								<JDictSelectTag type="select" v-model:value="formData.itemType" dictCode="room_fee_type" placeholder="请选择房费类型" @change="changeItemType"/>
							</a-form-item>
						</a-col>
						<a-col :span="24" v-if="formData.itemType == '1'">
							<a-form-item label="房间" v-bind="validateInfos.itemId" id="ElderRoomFeeConfigForm-itemId" name="itemId">
                <Cascader :value="formData.itemIdArr" :options="roomList"  placeholder="请选择" @change="changeItem"/>
							</a-form-item>
						</a-col>
            <a-col :span="24" v-if="formData.itemType == '2'">
							<a-form-item label="床位" v-bind="validateInfos.itemId" id="ElderRoomFeeConfigForm-itemId" name="itemId">
                <Cascader :value="formData.itemIdArr" :options="roomList"  placeholder="请选择" @change="changeItem"/>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="费用" v-bind="validateInfos.price" id="ElderRoomFeeConfigForm-price" name="price">
								<a-input-number v-model:value="formData.price" placeholder="请输入费用" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderRoomFeeConfigForm-remark" name="remark">
								<a-textarea v-model:value="formData.remark" placeholder="请输入备注"  allow-clear ></a-textarea>
							</a-form-item>
						</a-col>
          </a-row>
        </a-form>
      </template>
    </JFormContainer>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted,watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderRoomFeeConfig.api';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import { getProjectListAllM } from '../ElderProject.api'; 
  import {getRoomCascaderListMethod} from '../ElderRoom.api';
  import { Cascader } from 'ant-design-vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const projectList = ref([]);
  const roomList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: undefined,   
    itemId: undefined,   
    itemIdArr: undefined,  
    itemType: undefined,   
    price: undefined,
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
  watch(()=>{
    if (formData.itemId){
      formData.itemIdArr = formData.itemId.split(',');
      changeItemType(formData.itemType,formData.projectId)
    }
  })
  //表单验证
  const validatorRules = reactive({
    projectId: [{ required: true, message: '请选择项目!'},],
    itemId: [{ required: true, message: '请选择!'},],
    itemType: [{ required: true, message: '请选择房费类型!'},],
    price: [{ required: true, message: '请输入费用!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });

  async function changeItem(value){
    if (value){
      formData.itemId = value.join();
    } else {
      formData.itemId = '';
    }
  }

  async function changeItemType(value){
    if (formData.projectId) {
      roomList.value = await getRoomCascaderListMethod({type: value,projectId:formData.projectId})
    }
  }
  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      const tmpData = {};
      Object.keys(formData).forEach((key) => {
        if(record.hasOwnProperty(key)){
          tmpData[key] = record[key]
        }
      })
      //赋值
      Object.assign(formData, tmpData);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    try {
      // 触发表单验证
      await validate();
    } catch ({ errorFields }) {
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          formRef.value.scrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
        }
      }
      return Promise.reject(errorFields);
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in model) {
      // 更新个性化日期选择器的值
      model[data] = getDateByPicker(model[data], fieldPickers[data]);
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }
    }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    padding: 14px 20px;
  }
</style>
