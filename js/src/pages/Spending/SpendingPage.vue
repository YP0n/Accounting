<template>
  <div
      class="spending"
      :class="{ 'single-column': !hasExpenses }"
  >
    <div class="column">
      <form
        novalidate
        @submit="sendSpending"
      >
        <h2>Додати витрати</h2>
        <custom-input
          id="foodExpense"
          label="Їжа"
          :value="foodExpense"
          :error="errors.foodExpense"
          @change-field="changeField"
        />
        <custom-input
          id="utilityExpense"
          label="Комунальні витрати"
          :value="utilityExpense"
          :error="errors.utilityExpense"
          @change-field="changeField"
        />
        <custom-input
          id="otherExpense"
          label="Інші витрати"
          :value="otherExpense"
          :error="errors.otherExpense"
          @change-field="changeField"
        />
        <custom-input
          id="dateExpensePersonal"
          label="Дата"
          type="date"
          :value="dateExpensePersonal"
          :error="errors.dateExpensePersonal"
          @change-field="changeField"
        />
        <button
          class="button"
          type="submit"
        >
          Додати
        </button>
      </form>
    </div>
    <div class="column" v-if="hasExpenses">
      <h2>Список витрат</h2>
<!--      <table class="data-table">-->
<!--        <thead>-->
<!--          <tr>-->
<!--            <th>Їжа</th>-->
<!--            <th>Комунальні</th>-->
<!--            <th>Інші</th>-->
<!--            <th>Дата</th>-->
<!--            <th></th>-->
<!--          </tr>-->
<!--        </thead>-->
<!--        <tbody>-->
<!--          <tr-->
<!--              v-for="expense in expenses"-->
<!--              :key="expense.id"-->
<!--              :class="{ 'not-editable': !checkIsEditingExpense(expense.id) }"-->
<!--          >-->
<!--            <td>-->
<!--              {{ !checkIsEditingExpense(expense.id) }}-->
<!--              <custom-input-->
<!--                :id="`${expense.id}-${expense.foodExpense}`"-->
<!--                :value="expense.foodExpense"-->
<!--                @change-field="changeExpenseField()"-->
<!--              />-->
<!--            </td>-->
<!--            <td>-->
<!--              <custom-input-->
<!--                :id="`${expense.id}-${expense.utilityExpense}`"-->
<!--                :value="expense.utilityExpense"-->
<!--                @change-field="changeExpenseField"-->
<!--              />-->
<!--            </td>-->
<!--            <td>-->
<!--              <custom-input-->
<!--                :id="`${expense.id}-${expense.otherExpense}`"-->
<!--                :value="expense.otherExpense"-->
<!--                @change-field="changeExpenseField"-->
<!--              />-->
<!--            </td>-->
<!--            <td>-->
<!--              <custom-input-->
<!--                :id="`${expense.id}-${expense.dateExpensePersonal}`"-->
<!--                :value="expense.dateExpensePersonal"-->
<!--                type="date"-->
<!--                @change-field="changeExpenseField"-->
<!--              />-->
<!--            </td>-->
<!--            <td>-->
<!--              <action-buttons-->
<!--                :is-editing="checkIsEditingExpense(expense.id)"-->
<!--                @set-editing="setIsEditingExpense(expense.id)"-->
<!--                @cancel-editing="setIsEditingExpense('')"-->
<!--                @remove="removeExpense(expense.id)"-->
<!--                @save="updateExpense(expense.id)"-->
<!--              />-->
<!--            </td>-->
<!--          </tr>-->
<!--        </tbody>-->
<!--      </table>-->
      <spending-table
        :expenses="expenses"
        @update-expense="updateExpense"
        @remove-expense="removeExpense"
      />
    </div>

    <teleport to="body">
      <main-loader v-if="isLoading" />
    </teleport>
  </div>
</template>

<script>
import axios from 'axios'

import CustomInput from '@/components/CustomInput'
// import ActionButtons from '@/components/ActionButtons'
import SpendingTable from './SpendingTable'
import MainLoader from '@/components/MainLoader'

