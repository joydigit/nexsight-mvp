<template>
  <div class="kpi-cards-row">
    <Card
      v-for="(item, index) in kpiCardList"
      :key="item.title"
      size="small"
      :bordered="false"
      class="kpi-card"
      :style="{ animationDelay: `${index * 0.08}s` }"
    >
      <div class="kpi-card-inner">
        <div class="kpi-icon" :style="{ background: item.color }">
          <Icon :icon="item.icon" :size="24" color="#fff" />
        </div>
        <div class="kpi-content">
          <div class="kpi-value">
            <span v-if="item.prefix" class="kpi-prefix">{{ item.prefix }}</span>
            <CountTo :startVal="0" :endVal="item.value" :duration="1200" />
            <span v-if="item.suffix" class="kpi-suffix">{{ item.suffix }}</span>
          </div>
          <div class="kpi-title">{{ item.title }}</div>
        </div>
      </div>
    </Card>
  </div>
</template>

<script lang="ts" setup>
import { Card } from 'ant-design-vue';
import { Icon } from '/@/components/Icon';
import { CountTo } from '/@/components/CountTo/index';
import { kpiCardList } from '../mockData';
</script>

<style lang="less" scoped>
.kpi-cards-row {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
  margin-bottom: 16px;
}

.kpi-card {
  border-radius: 8px;
  transition: transform 0.2s, box-shadow 0.2s;
  animation: fadeInUp 0.5s ease both;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  :deep(.ant-card-body) {
    padding: 20px 16px;
  }
}

.kpi-card-inner {
  display: flex;
  align-items: center;
  gap: 16px;
}

.kpi-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.kpi-content {
  flex: 1;
  min-width: 0;
}

.kpi-value {
  font-size: 28px;
  font-weight: 700;
  line-height: 1.2;
  color: rgba(0, 0, 0, 0.85);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.kpi-prefix,
.kpi-suffix {
  font-size: 14px;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.45);
}

.kpi-title {
  font-size: 13px;
  color: rgba(0, 0, 0, 0.45);
  margin-top: 4px;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(16px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 1400px) {
  .kpi-cards-row {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .kpi-cards-row {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
