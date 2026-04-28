<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerForm">
          <a-row>						
						<a-col :span="24">
							<a-form-item label="所属项目" v-bind="validateInfos.projectId" id="ElderCustomerForm-projectId" name="projectId">
								<a-select
                  v-model:value="formData.projectId"
                  :options="projectList"
                  :fieldNames="{ label: 'projectName', value: 'id' }"
                  showSearch
                  placeholder="请选择所属项目"
                  @change="handleChangeProjectId"
                />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="咨询接待" v-bind="validateInfos.consultingId" id="ElderCustomerForm-consultingId" name="consultingId">
                <a-select
                  v-model:value="formData.consultingId"
                  :options="consultingList"
                  :fieldNames="{ label: 'name', value: 'id' }"
                  showSearch
                  placeholder="请选择咨询接待"
                  @change="handleChangeConsulting"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="姓名" v-bind="validateInfos.name" id="ElderCustomerForm-name" name="name">
								<a-input v-model:value="formData.name" placeholder="请输入姓名"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="性别" v-bind="validateInfos.gender" id="ElderCustomerForm-gender" name="gender">
								<JDictSelectTag type="select" v-model:value="formData.gender" dictCode="gender_type" placeholder="请选择性别" />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="证件类型" v-bind="validateInfos.idCardType" id="ElderCustomerForm-idCardType" name="idCardType">
								<JDictSelectTag type="select" v-model:value="formData.idCardType" dictCode="id_card_type" placeholder="请选择证件类型" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="证件号" v-bind="validateInfos.idCard" id="ElderCustomerForm-idCard" name="idCard">
								<a-input v-model:value="formData.idCard" placeholder="请输入证件号"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="联系电话" v-bind="validateInfos.phone" id="ElderCustomerForm-phone" name="phone">
								<a-input v-model:value="formData.phone" placeholder="请输入联系电话"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="出生日期" v-bind="validateInfos.birthDate" id="ElderCustomerForm-birthDate" name="birthDate">
								<a-date-picker placeholder="请选择出生日期"  v-model:value="formData.birthDate" value-format="YYYY-MM-DD"  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="地址" v-bind="validateInfos.address" id="ElderCustomerForm-address" name="address">
								<a-input v-model:value="formData.address" placeholder="请输入地址"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderCustomerForm-remark" name="remark">
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
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getProjectListAllM } from './../ElderProject.api'; 
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderCustomer.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import { getElderConsultingAllListMethod,queryByIdMethod } from './../ElderConsulting.api';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const projectList = ref([]);
  const consultingList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',  
    consultingId: undefined, 
    projectId: undefined,   
    name: '',   
    gender: '',   
    idCard: '',   
    idCardType: '',   
    address: '',   
    phone: '',   
    birthDate: '',   
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
  watch(()=>{
    if (formData.projectId){
      handleChangeProjectId(formData.projectId);
    }
  })
   // 查询楼栋
  async function handleChangeProjectId(value){
    if (value) {
      const data = await getElderConsultingAllListMethod({projectId: value});
      consultingList.value = data;
    } else {
      consultingList.value = [];
    }
  }
  async function handleChangeConsulting(value) {
    const conInfo = await queryByIdMethod({id: value});
    const tmpData = {
      name: conInfo.name,
      gender: conInfo.gender,
      idCard: conInfo.idCard,
      idCardType: conInfo.idCardType,
      address: conInfo.address,
      phone: conInfo.phone,
      birthDate: conInfo.birthDate
    };    
    //赋值
    Object.assign(formData, tmpData);
  }
  //表单验证
  const validatorRules = reactive({
    projectId: [{ required: true, message: '请选择所属项目!'},],
    name: [{ required: true, message: '请输入姓名!'},],
    gender: [{ required: true, message: '请选择性别'},],
    idCardType:  [{ required: true, message: '请选择证件类型!'},],
    idCard:  [{ required: true, message: '请输入证件号!'},],
    phone:  [{ required: true, message: '请输入手机号!'},],
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
