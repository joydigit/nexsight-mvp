<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerPaymentForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderCustomerPaymentForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderCustomerPaymentForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户ID" v-bind="validateInfos.customerId" id="ElderCustomerPaymentForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住id" v-bind="validateInfos.checkinId" id="ElderCustomerPaymentForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" placeholder="请输入入住id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="账单ID" v-bind="validateInfos.billId" id="ElderCustomerPaymentForm-billId" name="billId">
								<a-input v-model:value="formData.billId" placeholder="请输入账单ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="收款类型名称" v-bind="validateInfos.paymentTypeName" id="ElderCustomerPaymentForm-paymentTypeName" name="paymentTypeName">
								<a-input v-model:value="formData.paymentTypeName" placeholder="请输入收款类型名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="收款类型编码" v-bind="validateInfos.paymentTypeCode" id="ElderCustomerPaymentForm-paymentTypeCode" name="paymentTypeCode">
								<a-input v-model:value="formData.paymentTypeCode" placeholder="请输入收款类型编码"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="金额（元），退款时为负数" v-bind="validateInfos.amount" id="ElderCustomerPaymentForm-amount" name="amount">
								<a-input-number v-model:value="formData.amount" placeholder="请输入金额（元），退款时为负数" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="支付方式名称" v-bind="validateInfos.paymentMethodName" id="ElderCustomerPaymentForm-paymentMethodName" name="paymentMethodName">
								<a-input v-model:value="formData.paymentMethodName" placeholder="请输入支付方式名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="支付方式编码" v-bind="validateInfos.paymentMethodCode" id="ElderCustomerPaymentForm-paymentMethodCode" name="paymentMethodCode">
								<a-input v-model:value="formData.paymentMethodCode" placeholder="请输入支付方式编码"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="第三方支付流水号（微信/支付宝订单号）" v-bind="validateInfos.transactionNo" id="ElderCustomerPaymentForm-transactionNo" name="transactionNo">
								<a-input v-model:value="formData.transactionNo" placeholder="请输入第三方支付流水号（微信/支付宝订单号）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="收款日期（业务日期）" v-bind="validateInfos.paymentDate" id="ElderCustomerPaymentForm-paymentDate" name="paymentDate">
								<a-date-picker placeholder="请选择收款日期（业务日期）"  v-model:value="formData.paymentDate" value-format="YYYY-MM-DD"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="凭证图片URL（收据拍照）" v-bind="validateInfos.voucherUrl" id="ElderCustomerPaymentForm-voucherUrl" name="voucherUrl">
								<a-input v-model:value="formData.voucherUrl" placeholder="请输入凭证图片URL（收据拍照）"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderCustomerPayment.api';
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
    billId: '',   
    paymentTypeName: '',   
    paymentTypeCode: '',   
    amount: undefined,
    paymentMethodName: '',   
    paymentMethodCode: '',   
    transactionNo: '',   
    paymentDate: '',   
    voucherUrl: '',   
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
    paymentTypeName: [{ required: true, message: '请输入收款类型名称!'},],
    paymentTypeCode: [{ required: true, message: '请输入收款类型编码!'},],
    amount: [{ required: true, message: '请输入金额（元），退款时为负数!'},],
    paymentMethodName: [{ required: true, message: '请输入支付方式名称!'},],
    paymentMethodCode: [{ required: true, message: '请输入支付方式编码!'},],
    paymentDate: [{ required: true, message: '请输入收款日期（业务日期）!'},],
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
