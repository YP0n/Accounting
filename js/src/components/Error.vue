<template>
  <transition name="fade">
    <span v-if="error" class="error">
      {{ error }}
    </span>
  </transition>
</template>

<script>
export default {
  name: 'Error',

  props: {
    error: {
      type: String,
      default: ''
    }
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.error {
  display: block;
  margin: 0 0 -15px;
  font-size: 12px;
  font-style: italic;
  color: $error;
}
</style>