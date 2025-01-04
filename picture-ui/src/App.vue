<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
</script>

<template>
  <a-config-provider
    :theme="{
      token: {
        colorPrimary: '#2f54eb',
      },
    }"
  >
    <BasicLayout />
  </a-config-provider>

</template>

<style scoped>

</style>
