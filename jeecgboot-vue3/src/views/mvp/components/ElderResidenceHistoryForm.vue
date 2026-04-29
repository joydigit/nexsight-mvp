<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderResidenceHistoryForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderResidenceHistoryForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderResidenceHistoryForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户ID" v-bind="validateInfos.customerId" id="ElderResidenceHistoryForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住id" v-bind="validateInfos.checkinId" id="ElderResidenceHistoryForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" placeholder="请输入入住id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="变更类型名称" v-bind="validateInfos.changeTypeName" id="ElderResidenceHistoryForm-changeTypeName" name="changeTypeName">
								<a-input v-model:value="formData.changeTypeName" placeholder="请输入变更类型名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="变更类型编码" v-bind="validateInfos.changeTypeCode" id="ElderResidenceHistoryForm-changeTypeCode" name="changeTypeCode">
								<a-input v-model:value="formData.changeTypeCode" placeholder="请输入变更类型编码"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="原房间ID（换房/退住时填充）" v-bind="validateInfos.oldRoomId" id="ElderResidenceHistoryForm-oldRoomId" name="oldRoomId">
								<a-input v-model:value="formData.oldRoomId" placeholder="请输入原房间ID（换房/退住时填充）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="原床位ID（换房/退住时填充）" v-bind="validateInfos.oldBedId" id="ElderResidenceHistoryForm-oldBedId" name="oldBedId">
								<a-input v-model:value="formData.oldBedId" placeholder="请输入原床位ID（换房/退住时填充）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="新房间ID（入住/换房时填充）" v-bind="validateInfos.newRoomId" id="ElderResidenceHistoryForm-newRoomId" name="newRoomId">
								<a-input v-model:value="formData.newRoomId" placeholder="请输入新房间ID（入住/换房时填充）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="新床位ID（入住/换房时填充）" v-bind="validateInfos.newBedId" id="ElderResidenceHistoryForm-newBedId" name="newBedId">
								<a-input v-model:value="formData.newBedId" placeholder="请输入新床位ID（入住/换房时填充）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="变更原因" v-bind="validateInfos.reason" id="ElderResidenceHistoryForm-reason" name="reason">
								<a-input v-model:value="formData.reason" placeholder="请输入变更原因"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
          </a-row>
        </a-form>
      </template>
    </JFormContainer>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderResidenceHistory.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
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
    tenantId: '',   
    projectId: '',   
    customerId: '',   
    checkinId: '',   
    changeTypeName: '',   
    changeTypeCode: '',   
    oldRoomId: '',   
    oldBedId: '',   
    newRoomId: '',   
    newBedId: '',   
    reason: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    tenantId: [{ required: true, message: '请输入租户ID!'},],
    projectId: [{ required: true, message: '请输入项目ID!'},],
    customerId: [{ required: true, message: '请输入客户ID!'},],
    changeTypeName: [{ required: true, message: '请输入变更类型名称!'},],
    changeTypeCode: [{ required: true, message: '请输入变更类型编码!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });

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
