<template>
  <div class="spending-table-wrapper">
    <div class="spending-table-header">
      <h2>Список витрат</h2>
      <date-picker
        class="spending-table-datepicker"
        :dates="dates"
        :range="true"
        @change="changeExpenseDates"
      />
      <div
        v-if="hasExpenses"
        class="data-table-header"
      >
        <div class="data-table-cell">Дата</div>
        <div class="data-table-cell">Їжа</div>
        <div class="data-table-cell">Комунальні</div>
        <div class="data-table-cell">Інші</div>
        <div class="data-table-cell"></div>
      </div>
    </div>
    <div
      v-if="hasExpenses"
      class="data-table"
    >
      <spending-table-item
        v-for="expense in expenses"
        :key="expense.id"
        :expense="expense"
        :is-editing="checkIsEditingExpense(expense.id)"
        @set-editing-expense-id="setEditingExpenseId"
        @update-expense="updateExpense"
        @remove-expense="removeExpense"
      />
    </div>
    <div
      v-else
      class="data-table-empty"
    >
      Немає даних
    </div>
  </div>
</template>

<script>
import DatePicker from '@/components/DatePicker'
import SpendingTableItem from './SpendingTableItem'

export default {
  name: 'SpendingTable',

  components: {
    DatePicker,
    SpendingTableItem
  },

  props: {
    dates: {
      type: Array,
      default: () => ([])
    },

    expenses: {
      type: Array,
      default: () => ([])
    }
  },

  data() {
    return {
      editingExpenseId: null
    }
  },

  computed: {
    hasExpenses() {
      return this.expenses && this.expenses.length
    }
  },

  methods: {
    changeExpenseDates(dates) {
      this.$emit('change-dates', dates)
    },

    setEditingExpenseId(id) {
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
@import '@/css/variables';

.spending-table-header {
  position: sticky;
  top: 0;
  background: $substrate;
  z-index: 10;
}

.data-table-header {
  display: table;
  table-layout: fixed;
  width: 100%;
  background: $alt-field-background;
  border-bottom: solid 1px $main;
}

.data-table-cell {
  display: table-cell;
  padding: 10px;

  &:first-child,
  &:last-child {
    width: 154px;
  }

  &:last-child {
    width: 144px;
  }
}

.spending-table-datepicker {
  width: 250px;

  .dp__outer_menu_wrap {
    left: 0 !important;
  }

  .dp__arrow_top {
    left: 115px;
  }
}

.data-table-empty {
  font-size: 30px;
  color: #ccc;
  text-align: center;
  padding: 55px 0;
}
</style>