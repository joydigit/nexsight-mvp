<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerCheckinForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderCustomerCheckinForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="所属项目ID" v-bind="validateInfos.projectId" id="ElderCustomerCheckinForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入所属项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="销售名称" v-bind="validateInfos.salesName" id="ElderCustomerCheckinForm-salesName" name="salesName">
								<a-input v-model:value="formData.salesName" placeholder="请输入销售名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="咨询接待id" v-bind="validateInfos.consultingId" id="ElderCustomerCheckinForm-consultingId" name="consultingId">
								<a-input v-model:value="formData.consultingId" placeholder="请输入咨询接待id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="销售id" v-bind="validateInfos.salesId" id="ElderCustomerCheckinForm-salesId" name="salesId">
								<a-input v-model:value="formData.salesId" placeholder="请输入销售id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住时间" v-bind="validateInfos.checkinTime" id="ElderCustomerCheckinForm-checkinTime" name="checkinTime">
								<a-date-picker placeholder="请选择入住时间"  v-model:value="formData.checkinTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="合同url" v-bind="validateInfos.contractFileUrl" id="ElderCustomerCheckinForm-contractFileUrl" name="contractFileUrl">
								<a-input v-model:value="formData.contractFileUrl" placeholder="请输入合同url"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="合同号" v-bind="validateInfos.contractNo" id="ElderCustomerCheckinForm-contractNo" name="contractNo">
								<a-input v-model:value="formData.contractNo" placeholder="请输入合同号"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="实际退住时间" v-bind="validateInfos.realCheckoutTime" id="ElderCustomerCheckinForm-realCheckoutTime" name="realCheckoutTime">
								<a-date-picker placeholder="请选择实际退住时间"  v-model:value="formData.realCheckoutTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="预计退住时间" v-bind="validateInfos.expectCheckoutTime" id="ElderCustomerCheckinForm-expectCheckoutTime" name="expectCheckoutTime">
								<a-date-picker placeholder="请选择预计退住时间"  v-model:value="formData.expectCheckoutTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住类型 1长住 2短住" v-bind="validateInfos.checkinType" id="ElderCustomerCheckinForm-checkinType" name="checkinType">
								<a-input v-model:value="formData.checkinType" placeholder="请输入入住类型 1长住 2短住"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="状态 1 入住 2 退住" v-bind="validateInfos.status" id="ElderCustomerCheckinForm-status" name="status">
								<a-input v-model:value="formData.status" placeholder="请输入状态 1 入住 2 退住"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户id" v-bind="validateInfos.customerId" id="ElderCustomerCheckinForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户id"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderCustomerCheckin.api';
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
    salesName: '',   
    consultingId: '',   
    salesId: '',   
    checkinTime: '',   
    contractFileUrl: '',   
    contractNo: '',   
    realCheckoutTime: '',   
    expectCheckoutTime: '',   
    checkinType: '',   
    status: '',   
    customerId: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    tenantId: [{ required: true, message: '请输入租户ID!'},],
    projectId: [{ required: true, message: '请输入所属项目ID!'},],
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
