<template>
  <j-modal title="账单明细" :width="1200" :visible="visible" :footer="null" @cancel="handleCancel" destroyOnClose>
    <ElderBillDetailList v-if="visible" :billInfo="billInfo" />
  </j-modal>
</template>

<script lang="ts" setup>
  import { ref, defineExpose } from 'vue';
  import JModal from '/@/components/Modal/src/JModal/JModal.vue';
  import ElderBillDetailList from '../ElderBillDetailList.vue';

  const visible = ref<boolean>(false);
  const billInfo = ref<Recordable>({});

  function open(record: Recordable) {
    billInfo.value = record;
    visible.value = true;
  }

  function handleCancel() {
    visible.value = false;
  }

  defineExpose({
    open,
  });
</script>
