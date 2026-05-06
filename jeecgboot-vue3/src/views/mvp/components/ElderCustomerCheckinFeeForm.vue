<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerCheckinFeeForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="入住编码" v-bind="validateInfos.checkinId" id="ElderCustomerCheckinFeeForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" disabled></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="费用类型" v-bind="validateInfos.paymentTypeCode" id="ElderCustomerCheckinFeeForm-feeType" name="paymentTypeCode">
								<JDictSelectTag type="select" v-model:value="formData.paymentTypeCode" dictCode="payment_type" placeholder="请选择费用类型" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="金额" v-bind="validateInfos.amount" id="ElderCustomerCheckinFeeForm-amount" name="amount">
								<a-input-number v-model:value="formData.amount" placeholder="请输入金额" style="width: 100%" allow-clear ></a-input-number>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="单位" v-bind="validateInfos.unitCode" id="ElderCustomerCheckinFeeForm-feeConfigId" name="unitCode">
								<JDictSelectTag type="select" v-model:value="formData.unitCode" dictCode="fee_unit" placeholder="请选择单位" />
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
  import { saveOrUpdate } from '../ElderCustomerCheckinFee.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
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
    paymentTypeCode: '',   
    projectId: '',   
    feeType: '',   
    checkinId: '',   
    feeConfigId: '',  
    unitCode: undefined,
    amount: 0,
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    checkinId: [{ required: true, message: '请输入入住编码!'},],
    paymentTypeCode: [{ required: true, message: '请选择费用类型!'},],
    amount: [{ required: true, message: '请输入金额!'},],
    unitCode: [{ required: true, message: '请选择单位!'},],
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
  function add(val) {
    edit(val);
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
