<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderConsultingForm">
          <a-row>						
						<a-col :span="24">
							<a-form-item label="所属项目" v-bind="validateInfos.projectId" id="ElderConsultingForm-projectId" name="projectId">
								<a-input v-model:value="formData.projectId" placeholder="请输入所属项目ID"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="姓名" v-bind="validateInfos.name" id="ElderConsultingForm-name" name="name">
								<a-input v-model:value="formData.name" placeholder="请输入姓名"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="性别" v-bind="validateInfos.gender" id="ElderConsultingForm-gender" name="gender">
                <JDictSelectTag type="select" v-model:value="formData.gender" dictCode="gender_type" placeholder="请选择性别" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="咨询类型" v-bind="validateInfos.consultTypeCode" id="ElderConsultingForm-consultTypeCode" name="consultTypeCode">
								<j-dict-select-tag v-model:value="formData.consultTypeCode" dictCode="consult_type" placeholder="请选择咨询类型"  allow-clear />
							</a-form-item>
						</a-col>						
						<a-col :span="24">
							<a-form-item label="跟进状态" v-bind="validateInfos.followStatus" id="ElderConsultingForm-followStatus" name="followStatus">
                <JDictSelectTag type="select" v-model:value="formData.followStatus" dictCode="follow_status" placeholder="请选择跟进状态" />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="证件类型" v-bind="validateInfos.idCardType" id="ElderConsultingForm-idCardType" name="idCardType">
                <JDictSelectTag type="select" v-model:value="formData.idCardType" dictCode="id_card_type" placeholder="请选择证件类型" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="证件号码" v-bind="validateInfos.idCard" id="ElderConsultingForm-idCard" name="idCard">
								<a-input v-model:value="formData.idCard" placeholder="请输入证件号码"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="地址" v-bind="validateInfos.address" id="ElderConsultingForm-address" name="address">
								<a-input v-model:value="formData.address" placeholder="请输入地址"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="联系电话" v-bind="validateInfos.phone" id="ElderConsultingForm-phone" name="phone">
								<a-input v-model:value="formData.phone" placeholder="请输入联系电话"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="出生日期" v-bind="validateInfos.birthDate" id="ElderConsultingForm-birthDate" name="birthDate">
								<a-date-picker placeholder="请选择出生日期"  v-model:value="formData.birthDate" value-format="YYYY-MM-DD"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderConsultingForm-remark" name="remark">
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
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderConsulting.api';
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
    name: '',   
    gender: '',   
    consultTypeName: '',   
    consultTypeCode: '',   
    followStatus: '',   
    idCard: '',   
    idCardType: '',   
    address: '',   
    phone: '',   
    birthDate: '',   
    receptionistName: '',   
    receptionistId: '',   
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    projectId: [{ required: true, message: '请输入所属项目ID!'},],
    consultTypeCode:  [{ required: true, message: '请选择咨询类型!'},],
    name: [{ required: true, message: '请输入姓名!'},],
    gender: [{ required: true, message: '请选择性别!'},],
    followStatus: [{ required: true, message: '请选择跟进状态!'},],
    phone: [{ required: true, message: '请输入联系电话!'},],
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
