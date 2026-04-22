<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ElderBedForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="项目" v-bind="validateInfos.projectId" id="ElderRoomForm-projectId" name="projectId">
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
							<a-form-item label="楼栋" v-bind="validateInfos.buildingId" id="ElderRoomForm-buildingId" name="buildingId">
								<a-select
                  v-model:value="formData.buildingId"
                  :options="buildingList"
                  :fieldNames="{ label: 'buildingName', value: 'id' }"
                  showSearch
                  placeholder="请选择楼栋"
                  @change="handleChangeBuilding"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="楼层" v-bind="validateInfos.floorId" id="ElderRoomForm-floorId" name="floorId">
								<a-select
                  v-model:value="formData.floorId"
                  :options="floorList"
                  :fieldNames="{ label: 'floorName', value: 'id' }"
                  showSearch
                  placeholder="请选择楼层"
                  @change="handleChangeFloor"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="房号" v-bind="validateInfos.roomId" id="ElderRoomForm-roomNo" name="roomId">
								<a-select
                  v-model:value="formData.roomId"
                  :options="roomList"
                  :fieldNames="{ label: 'roomNo', value: 'id' }"
                  showSearch
                  placeholder="请选择房间"
                />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="床位号" v-bind="validateInfos.bedNo" id="ElderBedForm-bedNo" name="bedNo">
								<a-input v-model:value="formData.bedNo" placeholder="请输入床位号"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="状态" v-bind="validateInfos.status" id="ElderBedForm-status" name="status">
								<JDictSelectTag type="select" v-model:value="formData.status" dictCode="room_status" placeholder="请选择状态" />
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
  import { saveOrUpdate } from '../ElderBed.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {getBuildingListByProjectIdMethod} from '../ElderBuilding.api';
  import { getProjectListAllM } from '../ElderProject.api'; 
  import {getFloorListByBuildingIdMethod} from '../ElderFloor.api';
  import {getRoomListByFloorIdMethod} from '../ElderRoom.api';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const projectList = ref([]);
  const buildingList = ref([]);
  const floorList = ref([]);
  const roomList = ref([]);
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    tenantId: '',   
    projectId: undefined,
    floorId: undefined,
    buildingId: undefined,   
    roomId: undefined,   
    bedNo: '',   
    status: '0',   
    delFlag: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    buildingId: [{ required: true, message: '请选择楼栋!'},],
    floorId: [{ required: true, message: '请选择楼层!'},],
    projectId: [{ required: true, message: '请选择项目!'},],
    roomId: [{ required: true, message: '请选择房间!'},],
    bedNo: [{ required: true, message: '请输入床位号!'},],
  });
  
  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
  watch(()=>{
    if (formData.projectId){
      handleChangeProjectId(formData.projectId);
    }
    if (formData.buildingId) {
      handleChangeBuilding(formData.buildingId);
    }
    if (formData.floorId) {
      handleChangeFloor(formData.floorId);
    }
  })
   // 查询楼栋
   async function handleChangeProjectId(value){
    if (value) {
      const data = await getBuildingListByProjectIdMethod({projectId: value});
      buildingList.value = data;
    } else {
      buildingList.value = [];
    }
  }
  async function handleChangeBuilding(value){
    if (value) {
      const data = await getFloorListByBuildingIdMethod({buildingId: value});
      floorList.value = data;
    } else {
      floorList.value = [];
    }
  }
  async function handleChangeFloor(value) {
    if (value) {
      const data = await getRoomListByFloorIdMethod({floorId: value});
      roomList.value = data;
    } else {
      roomList.value = [];
    }
  }
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
