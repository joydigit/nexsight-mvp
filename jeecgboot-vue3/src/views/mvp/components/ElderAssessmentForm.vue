<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderAssessmentForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="租户ID" v-bind="validateInfos.tenantId" id="ElderAssessmentForm-tenantId" name="tenantId">
								<a-input v-model:value="formData.tenantId" placeholder="请输入租户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="项目ID" v-bind="validateInfos.projectId" id="ElderAssessmentForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="客户ID" v-bind="validateInfos.customerId" id="ElderAssessmentForm-customerId" name="customerId">
								<a-input v-model:value="formData.customerId" placeholder="请输入客户ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住id" v-bind="validateInfos.checkinId" id="ElderAssessmentForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" placeholder="请输入入住id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评估类型名称" v-bind="validateInfos.assessmentTypeName" id="ElderAssessmentForm-assessmentTypeName" name="assessmentTypeName">
								<a-input v-model:value="formData.assessmentTypeName" placeholder="请输入评估类型名称"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估" v-bind="validateInfos.assessmentTypeCode" id="ElderAssessmentForm-assessmentTypeCode" name="assessmentTypeCode">
								<a-input v-model:value="formData.assessmentTypeCode" placeholder="请输入评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评分（百分制或特定量表分数）" v-bind="validateInfos.score" id="ElderAssessmentForm-score" name="score">
								<a-input-number v-model:value="formData.score" placeholder="请输入评分（百分制或特定量表分数）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评估结果描述" v-bind="validateInfos.result" id="ElderAssessmentForm-result" name="result">
								<a-input v-model:value="formData.result" placeholder="请输入评估结果描述"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="建议护理等级（如LEVEL_2）" v-bind="validateInfos.suggestedCareLevel" id="ElderAssessmentForm-suggestedCareLevel" name="suggestedCareLevel">
								<a-input v-model:value="formData.suggestedCareLevel" placeholder="请输入建议护理等级（如LEVEL_2）"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评估人姓名" v-bind="validateInfos.assessor" id="ElderAssessmentForm-assessor" name="assessor">
								<a-input v-model:value="formData.assessor" placeholder="请输入评估人姓名"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="评估报告附件URL（PDF或图片）" v-bind="validateInfos.fileUrl" id="ElderAssessmentForm-fileUrl" name="fileUrl">
								<a-input v-model:value="formData.fileUrl" placeholder="请输入评估报告附件URL（PDF或图片）"  allow-clear ></a-input>
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
  import { saveOrUpdate } from '../ElderAssessment.api';
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
    assessmentTypeName: '',   
    assessmentTypeCode: '',   
    score: undefined,
    result: '',   
    suggestedCareLevel: '',   
    assessor: '',   
    fileUrl: '',   
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
    assessmentTypeCode: [{ required: true, message: '请输入评估类型：ADL日常生活能力/FALL跌倒风险/COGNITIVE认知评估!'},],
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
