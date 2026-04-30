<template>
  <div class="p-2">
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form ref="formRef" @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="6">
            <a-form-item name="roomNo">
              <template #label><span title="房间号">房间号</span></template>
              <a-input placeholder="请输入房间号" v-model:value="queryParam.roomNo" allow-clear ></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
              <a-form-item name="status">
                <template #label><span title="状态">状态</span></template>
                <JDictSelectTag type="select" v-model:value="queryParam.status" dictCode="checkin_status" placeholder="请选择状态" />
              </a-form-item>
            </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="6">
              <a-form-item name="consultingId">
                <template #label><span title="咨询接待">咨询接待</span></template>
                <a-input placeholder="请输入咨询接待编码" v-model:value="queryParam.consultingId" allow-clear ></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="checkinTimeArr">
                <template #label><span title="入住时间">入住时间</span></template>
                <a-range-picker value-format="YYYY-MM-DD" v-model:value="queryParam.checkinTimeArr" class="query-group-cust"/>
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
        <a-button type="primary" v-auth="'elder_customer_checkin:add'"  @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>       
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <template v-slot:bodyCell="{ column, record, index, text }">
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <ElderCustomerCheckinModal ref="registerModal" @success="handleSuccess"></ElderCustomerCheckinModal>
  </div>
</template>

<script lang="ts" name="com.joydigit.seniorcaring.mvp-elderCustomerCheckin" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, superQuerySchema } from './ElderCustomerCheckin.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './ElderCustomerCheckin.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import ElderCustomerCheckinModal from './components/ElderCustomerCheckinModal.vue'
  import { useUserStore } from '/@/store/modules/user';
  import { useMessage } from '/@/hooks/web/useMessage';
  import {useModal} from '/@/components/Modal';
  import { getDateByPicker } from '/@/utils';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { useRoute } from 'vue-router';
  const route = useRoute();
  const emit = defineEmits(['successReload']);
  const fieldPickers = reactive({
  });

  const formRef = ref();
  const queryParam = reactive<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const userStore = useUserStore();
  const { createMessage } = useMessage();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'elder_customer_checkin',
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
      name: "elder_customer_checkin",
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
    const pardata = {
      projectId:route.query.projectId,
      customerId: route.query.id
    }
    registerModal.value.add(pardata);
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
    emit('successReload');
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'elder_customer_checkin:edit'
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '费用配置',
        onClick: handleDetail.bind(null, record),
        auth: 'elder_customer_checkin:feeConfig'
      },
      {
        label: '退住',
        onClick: handleDetail.bind(null, record),
        auth: 'elder_customer_checkin:feeConfig'
      },
      {
        label: '换房',
        onClick: handleDetail.bind(null, record),
        auth: 'elder_customer_checkin:feeConfig'
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
