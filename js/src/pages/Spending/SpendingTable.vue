<template>
  <table class="data-table">
    <thead>
    <tr>
      <th>Їжа</th>
      <th>Комунальні</th>
      <th>Інші</th>
      <th>Дата</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <spending-table-item
      v-for="expense in expenses"
      :key="expense.id"
      :expense="expense"
      :is-editing="checkIsEditingExpense(expense.id)"
      @set-editing-expense-id="setEditingExpenseId"
      @update-expense="updateExpense"
      @remove-expense="removeExpense"
      />
    </tbody>
  </table>
</template>

<script>
import SpendingTableItem from './SpendingTableItem'

export default {
  name: 'SpendingTable',

  components: {
    SpendingTableItem
  },

  data() {
    return {
      editingExpenseId: null
    }
  },

  props: {
    expenses: {
      type: Array,
      default: () => ([])
    }
  },

  methods: {
    setEditingExpenseId(id) {
      console.log('setIsEditingExpense: ', id);
      this.editingExpenseId = id
    },

    checkIsEditingExpense(id) {
      return this.editingExpenseId === id
    },

    updateExpense(expense) {
      this.$emit('update-expense', expense)
    },

    removeExpense(expenseId) {
      this.$emit('remove-expense', expenseId)
    }
  }
}
</script>

<style lang="scss">

</style>