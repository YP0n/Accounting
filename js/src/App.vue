<template>
  <expenses />
</template>

<script>
import Expenses from './pages/Expenses/Expenses.vue'

export default {
  name: 'App',

  components: {
    Expenses
  }
}
</script>

<style lang="scss">
@import './css/main.scss';
</style>
