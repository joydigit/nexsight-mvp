<template>
  <div class="elder-dashboard">
    <!-- KPI 指标卡片 -->
    <ElderKpiCards />

    <!-- 图表区域 -->
    <a-row :gutter="16" class="charts-row">
      <a-col :xl="12" :lg="12" :md="24" :sm="24" class="chart-col">
        <ElderRoomPie />
      </a-col>
      <a-col :xl="12" :lg="12" :md="24" :sm="24" class="chart-col">
        <ElderProjectBar />
      </a-col>
    </a-row>

    <!-- 底部：快捷入口 + 待办列表 -->
    <a-row :gutter="16" class="bottom-row">
      <a-col :xl="12" :lg="12" :md="24" :sm="24" class="bottom-col">
        <ElderQuickEntries />
      </a-col>
      <a-col :xl="12" :lg="12" :md="24" :sm="24" class="bottom-col">
        <ElderRecentTodos />
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import ElderKpiCards from './components/ElderKpiCards.vue';
import ElderRoomPie from './components/ElderRoomPie.vue';
import ElderProjectBar from './components/ElderProjectBar.vue';
import ElderQuickEntries from './components/ElderQuickEntries.vue';
import ElderRecentTodos from './components/ElderRecentTodos.vue';
</script>

<style lang="less" scoped>
.elder-dashboard {
  padding: 16px;
  background: #f0f2f5;
  min-height: 100vh;
}

.charts-row {
  margin-bottom: 16px;
}

.chart-col {
  margin-bottom: 16px;
}

.bottom-row {
  margin-bottom: 16px;
}

.bottom-col {
  margin-bottom: 16px;
}
</style>
