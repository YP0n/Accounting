<template>
  <div class="loader" />
</template>

<script>
export default {
  name: 'MainLoader',
}
</script>

<style lang="scss">
@import '@/css/variables';

.loader {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba($substrate, 0.7);

  &:after {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 50px;
    height: 50px;
    transform: translate(-50%, -50%);
    border: 8px solid $button-hover;
    border-top-color: transparent;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    content: '';
  }

  @keyframes spin {
    from {transform: rotate(0deg);}
    to {transform: rotate(360deg);}
  }
}
</style>