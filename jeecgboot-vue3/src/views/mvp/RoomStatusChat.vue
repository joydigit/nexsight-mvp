<template>
    <div class="p-2">
       <!--查询区域-->
      <div class="jeecg-basic-table-form-container">
        <a-form ref="formRef" @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-row :gutter="24">
            <a-col :lg="6">
              <a-form-item name="projectId">
                <template #label><span title="所属项目">所属项目</span></template>
                <a-select
                    v-model:value="queryParam.projectId"
                    :options="projectList"
                    :fieldNames="{ label: 'projectName', value: 'id' }"
                    showSearch
                    placeholder="请选择所属项目"
                    @change="handleChangeProjectId"
                  />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="buildingId">
                <template #label><span title="楼栋">楼栋</span></template>
                <a-select
                    v-model:value="queryParam.buildingId"
                    :options="buildingList"
                    :fieldNames="{ label: 'buildingName', value: 'id' }"
                    showSearch
                    placeholder="请选择楼栋"
                    @change="handleChangeBuilding"
                  />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="floorId">
                <template #label><span title="楼层">楼层</span></template>
                <a-select
                    v-model:value="queryParam.floorId"
                    :options="floorList"
                    :fieldNames="{ label: 'floorName', value: 'id' }"
                    showSearch
                    placeholder="请选择楼层"
                  />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="roomNo">
                <template #label><span title="房间号">房间号</span></template>
                <a-input placeholder="请输入房间号" v-model:value="queryParam.roomNo" allow-clear ></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-col :lg="6">
                  <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                  <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>                
                </a-col>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>      
    </div>
</template>
<script lang="ts" name="com.joydigit.seniorcaring.mvp-RoomStatusChat" setup>
  import { ref, reactive, onMounted } from 'vue';
  import { useUserStore } from '/@/store/modules/user';
  import { useMessage } from '/@/hooks/web/useMessage';
   import {useModal} from '/@/components/Modal';
  import {getBuildingListByProjectIdMethod} from './ElderBuilding.api';
  import { getProjectListAllM } from './ElderProject.api'; 
  import {getFloorListByBuildingIdMethod} from './ElderFloor.api';

  const projectList = ref([]);
  const buildingList = ref([]);
  const floorList = ref([]);
  const formRef = ref();
  const queryParam = reactive<any>({});


  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
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
  const labelCol = reactive({
    xs:24,
    sm:4,
    xl:6,
    xxl:4
  });
  const wrapperCol = reactive({
    xs: 24,
    sm: 20,
  });
  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    searchQuery();
  }
  
  /**
   * 查询
   */
   function searchQuery() {
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    formRef.value.resetFields();
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }

</script>
<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    padding: 0;
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
    .ant-form-item:not(.ant-form-item-with-help){
      margin-bottom: 16px;
      height: 32px;
    }
    :deep(.ant-picker),:deep(.ant-input-number){
      width: 100%;
    }
  }
</style>
