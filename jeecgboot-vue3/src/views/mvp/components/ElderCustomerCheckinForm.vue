<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderCustomerCheckinForm">
          <a-row>						
						<a-col :span="24">
							<a-form-item label="销售" v-bind="validateInfos.salesId" id="ElderCustomerCheckinForm-salesId" name="salesId">
                <JSelectUser v-model:value="formData.salesId" rowKey="id" placeholder="请选择用户" :isRadioSelection="true"></JSelectUser>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="客户" v-bind="validateInfos.customerId" id="ElderRoomReserveForm-customerId" name="customerId">
								<a-select
                  v-model:value="formData.customerId"
                  :options="customerList"
                  :disabled="dealType == '1' || !!formData.id"
                  :fieldNames="{ label: 'name', value: 'id' }"
                  showSearch
                  placeholder="请选择客户"
                />
							</a-form-item>
						</a-col>					
						<a-col :span="24">
							<a-form-item label="房间" v-bind="validateInfos.roomId" id="ElderRoomReserveForm-roomId" name="roomId">
                <a-select
                  v-model:value="formData.roomId"
                  :options="roomList"
                  :disabled="dealType == '2' || !!formData.id"
                  :fieldNames="{ label: 'roomNo', value: 'roomId' }"
                  showSearch
                  placeholder="请选择房间"
                  @change="handleChangeRoomId"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="床位" v-bind="validateInfos.bedId" id="ElderRoomReserveForm-bedId" name="bedId">
								<a-select
                  v-model:value="formData.bedId"
                  :options="bedList"
                  :disabled="dealType == '2'  || !!formData.id"
                  :fieldNames="{ label: 'bedNo', value: 'id' }"
                  showSearch
                  placeholder="请选择床位"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住类型" v-bind="validateInfos.checkinType" id="ElderCustomerCheckinForm-checkinType" name="checkinType">
								<JDictSelectTag type="select" v-model:value="formData.checkinType" dictCode="checkin_type" placeholder="请选择入住类型" :disabled="!!formData.id"/>
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="护理等级" v-bind="validateInfos.nursingLevel" id="ElderCustomerCheckinForm-nursingLevel" name="nursingLevel">
								<JDictSelectTag type="select" v-model:value="formData.nursingLevel" dictCode="care_level" placeholder="请选择护理等级"/>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="入住时间" v-bind="validateInfos.checkinTime" id="ElderCustomerCheckinForm-checkinTime" name="checkinTime">
								<a-date-picker placeholder="请选择入住时间"  v-model:value="formData.checkinTime" value-format="YYYY-MM-DD" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="预计退住时间" v-bind="validateInfos.expectCheckoutTime" id="ElderCustomerCheckinForm-expectCheckoutTime" name="expectCheckoutTime">
								<a-date-picker placeholder="请选择预计退住时间"  v-model:value="formData.expectCheckoutTime" value-format="YYYY-MM-DD" style="width: 100%"  allow-clear />
							</a-form-item>
						</a-col>
            <a-col :span="24">
							<a-form-item label="合同号" v-bind="validateInfos.contractNo" id="ElderCustomerCheckinForm-contractNo" name="contractNo">
								<a-input v-model:value="formData.contractNo" placeholder="请输入合同号"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="合同" v-bind="validateInfos.contractFileUrl" id="ElderCustomerCheckinForm-contractFileUrl" name="contractFileUrl">
								<j-upload v-model:value="formData.contractFileUrl"   ></j-upload>
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
  import JUpload from '/@/components/Form/src/jeecg/components/JUpload/JUpload.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getDateByPicker, getValueType } from '/@/utils';
  import { saveOrUpdate } from '../ElderCustomerCheckin.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import {getBedListByRoomIdMethod} from './../ElderBed.api';
  import {getRoomListByProjectIdMethod} from './../ElderRoom.api';
  import {getCustomerListByProjectIdMethod} from './../ElderCustomer.api';
  import { useUserStore } from '/@/store/modules/user';
  import JSelectUser from '/@/components/Form/src/jeecg/components/JSelectUser.vue';
  const roomList = ref([]);
  const customerList = ref([]);
  const bedList = ref([]);
  const dealType = ref('');
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
    salesName: '',   
    consultingId: '',   
    salesId: '',   
    checkinTime: '',   
    contractFileUrl: '',   
    contractNo: '',   
    realCheckoutTime: '',   
    expectCheckoutTime: '',  
    nursingLevel: '', 
    checkinType: '',   
    status: '',   
    customerId: undefined, 
    bedId: undefined,
    roomId: undefined, 
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  watch([() => formData.projectId,() => formData.roomId,() => formData.customerId], async ([newProjectId, newRoomId, newCustomerId], [oProjectId, oRoomId, oCustomerId]) => {
    if (newProjectId) {
      // 查询客户，查询房间列表
      roomList.value = await getRoomListByProjectIdMethod({ projectId: newProjectId });
      customerList.value = await getCustomerListByProjectIdMethod({ projectId: newProjectId });
      // 如果已有 roomId，联动加载床位
      if (formData.roomId) {
        await handleChangeRoomId(formData.roomId);
      }
    } else {
      roomList.value = [];
      customerList.value = [];
      bedList.value = [];
    }
  });
  onMounted(() => {
    // 记录当前的UserId
    userId.value = userStore.getUserInfo?.id;
    console.log('Mounted', userId.value);
  });
  async function handleChangeRoomId(value) {
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
  const validatorRules = reactive({
    customerId: [{ required: true, message: '请选择客户!'},],
    roomId: [{ required: true, message: '请选择房间!'},],
    bedId: [{ required: true, message: '请选择床位!'},],
    checkinType: [{ required: true, message: '请选择入住类型!'},],
    checkinTime: [{ required: true, message: '请选择入住时间!'},],
    expectCheckoutTime:  [{ required: true, message: '请选择预计退住时间!'},],
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
    if (initData.customerId){
      dealType.value = '1';
    }
    if (initData.bedId){
      dealType.value = '2';
    }
    initData.salesId= userId.value;
    edit(initData);
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
