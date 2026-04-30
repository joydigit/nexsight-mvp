<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderProjectFeeConfigForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="项目" v-bind="validateInfos.projectId" id="ElderProjectFeeConfigForm-projectId" name="projectId">
								<a-select
                  v-model:value="formData.projectId"
                  :options="projectList"
                  :fieldNames="{ label: 'projectName', value: 'id' }"
                  showSearch
                  placeholder="请选择所属项目"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="费用类型" v-bind="validateInfos.paymentTypeCode" id="ElderProjectFeeConfigForm-paymentTypeCode" name="paymentTypeCode">
								<JDictSelectTag type="select" v-model:value="formData.paymentTypeCode" dictCode="payment_type" placeholder="请选择费用类型"  @change="handleChangePaymentTypeCode"/>
							</a-form-item>
						</a-col>
            <a-col :span="24" v-if="formData.paymentTypeCode == '6'">
							<a-form-item label="房间类型" v-bind="validateInfos.roomType" id="ElderProjectFeeConfigForm-roomType" name="roomType">
								<JDictSelectTag type="select" v-model:value="formData.roomType" dictCode="room_type" placeholder="请选择房间类型" />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="入住类型" v-bind="validateInfos.checkinType" id="ElderCustomerCheckinForm-checkinType" name="checkinType">
								<JDictSelectTag type="select" v-model:value="formData.checkinType" dictCode="checkin_type" placeholder="请选择入住类型"/>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="单价（元）" v-bind="validateInfos.price" id="ElderProjectFeeConfigForm-price" name="price">
								<a-input-number v-model:value="formData.price" placeholder="请输入单价（元）" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="单位" v-bind="validateInfos.unitCode" id="ElderProjectFeeConfigForm-unitCode" name="unitCode">
								<JDictSelectTag type="select" v-model:value="formData.unitCode" dictCode="fee_unit" placeholder="请选择单位" />
							</a-form-item>
						</a-col>					
						<a-col :span="24">
							<a-form-item label="状态" v-bind="validateInfos.status" id="ElderProjectFeeConfigForm-status" name="status">
								<JDictSelectTag type="select" v-model:value="formData.status" dictCode="fee_status" placeholder="请选择状态" />
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
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { saveOrUpdate } from '../ElderProjectFeeConfig.api';
  import { Form } from 'ant-design-vue';
  import { getProjectListAllM } from '../ElderProject.api'; 
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const projectList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: undefined,   
    paymentTypeCode: undefined, 
    roomType: undefined,  
    price: undefined,
    unitCode: undefined,   
    status: '0',   
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
    if (formData.paymentTypeCode){
      handleChangePaymentTypeCode(formData.paymentTypeCode)
    }
  })
  //表单验证
  const validatorRules = reactive({
    projectId: [{ required: true, message: '请选择项目!'},],
    paymentTypeCode: [{ required: true, message: '请选择费用类型!'},],
    price: [{ required: true, message: '请输入单价（元）!'},],
    unitCode: [{ required: true, message: '请选择单位!'},],
    status: [{ required: true, message: '请选择状态!'},],
    roomType: [{ required: false, message: '请选择房间类型!'},],
    checkinType: [{ required: true, message: '请选择入住类型!'},],
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

  function handleChangePaymentTypeCode(value){
    if (value == '6'){
      validatorRules.roomType = [{ required: true, message: '请选择房间类型!'},]
    } else {
      validatorRules.roomType = [{ required: false, message: '请选择房间类型!'},]
    }
  }
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
