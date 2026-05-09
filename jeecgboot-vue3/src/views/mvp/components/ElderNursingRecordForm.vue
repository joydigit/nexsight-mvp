<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderNursingRecordForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="入住编码" v-bind="validateInfos.checkinId" id="ElderNursingRecordForm-checkinId" name="checkinId" v-if="formData.id">
								<a-input v-model:value="formData.checkinId"disabled></a-input>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="执行人" v-bind="validateInfos.executorId" id="ElderNursingRecordForm-executorId" name="executorId">
								<JSelectUser v-model:value="formData.executorId" rowKey="id" placeholder="请选择" :isRadioSelection="true"></JSelectUser>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="护理时间" v-bind="validateInfos.nursingDate" id="ElderNursingRecordForm-nursingDate" name="nursingDate">
								<a-date-picker placeholder="请选择护理时间"  v-model:value="formData.nursingDate" showTime value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="护理项目" v-bind="validateInfos.itemCode" id="ElderNursingRecordForm-itemCode" name="itemCode">
								<JDictSelectTag type="select" v-model:value="formData.itemCode" dictCode="nursing_item_type" placeholder="请选择护理项目" />
							</a-form-item>
						</a-col>	
						<a-col :span="24">
							<a-form-item label="执行状态" v-bind="validateInfos.status" id="ElderNursingRecordForm-status" name="status">
								<JDictSelectTag type="select" v-model:value="formData.status" dictCode="nursing_record_status" placeholder="请选择状态" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="拍照凭证" v-bind="validateInfos.evidenceUrl" id="ElderNursingRecordForm-evidenceUrl" name="evidenceUrl">
								<j-upload v-model:value="formData.evidenceUrl"   ></j-upload>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="异常情况描述" v-bind="validateInfos.abnormalDesc" id="ElderNursingRecordForm-abnormalDesc" name="abnormalDesc">
								<a-textarea v-model:value="formData.abnormalDesc" placeholder="请输入异常情况描述"  allow-clear ></a-textarea>
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
  import JSelectUser from '/@/components/Form/src/jeecg/components/JSelectUser.vue';
  import { saveOrUpdate } from '../ElderNursingRecord.api';
  import JUpload from '/@/components/Form/src/jeecg/components/JUpload/JUpload.vue';
  import { Form } from 'ant-design-vue';
  import { useUserStore } from '/@/store/modules/user';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const userStore = useUserStore();
  const userId = ref<Nullable<number | string>>(0);
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
    nursingDate: [{ required: true, message: '请选择!'},],
    itemCode: [{ required: true, message: '请选择!'},],
    executorId: [{ required: true, message: '请选择!'},],
    status:  [{ required: true, message: '请选择!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });
  onMounted(() => {
    // 记录当前的UserId
    userId.value = userStore.getUserInfo?.id;
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
    edit({...initData,executorId:userId.value});
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
