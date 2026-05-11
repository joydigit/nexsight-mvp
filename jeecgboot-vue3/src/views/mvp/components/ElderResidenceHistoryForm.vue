<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderResidenceHistoryForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="入住编号" v-bind="validateInfos.checkinId" id="ElderResidenceHistoryForm-checkinId" name="checkinId">
								<a-input v-model:value="formData.checkinId" disabled></a-input>
							</a-form-item>
						</a-col>					
						<a-col :span="24">
							<a-form-item label="变更类型" v-bind="validateInfos.changeTypeCode" id="ElderResidenceHistoryForm-changeTypeCode" name="changeTypeCode">
								<JDictSelectTag type="select" v-model:value="formData.changeTypeCode" dictCode="change_type" placeholder="请选择状态" disabled/>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="原房间" v-bind="validateInfos.oldRoomId" id="ElderResidenceHistoryForm-oldRoomId" name="oldRoomId">
								<a-select
                  v-model:value="formData.oldRoomId"
                  :options="roomList"
                  disabled
                  :fieldNames="{ label: 'roomNo', value: 'roomId' }"
                  showSearch
                  placeholder="请选择房间"
                  @change="handleChangeOldRoomId"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="原床位" v-bind="validateInfos.oldBedId" id="ElderResidenceHistoryForm-oldBedId" name="oldBedId">
								<a-select
                  v-model:value="formData.oldBedId"
                  :options="bedList"
                  disabled
                  :fieldNames="{ label: 'bedNo', value: 'id' }"
                  showSearch
                  placeholder="请选择床位"
                />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="退住日期" v-bind="validateInfos.checkoutTime" id="ElderNursingRecordForm-nursingDate" name="checkoutTime" v-if="optType == 1">
								<a-date-picker placeholder="请选择退住日期"  v-model:value="formData.checkoutTime" value-format="YYYY-MM-DD" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="新房间" v-bind="validateInfos.newRoomId" id="ElderResidenceHistoryForm-newRoomId" name="newRoomId" v-if="optType == 2">
                <a-select
                  v-model:value="formData.newRoomId"
                  :options="roomNewList"
                  :fieldNames="{ label: 'roomNo', value: 'roomId' }"
                  showSearch
                  placeholder="请选择新房间"
                  @change="handleChangeNewRoomId"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="新床位" v-bind="validateInfos.newBedId" id="ElderResidenceHistoryForm-newBedId" name="newBedId" v-if="optType == 2">
								<a-select
                  v-model:value="formData.newBedId"
                  :options="bedNewList"
                  :fieldNames="{ label: 'bedNo', value: 'id' }"
                  showSearch
                  placeholder="请选择新床位"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="变更原因" v-bind="validateInfos.reason" id="ElderResidenceHistoryForm-reason" name="reason">
								<a-textarea v-model:value="formData.reason" placeholder="请输入变更原因"  allow-clear ></a-textarea>
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
  import {getBedListByRoomIdMethod} from './../ElderBed.api';
  import {getRoomListByProjectIdMethod} from './../ElderRoom.api';
  import { saveOrUpdate } from '../ElderResidenceHistory.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const roomList = ref([]);
  const bedList = ref([]);
  const roomNewList = ref([]);
  const bedNewList = ref([]);
  const formRef = ref();
  const optType = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: '',   
    customerId: '',   
    checkinId: '',   
    changeTypeName: '',   
    changeTypeCode: '', 
    checkoutTime: undefined,  
    oldRoomId: '',   
    oldBedId: '',   
    newRoomId: undefined,   
    newBedId: undefined,   
    reason: '',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  watch(() => formData.projectId, async (val) => {
    if (val) {
      // 查询客户，查询房间列表
      roomList.value = await getRoomListByProjectIdMethod({ projectId: val });
      roomNewList.value = roomList.value
      // 如果已有 roomId，联动加载床位
      if (formData.oldRoomId) {
        await handleChangeOldRoomId(formData.oldRoomId);
      }
    } else {
      roomList.value = [];
      roomNewList.value = [];
      bedList.value = [];
      bedNewList.value = [];
    }
  });
  //表单验证
  const validatorRules = reactive({
    checkoutTime: [{ required: true, message: '请选择退住日期!'},],
    newRoomId: [{ required: false, message: '请选择新房间!'},],
    newBedId: [{ required: false, message: '请选择新床位!'},],
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
  function add(initData) {
    optType.value = initData.optType;
    if(initData.optType == 1){
      initData.changeTypeCode = '2'
      validatorRules.checkoutTime =  [{ required: true, message: '请选择退住日期!'},]
      validatorRules.newRoomId = [{ required: false, message: '请选择新房间!'},]
      validatorRules.newBedId = [{ required: false, message: '请选择新床位!'},]
    } else {
      initData.changeTypeCode = '3'
      validatorRules.checkoutTime =  [{ required: false, message: '请选择退住日期!'},]
      validatorRules.newRoomId = [{ required: true, message: '请选择新房间!'},]
      validatorRules.newBedId = [{ required: true, message: '请选择新床位!'},]
    }
    edit(initData);
  }

  async function handleChangeOldRoomId(value) {
    if (value) {
      let beds = await getBedListByRoomIdMethod({ roomId: value });
      beds.forEach(item=>{
        item.disabled = item.status == '2';
      })
      bedList.value = beds;
    } else {
      bedList.value = [];
    }
  }

  async function handleChangeNewRoomId(value) {
    if (value) {
      let beds = await getBedListByRoomIdMethod({ roomId: value });
      beds.forEach(item=>{
        item.disabled = item.status == '2';
      })
      bedNewList.value = beds;
    } else {
      bedNewList.value = [];
    }
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
