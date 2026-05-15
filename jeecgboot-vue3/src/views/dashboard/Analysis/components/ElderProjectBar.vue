<template>
  <a-card :bordered="false" title="各项目在住人数" class="chart-card">
    <Bar :chartData="projectOccupancyData" height="280px" seriesColor="#1890ff" />
  </a-card>
</template>

<script lang="ts" setup>
import Bar from '/@/components/chart/Bar.vue';
import { projectOccupancyData } from '../mockData';
</script>

<style lang="less" scoped>
.chart-card {
  border-radius: 8px;
  height: 100%;
}
</style>
