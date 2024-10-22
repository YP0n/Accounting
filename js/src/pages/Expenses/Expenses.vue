<template>
  <div class="expenses">
    <div class="column">
      <add-expenses @add-expense="callAddExpense" />
    </div>
    <div class="column">
      <expenses-table
        :dates="[expenseStartDate, expenseEndDate]"
        :expenses="expenses"
        @change-dates="changeExpensesDates"
        @update-expense="callUpdateExpense"
        @remove-expense="callRemoveExpense"
      />
    </div>

    <teleport to="body">
      <main-loader v-if="isLoading" />
    </teleport>
  </div>
</template>

<script>
import AddExpenses from './_components/AddExpenses'
import ExpensesTable from './_components/ExpensesTable'
import MainLoader from '@/components/MainLoader'

import { getExpenses, addExpense, updateExpense, removeExpense } from '@/Network.js'

import { getFormattedDate } from '@/utils'

export default {
  name: 'Expenses',

  components: {
    AddExpenses,
    ExpensesTable,
    MainLoader
  },

  data() {
    return {
      expenseStartDate: getFormattedDate(new Date()),
      expenseEndDate: getFormattedDate(new Date()),
      expenses: [], //Масив для збереження списку витрат
      isLoading: false
    }
  },

  mounted() {
    //Викликаємо GET запит при завантаженні компонентів
    this.callGetExpenses();
  },

  methods: {
    changeExpensesDates({ startDate, endDate }) {
      this.expenseStartDate = startDate
      this.expenseEndDate = endDate

      this.callGetExpenses()
    },

    async callGetExpenses() {
      if(this.expenseStartDate || this.expenseEndDate) {
        try {
          this.isLoading = true

          this.expenses = await getExpenses({
            expenseStartDate: this.expenseStartDate,
            expenseEndDate: this.expenseEndDate
          })

        } catch(error) {
          console.error('Помилка при отриманні списку витрат:', error);
        } finally {
          this.isLoading = false
        }
      }
    },

    async callAddExpense(data) {
      try {
        this.isLoading = true

        await addExpense(data)
        await this.callGetExpenses()
      } catch (error) {
        console.error('Помилка при додаванні витрати:', error);
      }
    },

    async callUpdateExpense(expense) {
      try {
        this.isLoading = true

        await updateExpense(expense)
        await this.callGetExpenses()
      } catch (e) {
        console.error(e)
      }
    },

    async callRemoveExpense(id) {
      try {
        this.isLoading = true

        await removeExpense(id)
        await this.callGetExpenses()
      } catch (e) {
        console.error(e)
      }
    },
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.expenses {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;

  &:after {
    position: absolute;
    left: 400px;
    top: 0;
    bottom: 0;
    border-left: 1px solid $main;
    content: '';
  }

  .column {
    width: 400px;
    padding: 20px;

    &:last-child {
      position: absolute;
      right: 0;
      top: 0;
      bottom: 0;
      width: calc(100% - 400px);
      overflow-y: auto;
    }
  }
}

@media (max-width: 1024px) {
  .expenses {
    position: static;

    &:after {
      display: none;
    }

    .column,
    .column:last-child {
      position: static;
      width: 100%;
    }

    .column:last-child {
      border: 0;
      overflow-y: visible;

      &:before {
        display: block;
        border-top: solid 1px $main;
        padding: 20px 0 0;
        content: '';
      }
    }
  }
}
</style>
