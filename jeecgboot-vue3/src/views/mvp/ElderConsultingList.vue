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
                />
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="name">
              <template #label><span title="姓名">姓名</span></template>
              <a-input placeholder="请输入姓名" v-model:value="queryParam.name" allow-clear ></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="consultTypeName">
              <template #label><span title="咨询类型名称">咨询类型</span></template>
              <JDictSelectTag type="select" v-model:value="queryParam.consultTypeName" dictCode="consult_type" placeholder="请选择咨询类型" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="6">
              <a-form-item name="followStatus">
                <template #label><span title="跟进状态">跟进状态</span></template>
                <JDictSelectTag type="select" v-model:value="queryParam.followStatus" dictCode="follow_status" placeholder="请选择跟进状态" />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="phone">
                <template #label><span title="联系电话">联系电话</span></template>
                <a-input placeholder="请输入联系电话" v-model:value="queryParam.phone" allow-clear ></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="receptionistName">
                <template #label><span title="接待人名称">接待人名</span></template>
                <a-input placeholder="请输入接待人名称" v-model:value="queryParam.receptionistName" allow-clear ></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" v-auth="'elder_consulting:add'"  @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" v-auth="'elder_consulting:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'elder_consulting:deleteBatch'">批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>       
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <template v-slot:bodyCell="{ column, record, index, text }">
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <ElderConsultingModal ref="registerModal" @success="handleSuccess"></ElderConsultingModal>
    <!-- 跟踪记录弹框 -->
    <JModal
      title="跟踪记录"
      :width="1200"
      :visible="followVisible"
      @cancel="followVisible = false"
      :destroyOnClose="true"
    >
      <template #footer></template>
      <ElderConsultingFollowRecordList :consulting="currentConsulting" />
    </JModal>
  </div>
</template>

<script lang="ts" name="com.joydigit.seniorcaring.mvp-elderConsulting" setup>
  import { ref, reactive, onMounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, superQuerySchema } from './ElderConsulting.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './ElderConsulting.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import ElderConsultingModal from './components/ElderConsultingModal.vue'
  import { useUserStore } from '/@/store/modules/user';
  import { useMessage } from '/@/hooks/web/useMessage';
  import {useModal} from '/@/components/Modal';
  import { getProjectListAllM } from './ElderProject.api';
  import { getDateByPicker } from '/@/utils';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JModal from '/@/components/Modal/src/JModal/JModal.vue';
  import ElderConsultingFollowRecordList from './ElderConsultingFollowRecordList.vue';

  const fieldPickers = reactive({
  });

  const projectList = ref([]);
  const formRef = ref();
  const queryParam = reactive<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const userStore = useUserStore();
  const { createMessage } = useMessage();
  const followVisible = ref(false);
  const currentConsulting = ref({});

  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'elder_consulting',
      api: list,
      columns,
      canResize:true,
      useSearchForm: false,
      showTableSetting: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: async (params) => {
        for (let key in fieldPickers) {
          if (queryParam[key] && fieldPickers[key]) {
            queryParam[key] = getDateByPicker(queryParam[key], fieldPickers[key]);
          }
        }
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: "咨询接待",
      url: getExportUrl,
      params: queryParam,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
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

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

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
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
   
  /**
   * 跟踪记录
   */
  function handleFollowRecord(record: Recordable) {
    currentConsulting.value = record;
    followVisible.value = true;
  }

  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'elder_consulting:edit'
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '跟踪记录',
        onClick: handleFollowRecord.bind(null, record),
        auth: 'elder_FollowRecord:list'
      },
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'elder_consulting:delete'
      }
    ]
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
