<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderVitalSignsForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderVitalSignsForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderVitalSignsForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户ID" v-bind="validateInfos.customerId" id="ElderVitalSignsForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住id" v-bind="validateInfos.checkinId" id="ElderVitalSignsForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" placeholder="请输入入住id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="记录时间" v-bind="validateInfos.recordTime" id="ElderVitalSignsForm-recordTime" name="recordTime">
								<a-date-picker placeholder="请选择记录时间"  v-model:value="formData.recordTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="体温（°C），如37.5" v-bind="validateInfos.temperature" id="ElderVitalSignsForm-temperature" name="temperature">
								<a-input v-model:value="formData.temperature" placeholder="请输入体温（°C），如37.5"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="收缩压/高压（mmHg），如140" v-bind="validateInfos.bloodPressureHigh" id="ElderVitalSignsForm-bloodPressureHigh" name="bloodPressureHigh">
								<a-input v-model:value="formData.bloodPressureHigh" placeholder="请输入收缩压/高压（mmHg），如140"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="舒张压/低压（mmHg），如90" v-bind="validateInfos.bloodPressureLow" id="ElderVitalSignsForm-bloodPressureLow" name="bloodPressureLow">
								<a-input v-model:value="formData.bloodPressureLow" placeholder="请输入舒张压/低压（mmHg），如90"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="心率（次/分），如75" v-bind="validateInfos.heartRate" id="ElderVitalSignsForm-heartRate" name="heartRate">
								<a-input v-model:value="formData.heartRate" placeholder="请输入心率（次/分），如75"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="血糖（mmol/L），如5.8" v-bind="validateInfos.bloodSugar" id="ElderVitalSignsForm-bloodSugar" name="bloodSugar">
								<a-input v-model:value="formData.bloodSugar" placeholder="请输入血糖（mmol/L），如5.8"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="呼吸（次/分），如20" v-bind="validateInfos.respiration" id="ElderVitalSignsForm-respiration" name="respiration">
								<a-input v-model:value="formData.respiration" placeholder="请输入呼吸（次/分），如20"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注（如"餐后2小时"）" v-bind="validateInfos.remark" id="ElderVitalSignsForm-remark" name="remark">
								<a-input v-model:value="formData.remark" placeholder="请输入备注（如"餐后2小时"）"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderVitalSigns.api';
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
    recordTime: '',   
    temperature: '',   
    bloodPressureHigh: '',   
    bloodPressureLow: '',   
    heartRate: '',   
    bloodSugar: '',   
    respiration: '',   
    remark: '',   
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
    recordTime: [{ required: true, message: '请输入记录时间!'},],
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
