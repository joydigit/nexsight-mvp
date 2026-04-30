<template>
  <j-modal :title="title" :width="1200" :visible="visible" @cancel="handleCancel" :footer="null">
    <ElderCustomerCheckinFeeList :checkinId="checkinId" />
  </j-modal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import ElderCustomerCheckinFeeList from '../ElderCustomerCheckinFeeList.vue';
  import JModal from '/@/components/Modal/src/JModal/JModal.vue';

  const title = ref<string>('费用配置');
  const visible = ref<boolean>(false);
  const checkinId = ref<string>('');

  /**
   * 打开弹框
   */
  function open(id: string) {
    checkinId.value = id;
    visible.value = true;
  }

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
    checkinId.value = '';
  }

  defineExpose({
    open,
  });
</script>

<style lang="less" scoped></style>
