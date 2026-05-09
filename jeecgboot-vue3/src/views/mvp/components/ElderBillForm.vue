<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderBillForm">
          <a-row>		
						<a-col :span="24">
							<a-form-item label="账单号" v-bind="validateInfos.billNo" id="ElderBillForm-billNo" name="billNo" v-if="formData.id">
								<a-input v-model:value="formData.billNo" disabled ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户" v-bind="validateInfos.customerId" id="ElderBillForm-customerId" name="customerId">
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
							<a-form-item label="账单月份" v-bind="validateInfos.billMonth" id="ElderBillForm-billMonth" name="billMonth">
								<a-date-picker placeholder="请选择账单月份" picker="month"  v-model:value="formData.billMonth" value-format="YYYY-MM"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="应收总额（元）" v-bind="validateInfos.totalAmount" id="ElderBillForm-totalAmount" name="totalAmount">
								<a-input-number v-model:value="formData.totalAmount" placeholder="请输入应收总额（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="实收总额（元）" v-bind="validateInfos.paidAmount" id="ElderBillForm-paidAmount" name="paidAmount">
								<a-input-number v-model:value="formData.paidAmount" placeholder="请输入实收总额（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="优惠金额（元）" v-bind="validateInfos.discountAmount" id="ElderBillForm-discountAmount" name="discountAmount">
								<a-input-number v-model:value="formData.discountAmount" placeholder="请输入优惠金额（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="账单状态" v-bind="validateInfos.status" id="ElderBillForm-status" name="status">
								<JDictSelectTag type="select" v-model:value="formData.status" dictCode="bill_status" placeholder="请选择账单状态"/>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="应收日期" v-bind="validateInfos.dueDate" id="ElderBillForm-dueDate" name="dueDate">
								<a-date-picker placeholder="请选择应收日期"  v-model:value="formData.dueDate" value-format="YYYY-MM-DD"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderBillForm-remark" name="remark">
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
  import { saveOrUpdate } from '../ElderBill.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import {getCustomerListByProjectIdMethod} from './../ElderCustomer.api';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const customerList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: '',   
    billNo: '',   
    customerId: '',   
    checkinId: '',   
    billMonth: '',   
    totalAmount: undefined,
    paidAmount: undefined,
    discountAmount: 0,
    status: '0',   
    dueDate: '',   
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    customerId: [{ required: true, message: '请选择客户!'},],
    billMonth: [{ required: true, message: '请选择账单月份!'},],
    totalAmount: [{ required: true, message: '请输入应收总额（元）!'},],
    paidAmount: [{ required: true, message: '请输入实收总额（元）!'},],
    discountAmount: [{ required: true, message: '请输入优惠金额（元）!'},],
    dueDate: [{ required: true, message: '请输入应收日期!'},],
    status:  [{ required: true, message: '请选择!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });
  const dealType = ref('');
   //表单验证
  watch(() => formData.projectId, async (val) => {
    if (val) {
      // 查询客户，查询房间列表
      customerList.value = await getCustomerListByProjectIdMethod({ projectId: val });
    } else {
      customerList.value = [];
    }
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
