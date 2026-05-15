<template>
  <a-card :bordered="false" title="房间状态分布" class="chart-card">
    <Pie :chartData="roomStatusData" height="280px" />
    <div class="pie-legend">
      <span v-for="item in roomStatusData" :key="item.name" class="legend-item">
        <span class="legend-dot" :style="{ background: getColor(item.name) }"></span>
        {{ item.name }} ({{ item.value }})
      </span>
    </div>
  </a-card>
</template>

<script lang="ts" setup>
import Pie from '/@/components/chart/Pie.vue';
import { roomStatusData } from '../mockData';

const colorMap: Record<string, string> = {
  '空闲': '#52c41a',
  '已入住': '#1890ff',
  '预定': '#faad14',
  '维修': '#f5222d',
  '停用': '#722ed1',
};

function getColor(name: string) {
  return colorMap[name] || '#999';
}
</script>

<style lang="less" scoped>
.chart-card {
  border-radius: 8px;
  height: 100%;
}

.pie-legend {
  display: flex;
  justify-content: center;
  gap: 16px;
  flex-wrap: wrap;
  margin-top: 8px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: rgba(0, 0, 0, 0.45);
}

.legend-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  display: inline-block;
}
</style>
