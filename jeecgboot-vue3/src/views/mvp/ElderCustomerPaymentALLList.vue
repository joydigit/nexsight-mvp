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
            <a-form-item name="customerName">
              <template #label><span title="姓名">姓名</span></template>
              <a-input placeholder="请输入姓名" v-model:value="queryParam.customerName" allow-clear ></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="6">
            <a-form-item name="checkinId">
              <template #label><span title="入住编码">入住编码</span></template>
              <a-input placeholder="请输入住编码" v-model:value="queryParam.checkinId" allow-clear ></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="6">
              <a-form-item name="billNo">
                <template #label><span title="账单编号">账单编号</span></template>
                <a-input placeholder="请输账单编号" v-model:value="queryParam.billNo" allow-clear ></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="paymentMethodCode">
                <template #label><span title="支付方式">支付方式</span></template>
                <JDictSelectTag type="select" v-model:value="queryParam.paymentMethodCode" dictCode="payment_method" placeholder="请选择支付方式" />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="transactionTypeCode">
                <template #label><span title="交易类型">交易类型</span></template>
                <JDictSelectTag type="select" v-model:value="queryParam.transactionTypeCode" dictCode="transaction_type_code" placeholder="请选择交易类型" />
              </a-form-item>
            </a-col>
            <a-col :lg="6">
              <a-form-item name="confirmFlag">
                <template #label><span title="确认状态">确认状态</span></template>
                <JDictSelectTag type="select" v-model:value="queryParam.confirmFlag" dictCode="payment_confirm_flag" placeholder="请选择确认状态" />
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
        <a-button  type="primary" v-auth="'elder_customer_payment:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleConfirm">
                <Icon icon="ant-design:delete-outlined"></Icon>
                确认
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'elder_customer_payment:confirm'">批量操作
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
    <ElderCustomerPaymentModal ref="registerModal" @success="handleSuccess"></ElderCustomerPaymentModal>
  </div>
</template>

<script lang="ts" name="com.joydigit.seniorcaring.mvp-elderCustomerPayment" setup>
  import { ref, reactive,onMounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columnsAll, superQuerySchema } from './ElderCustomerPayment.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, confirmMethod } from './ElderCustomerPayment.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import ElderCustomerPaymentModal from './components/ElderCustomerPaymentModal.vue'
  import { useUserStore } from '/@/store/modules/user';
  import { useMessage } from '/@/hooks/web/useMessage';
   import {useModal} from '/@/components/Modal';
  import { getDateByPicker } from '/@/utils';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { useRoute } from 'vue-router';
  import { getProjectListAllM } from './ElderProject.api'; 
  const route = useRoute()
  const fieldPickers = reactive({
  });
  const projectList = ref([]);
  const formRef = ref();
  const queryParam = reactive<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const userStore = useUserStore();
  const { createMessage } = useMessage();
  onMounted(async () => {
    projectList.value = await getProjectListAllM();
  });
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'elder_customer_payment',
      api: list,
      columns: columnsAll,
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
        queryParam.customerId = route.query.id;
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: "资金流水",
      url: getExportUrl,
      params: queryParam,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection,selectedRowKeys }] = tableContext;
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
  async function handleConfirmMethod(record) {
    await confirmMethod({ ids: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleConfirm() {
    await confirmMethod({ ids: selectedRowKeys.value }, handleSuccess);
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
        label: '确认',
        onClick: handleConfirmMethod.bind(null, record),
        auth: 'elder_customer_payment:confirm',
        ifShow: record.confirmFlag == '0'
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return []
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
