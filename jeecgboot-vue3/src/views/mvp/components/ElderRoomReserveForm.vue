<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderRoomReserveForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="客户" v-bind="validateInfos.customerId" id="ElderRoomReserveForm-customerId" name="customerId">
								<a-select
                  v-model:value="formData.customerId"
                  :options="customerList"
                  :disabled="dealType == '1'"
                  :fieldNames="{ label: 'name', value: 'id' }"
                  showSearch
                  placeholder="请选择客户"
                />
							</a-form-item>
						</a-col>					
						<a-col :span="24">
							<a-form-item label="房间" v-bind="validateInfos.roomId" id="ElderRoomReserveForm-roomId" name="roomId">
                <a-select
                  v-model:value="formData.roomId"
                  :options="roomList"
                  :disabled="dealType == '2'"
                  :fieldNames="{ label: 'roomNo', value: 'roomId' }"
                  showSearch
                  placeholder="请选择房间"
                  @change="handleChangeRoomId"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="床位" v-bind="validateInfos.bedId" id="ElderRoomReserveForm-bedId" name="bedId">
								<a-select
                  v-model:value="formData.bedId"
                  :options="bedList"
                  :disabled="dealType == '2'"
                  :fieldNames="{ label: 'bedNo', value: 'id' }"
                  showSearch
                  placeholder="请选择床位"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="状态" v-bind="validateInfos.status" id="ElderRoomReserveForm-status" name="status">
								<JDictSelectTag type="select" v-model:value="formData.status" dictCode="reserve_status" placeholder="请选择状态" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderRoomReserveForm-remark" name="remark">
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
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderRoomReserve.api';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import {getBedListByRoomIdMethod} from './../ElderBed.api';
  import {getRoomListByProjectIdMethod} from './../ElderRoom.api';
  import {getCustomerListByProjectIdMethod} from './../ElderCustomer.api';
  const roomList = ref([]);
  const customerList = ref([]);
  const bedList = ref([]);
  const dealType = ref('');
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    customerId: undefined,   
    consultingId: '',   
    roomId: undefined,   
    bedId: undefined,   
    status: '1',   
    tenantId: undefined,
    projectId: '',   
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    roomId: [{ required: true, message: '请选择房间!'},],
    customerId: [{ required: true, message: '请选择客户!'},],
    status: [{ required: true, message: '请选择预定状态!'},],
    bedId: [{ required: true, message: '请选择床位!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });
  watch(() => formData.projectId, async (val) => {
    if (val) {
      // 查询客户，查询房间列表
      roomList.value = await getRoomListByProjectIdMethod({ projectId: val });
      customerList.value = await getCustomerListByProjectIdMethod({ projectId: val });
      // 如果已有 roomId，联动加载床位
      if (formData.roomId) {
        await handleChangeRoomId(formData.roomId);
      }
    } else {
      roomList.value = [];
      customerList.value = [];
      bedList.value = [];
    }
  });

  async function handleChangeRoomId(value) {
    if (value) {
      let beds = await getBedListByRoomIdMethod({ roomId: value });
      beds.forEach(item=>{
        item.disabled = item.status == '1';
      })
      bedList.value = beds;
    } else {
      bedList.value = [];
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
  function add(initData) {
    if (initData.customerId){
      dealType.value = '1';
    }
    if (initData.bedId){
      dealType.value = '2';
    }
    edit(initData);
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
