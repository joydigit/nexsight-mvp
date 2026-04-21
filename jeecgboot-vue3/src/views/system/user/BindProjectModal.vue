<template>
  <BasicModal v-bind="$attrs" @register="registerModal" title="绑定项目" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts" name="BindProjectModal" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formBindProjectSchema } from './user.data';
  import { bindProjectUserMethod,getProjectUserInfoMethod } from './user.api';
  // 声明Emits
  const emit = defineEmits(['success', 'register']);

  const userId = ref('');
  //表单配置
  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    schemas: formBindProjectSchema,
    showActionButtonGroup: false,
  });
  //表单赋值
  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    //重置表单
    await resetFields();
    setModalProps({ confirmLoading: false });
    userId.value = data.id
    let projectUsers = await getProjectUserInfoMethod({userId: data.id});
    console.log(projectUsers)
    if (projectUsers && projectUsers.projectIds) {
      data.projectIdList = projectUsers.projectIds.split(',')
    }
    //表单赋值
    await setFieldsValue({ ...data });
  });
  //表单提交事件
  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });
      //提交表单
      values.userId = userId.value;
      values.projectIds = values.projectIdList.toString();
      await bindProjectUserMethod(values);
      //关闭弹窗
      closeModal();
      //刷新列表
      emit('success');
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>
