<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderBillDetailForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderBillDetailForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderBillDetailForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="账单ID" v-bind="validateInfos.billId" id="ElderBillDetailForm-billId" name="billId">
								<a-input v-model:value="formData.billId" placeholder="请输入账单ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他" v-bind="validateInfos.itemType" id="ElderBillDetailForm-itemType" name="itemType">
								<a-input v-model:value="formData.itemType" placeholder="请输入费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="费用名称（如"床位费-单人间"、"护理费-特级"）" v-bind="validateInfos.itemName" id="ElderBillDetailForm-itemName" name="itemName">
								<a-input v-model:value="formData.itemName" placeholder="请输入费用名称（如"床位费-单人间"、"护理费-特级"）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="数量（如15天，或1次）" v-bind="validateInfos.quantity" id="ElderBillDetailForm-quantity" name="quantity">
								<a-input-number v-model:value="formData.quantity" placeholder="请输入数量（如15天，或1次）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="单价（元）" v-bind="validateInfos.unitPrice" id="ElderBillDetailForm-unitPrice" name="unitPrice">
								<a-input-number v-model:value="formData.unitPrice" placeholder="请输入单价（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="金额（元）= quantity * unit_price" v-bind="validateInfos.amount" id="ElderBillDetailForm-amount" name="amount">
								<a-input-number v-model:value="formData.amount" placeholder="请输入金额（元）= quantity * unit_price" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="计算说明（如"入住15天，按天折算：6000/30*15"）" v-bind="validateInfos.calcDesc" id="ElderBillDetailForm-calcDesc" name="calcDesc">
								<a-input v-model:value="formData.calcDesc" placeholder="请输入计算说明（如"入住15天，按天折算：6000/30*15"）"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderBillDetail.api';
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
    billId: '',   
    itemType: '',   
    itemName: '',   
    quantity: undefined,
    unitPrice: undefined,
    amount: undefined,
    calcDesc: '',   
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
    billId: [{ required: true, message: '请输入账单ID!'},],
    itemType: [{ required: true, message: '请输入费用类型：BED_FEE床位费/CARE_FEE护理费/MEAL_FEE餐费/MEDICAL医疗费/OTHER其他!'},],
    itemName: [{ required: true, message: '请输入费用名称（如"床位费-单人间"、"护理费-特级"）!'},],
    quantity: [{ required: true, message: '请输入数量（如15天，或1次）!'},],
    unitPrice: [{ required: true, message: '请输入单价（元）!'},],
    amount: [{ required: true, message: '请输入金额（元）= quantity * unit_price!'},],
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
