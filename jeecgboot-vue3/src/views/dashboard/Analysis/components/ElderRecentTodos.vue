<template>
  <a-card :bordered="false" title="近期待办" class="todos-card">
    <a-tabs v-model:activeKey="activeTab" size="small">
      <a-tab-pane key="consulting" tab="待跟进咨询">
        <a-table
          :dataSource="pendingConsultingList"
          :columns="consultingColumns"
          size="small"
          :pagination="false"
          rowKey="id"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'status'">
              <a-tag :color="record.status === '待跟进' ? 'orange' : 'blue'">{{ record.status }}</a-tag>
            </template>
          </template>
        </a-table>
      </a-tab-pane>
      <a-tab-pane key="checkin" tab="近期入住">
        <a-table
          :dataSource="recentCheckinList"
          :columns="checkinColumns"
          size="small"
          :pagination="false"
          rowKey="id"
        >
          <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'status'">
              <a-tag color="green">{{ record.status }}</a-tag>
            </template>
          </template>
        </a-table>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { pendingConsultingList, recentCheckinList } from '../mockData';

const activeTab = ref('consulting');

const consultingColumns = [
  { title: '客户', dataIndex: 'customerName', key: 'customerName' },
  { title: '电话', dataIndex: 'phone', key: 'phone' },
  { title: '咨询日期', dataIndex: 'consultingDate', key: 'consultingDate' },
  { title: '跟进人', dataIndex: 'followPerson', key: 'followPerson' },
  { title: '状态', dataIndex: 'status', key: 'status', align: 'center' as const },
];

const checkinColumns = [
  { title: '客户', dataIndex: 'customerName', key: 'customerName' },
  { title: '房间', dataIndex: 'roomNo', key: 'roomNo' },
  { title: '项目', dataIndex: 'projectName', key: 'projectName' },
  { title: '入住日期', dataIndex: 'checkinDate', key: 'checkinDate' },
  { title: '状态', dataIndex: 'status', key: 'status', align: 'center' as const },
];
</script>

<style lang="less" scoped>
.todos-card {
  border-radius: 8px;
  height: 100%;
}
</style>
