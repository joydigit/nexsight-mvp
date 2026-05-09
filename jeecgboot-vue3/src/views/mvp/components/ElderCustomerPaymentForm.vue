<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerPaymentForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="客户" v-bind="validateInfos.customerId" id="ElderCustomerPaymentForm-customerId" name="customerId">
								<a-select
                  v-model:value="formData.customerId"
                  :options="customerList"
                  :disabled="dealType == '1' || !!formData.id"
                  :fieldNames="{ label: 'name', value: 'id' }"
                  showSearch
                  placeholder="请选择客户"
                />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="交易类型" v-bind="validateInfos.transactionTypeCode" id="ElderCustomerPaymentForm-transactionTypeCode" name="transactionTypeCode">
								<JDictSelectTag type="select" v-model:value="formData.transactionTypeCode" dictCode="transaction_type_code" placeholder="请选择交易类型" @change="changetransactionType"/>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="账单" v-bind="validateInfos.billId" id="ElderCustomerPaymentForm-billId" name="billId" v-if="formData.transactionTypeCode == '4'" >
                <a-select
                  v-model:value="formData.billId"
                  :options="billList"
                  :fieldNames="{ label: 'billNo', value: 'id' }"
                  showSearch
                  placeholder="请选择账单"
                  @change="changeBill"
                />
							</a-form-item>
						</a-col>	
            <a-col :span="24">
							<a-form-item label="支付方式" v-bind="validateInfos.paymentMethodCode" id="ElderCustomerPaymentForm-paymentMethodCode" name="paymentMethodCode">
								<JDictSelectTag type="select" v-model:value="formData.paymentMethodCode" dictCode="payment_method" placeholder="请选择支付方式" @change="changePaymentMethodCode"/>
							</a-form-item>
						</a-col>			
            <a-col :span="24">
							<a-form-item label="账户" v-bind="validateInfos.accountId" id="ElderCustomerPaymentForm-accountId" name="accountId" v-if="formData.transactionTypeCode == '1' || formData.transactionTypeCode == '3' || formData.paymentMethodCode == '1' || formData.paymentMethodCode == '2'" >
								<a-select
                  v-model:value="formData.accountId"
                  :options="customerAccountList"
                  :fieldNames="{ label: 'accountTypeName', value: 'id' }"
                  showSearch
                  :disabled="formData.paymentMethodCode == '1' || formData.paymentMethodCode == '2'"
                  placeholder="请选择账户"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="金额" v-bind="validateInfos.amount" id="ElderCustomerPaymentForm-amount" name="amount">
								<a-input-number v-model:value="formData.amount" placeholder="请输入金额（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="第三方支付流水号" v-bind="validateInfos.transactionNo" id="ElderCustomerPaymentForm-transactionNo" name="transactionNo" v-if="formData.paymentMethodCode == '3' || formData.paymentMethodCode == '4'">
								<a-input v-model:value="formData.transactionNo" placeholder="请输入第三方支付流水号"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="收款日期" v-bind="validateInfos.paymentDate" id="ElderCustomerPaymentForm-paymentDate" name="paymentDate">
								<a-date-picker placeholder="请选择收款日期"  v-model:value="formData.paymentDate" value-format="YYYY-MM-DD"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="凭证文件" v-bind="validateInfos.voucherUrl" id="ElderCustomerPaymentForm-voucherUrl" name="voucherUrl">
								<j-upload v-model:value="formData.voucherUrl"   ></j-upload>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderCustomerPaymentForm-remark" name="remark">
                <a-textarea v-model:value="formData.remark" rows="4" placeholder="请输入备注" />
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
  import JUpload from '/@/components/Form/src/jeecg/components/JUpload/JUpload.vue';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderCustomerPayment.api';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { Form } from 'ant-design-vue';
  import { useUserStore } from '/@/store/modules/user';
  import {getCustomerListByProjectIdMethod} from './../ElderCustomer.api';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import {getListByCustomerIdMothod} from './../ElderCustomerAccount.api';
  import {getBillListByCustomerIdMethod} from './../ElderBill.api'
  import { render } from '/@/utils/common/renderUtils';
  import { useRoute } from 'vue-router';
  const route = useRoute()
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const customerList = ref([]);
  const billList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: '',   
    customerId: '',   
    checkinId: '',   
    accountId: undefined,
    billId: undefined,   
    paymentTypeName: '',   
    paymentTypeCode: '',   
    amount: undefined,
    transactionTypeCode: undefined,
    paymentMethodName: '', 
    paymentMethodCode: '',   
    transactionNo: '',   
    paymentDate: '',   
    voucherUrl: '',   
    delFlag: undefined,
  });
  const dealType = ref('');
  const customerAccountList = ref([]);
  const userStore = useUserStore();
  const userId = ref<Nullable<number | string>>(0);
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  watch(() => formData.projectId, async (val) => {
    if (val) {
      // 查询客户，查询房间列表
      customerList.value = await getCustomerListByProjectIdMethod({ projectId: val });
    } else {
      customerList.value = [];
    }
    if (formData.transactionTypeCode) {
      changetransactionType(formData.transactionTypeCode)
    }
  });
  onMounted(() => {
    // 记录当前的UserId
    userId.value = userStore.getUserInfo?.id;
    console.log('Mounted', userId.value);
  });
  //表单验证
  const validatorRules = reactive({
    customerId: [{ required: true, message: '请输入客户ID!'},],
    amount: [{ required: true, message: '请输入金额（元），退款时为负数!'},],
    paymentMethodCode: [{ required: true, message: '请选择支付方式!'},],
    paymentDate: [{ required: true, message: '请输入收款日期!'},],
    transactionTypeCode: [{ required: true, message: '请选择交易类型!'},],
    accountId:  [{ required: false, message: '请选择账户!'},],
    billId:  [{ required: false, message: '请选择账单!'},]
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
  async function queryAccount() {
    customerAccountList.value = await getListByCustomerIdMothod({customerId: route.query.id});
    if (customerAccountList.value){
      customerAccountList.value.forEach(item => {
        item.accountTypeName = getRenderDict(item.accountTypeCode,'account_type')
      });
      console.log(customerAccountList.value)
    }
  }
  function getRenderDict( text , dictCode){
    let te = render.renderDict(text, dictCode);
    return te.children;
  }
  async function queryBill(){
    billList.value = await getBillListByCustomerIdMethod({customerId: route.query.id});
  }

  function changeBill(val){
    const bill =  billList.value.filter(s=>s.id == val);
    formData.amount = bill[0].paidAmount;
  }
  function changetransactionType(val){
    console.log(val)
    if (formData.transactionTypeCode == '1' || formData.transactionTypeCode == '3'){
      queryAccount();
      validatorRules.accountId =  [{ required: true, message: '请选择账户!'},];
    } else {
      validatorRules.accountId =  [{ required: false, message: '请选择账户!'},];
    }
    if (formData.transactionTypeCode == '4'){
      queryBill();
      validatorRules.billId =  [{ required: true, message: '请选择账单!'},];
    } else {
      validatorRules.billId =  [{ required: false, message: '请选择账单!'},];
    }   
  }
  async function changePaymentMethodCode(val) {
    if (formData.transactionTypeCode == '1' || formData.transactionTypeCode == '3' || formData.paymentMethodCode == '1' || formData.paymentMethodCode == '2'){
      await queryAccount();
      validatorRules.accountId =  [{ required: true, message: '请选择账户!'},];
    } else {
      validatorRules.accountId =  [{ required: false, message: '请选择账户!'},];
    }
    if (formData.paymentMethodCode == '1' || formData.paymentMethodCode == '2') {
      const accList = customerAccountList.value.filter(item=> item.accountTypeCode == formData.paymentMethodCode);
      if (accList) {
        formData.accountId = accList[0].id
      } else {
        formData.accountId = undefined;
      }
    }
  }
  /**
   * 新增
   */
  function add(initData) {
    if (initData.customerId){
      dealType.value = '1';
    } else {
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