import { hardcodedExpenses } from '../../../hardcodedData'

export default {
  name: 'SpendingPage',

  components: {
    CustomInput,
    // ActionButtons,
    SpendingTable,
    MainLoader
  },

  data() {
    return {
      foodExpense: '',
      utilityExpense: '',
      otherExpense: '',
      dateExpensePersonal: '',
      expenseType: {},
      expenses: [], //Масив для збереження списку витрат
      editingExpenseId: '',
      errors: {},
      isLoading: false
    }
  },

  dateRegex: /^\d{4}-\d{2}-\d{2}$/,

  computed: {
    hasExpenses() {
      return this.expenses && this.expenses.length
    }
  },

  mounted() {
    //Викликаємо GET запит при завантаженні компонентів
    this.getAllExpenses();
  },

  methods: {
    // setIsEditingExpense(id) {
    //   console.log('setIsEditingExpense: ', id);
    //   this.editingExpenseId = id
    // },
    //
    // checkIsEditingExpense(id) {
    //   return this.editingExpenseId === id
    // },

    async updateExpense(expense) {
      console.log('updateExpense expense: ', expense);
      try {
        this.isLoading = true

        // TODO: Make api for the update expense
        // await axios.put('/personal_expenses/api/expenses', expense)
        await this.getAllExpenses()
      } finally {
        this.isLoading = false
      }
    },

    async removeExpense(id) {
      console.log('removeExpense id: ', id);
      try {
        this.isLoading = true
        console.log('Removed expense id: ', id);
        // TODO: Make api for the delete expense by id
        // await axios.delete('/personal_expenses/api/expenses', expenseId)
        await this.getAllExpenses()
      } finally {
        this.isLoading = false
      }
    },

    async getAllExpenses() {
      console.log('getAllExpenses', hardcodedExpenses);
      try {
        this.isLoading = true

        // const response = await axios.get('/personal_expenses/api/expenses')

        // this.expenses = response.data;
        this.expenses = hardcodedExpenses;
        console.log('Список витрат успішно отриманий:', this.expenses);
      } catch(error) {
        console.error('Помилка при отриманні списку витрат:', error);
      } finally {
        this.isLoading = false
      }
    },

    changeField({ fieldName, value }) {
      this[fieldName] = value
    },

    validateDate() {
      this.errors.dateExpensePersonal = ''

      if (!this.dateExpensePersonal) {
        this.errors.dateExpensePersonal = 'Не вказана дата'
        return false
      }

      if (!this.$options.dateRegex.test(this.dateExpensePersonal)) {
        this.errors.dateExpensePersonal = 'Невірний формат дати'
        return false
      }

      return true
    },

    async sendSpending(e) {
      e.preventDefault();

      const isValid = this.validateDate()

      if (!isValid) {
        return
      }

      try {
        this.isLoading = true

        await axios.post('/personal_expenses/api/expenses', {
          foodExpense: this.foodExpense,
          utilityExpense: this.utilityExpense,
          otherExpense: this.otherExpense,
          dateExpensePersonal: this.dateExpensePersonal,
          expenseType: this.expenseType.value
        })

        this.getAllExpenses()
      } catch (error) {
        console.error('Помилка при додаванні витрати:', error);
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>

<style lang="scss">
@import "../../css/variables";

.spending {
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

.data-table {
  width: 100%;
  border-collapse:collapse;
  border-spacing:0;

  tr {
    background: $alt-field-background;
  }

  tbody tr {
    &:nth-child(odd) {
      background: $field-background;
    }
  }

  th,
  td {
    padding: 10px;

    &:last-child {
      width: 100px;
    }
  }

  th {
    text-align: left;
    border-bottom: solid 1px $main;

    &:nth-child(1) {
      width: 100px;
    }
  }
}

@media (max-width: 1024px) {
  .spending {
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

      &:before {
        display: block;
        border-top: solid 1px $main;
        padding: 20px 0 0;
        content: '';
      }
    }

    &.single-column .column:last-child:before {
      border: 0;
    }
  }
}
</style>
