<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderNursingRecordForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderNursingRecordForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderNursingRecordForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户ID" v-bind="validateInfos.customerId" id="ElderNursingRecordForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住id" v-bind="validateInfos.checkinId" id="ElderNursingRecordForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" placeholder="请输入入住id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="护理时间" v-bind="validateInfos.nursingDate" id="ElderNursingRecordForm-nursingDate" name="nursingDate">
								<a-date-picker placeholder="请选择护理时间"  v-model:value="formData.nursingDate" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="护理项目编码（如ORAL_CARE口腔护理）" v-bind="validateInfos.itemCode" id="ElderNursingRecordForm-itemCode" name="itemCode">
								<a-input v-model:value="formData.itemCode" placeholder="请输入护理项目编码（如ORAL_CARE口腔护理）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="护理项目名称" v-bind="validateInfos.itemName" id="ElderNursingRecordForm-itemName" name="itemName">
								<a-input v-model:value="formData.itemName" placeholder="请输入护理项目名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="执行人ID（user_id逻辑外键）" v-bind="validateInfos.executorId" id="ElderNursingRecordForm-executorId" name="executorId">
								<a-input v-model:value="formData.executorId" placeholder="请输入执行人ID（user_id逻辑外键）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="执行开始时间" v-bind="validateInfos.executeStartTime" id="ElderNursingRecordForm-executeStartTime" name="executeStartTime">
								<a-date-picker placeholder="请选择执行开始时间"  v-model:value="formData.executeStartTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="执行结束时间" v-bind="validateInfos.executeEndTime" id="ElderNursingRecordForm-executeEndTime" name="executeEndTime">
								<a-date-picker placeholder="请选择执行结束时间"  v-model:value="formData.executeEndTime" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="执行状态：0-待执行，1-已完成，2-跳过，3-异常" v-bind="validateInfos.status" id="ElderNursingRecordForm-status" name="status">
								<a-input v-model:value="formData.status" placeholder="请输入执行状态：0-待执行，1-已完成，2-跳过，3-异常"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="异常情况描述" v-bind="validateInfos.abnormalDesc" id="ElderNursingRecordForm-abnormalDesc" name="abnormalDesc">
								<a-input v-model:value="formData.abnormalDesc" placeholder="请输入异常情况描述"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="拍照凭证URL（如翻身后的皮肤状况）" v-bind="validateInfos.evidenceUrl" id="ElderNursingRecordForm-evidenceUrl" name="evidenceUrl">
								<a-input v-model:value="formData.evidenceUrl" placeholder="请输入拍照凭证URL（如翻身后的皮肤状况）"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderNursingRecord.api';
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
    nursingDate: '',   
    itemCode: '',   
    itemName: '',   
    executorId: '',   
    executeStartTime: '',   
    executeEndTime: '',   
    status: '',   
    abnormalDesc: '',   
    evidenceUrl: '',   
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
    nursingDate: [{ required: true, message: '请输入护理时间!'},],
    itemCode: [{ required: true, message: '请输入护理项目编码（如ORAL_CARE口腔护理）!'},],
    itemName: [{ required: true, message: '请输入护理项目名称!'},],
    executorId: [{ required: true, message: '请输入执行人ID（user_id逻辑外键）!'},],
    executeStartTime: [{ required: true, message: '请输入执行开始时间!'},],
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
