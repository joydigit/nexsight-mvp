<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderConsultingFollowRecordForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="线索客户" v-bind="validateInfos.consultingName" id="ElderConsultingFollowRecordForm-consultingName" name="consultingName">
								<a-input v-model:value="formData.consultingName" disabled ></a-input>
							</a-form-item>
						</a-col>						
						<a-col :span="24">
							<a-form-item label="接待人" v-bind="validateInfos.receptionistId" id="ElderConsultingFollowRecordForm-receptionistName" name="receptionistId">
                <JSelectUser v-model:value="formData.receptionistId" rowKey="id" placeholder="请选择用户" :isRadioSelection="true"></JSelectUser>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="回访类型" v-bind="validateInfos.followTypeCode" id="ElderConsultingFollowRecordForm-followTypeCode" name="followTypeCode">
								<JDictSelectTag type="select" v-model:value="formData.followTypeCode" dictCode="follow_type" placeholder="请选择回访类型" />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="跟进状态" v-bind="validateInfos.followStatus" id="ElderConsultingFollowRecordForm-followStatus" name="followStatus">
                <JDictSelectTag type="select" v-model:value="formData.followStatus" dictCode="follow_status" placeholder="请选择跟进状态" />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="跟进时间" v-bind="validateInfos.followTime" id="ElderConsultingFollowRecordForm-followTime" name="followTime">
								<DatePicker :showTime="true" placeholder="请选择跟进时间"  v-model:value="formData.followTime" value-format="YYYY-MM-DD HH:mm:ss "  style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="备注" v-bind="validateInfos.remark" id="ElderConsultingFollowRecordForm-remark" name="remark">
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
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderConsultingFollowRecord.api';
  import { Form } from 'ant-design-vue';
  import JSelectUser from '/@/components/Form/src/jeecg/components/JSelectUser.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import { useUserStore } from '/@/store/modules/user';
  import {DatePicker} from 'ant-design-vue'
  const userStore = useUserStore();
  const userId = ref<Nullable<number | string>>(0);
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true },
    consulting: { type: Object, default: () => ({})},
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: '',   
    receptionistId: '',   
    receptionistName: '',   
    followTypeCode: '',   
    followTypeName: '', 
    followStatus: undefined,  
    consultingId: '',  
    consultingName: '', 
    followTime: undefined,
    remark: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    followTypeCode: [{ required: true, message: '请选择回访类型!'},],
    followStatus:  [{ required: true, message: '请选择跟进状态!'},],
    followTime: [{ required: true, message: '请选择跟进时间!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });
  //日期个性化选择
  const fieldPickers = reactive({
  });
  onMounted(() => {
    // 记录当前的UserId
    userId.value = userStore.getUserInfo?.id;
    console.log('Mounted', userId.value);
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
    edit({consultingId: props.consulting.id,consultingName: props.consulting.name,receptionistId: userId.value});
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
