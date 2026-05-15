<template>
  <a-card :bordered="false" title="快捷操作" class="quick-card">
    <a-card-grid
      v-for="item in quickEntries"
      :key="item.title"
      class="quick-grid-item"
      @click="handleClick(item)"
    >
      <div class="quick-item-inner">
        <span class="quick-icon-wrap" :style="{ background: item.color }">
          <Icon :icon="item.icon" :size="20" color="#fff" />
        </span>
        <span class="quick-title">{{ item.title }}</span>
      </div>
    </a-card-grid>
  </a-card>
</template>

<script lang="ts" setup>
import { Card } from 'ant-design-vue';
import { Icon } from '/@/components/Icon';
import { useMessage } from '/@/hooks/web/useMessage';
import { quickEntries } from '../mockData';
import type { QuickEntry } from '../mockData';

const CardGrid = Card.Grid;
const { createMessage } = useMessage();

function handleClick(item: QuickEntry) {
  if (item.path) {
    // 后续对接路由跳转
    // router.push(item.path);
    createMessage.success(`即将跳转至：${item.title}`);
  }
}
</script>

<style lang="less" scoped>
.quick-card {
  border-radius: 8px;
  height: 100%;

  :deep(.ant-card-body) {
    padding: 0;
  }
}

.quick-grid-item {
  width: 25%;
  padding: 20px 8px;
  cursor: pointer;
  transition: all 0.2s;
  border-radius: 0;

  &:hover {
    background: #f0f5ff;
    transform: translateY(-1px);
  }
}

.quick-item-inner {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.quick-icon-wrap {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.quick-title {
  font-size: 13px;
  color: rgba(0, 0, 0, 0.65);
}
</style>
