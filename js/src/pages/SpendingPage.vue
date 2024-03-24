<template>
  <div class="spending">
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
      <custom-drop
        id="expenseType"
        label="Tип витрат"
        :list="$options.expenseTypeList"
        :selected="expenseType"
        @select="changeField"
      />
      <button
        class="button"
        type="submit"
      >
        Додати
      </button>
    </form>

    <hr class="divider">

    <template v-if="expenses && expenses.length">
      <h2>Список витрат</h2>
<!--      <ul>-->
<!--        <li v-for="expense in expenses" :key="expense.id">-->
<!--          {{ expense.foodExpense }} - -->
<!--          {{ expense.utilityExpense }} - -->
<!--          {{ expense.otherExpense }} - -->
<!--          {{ expense.dateExpensePersonal }} - -->
<!--          {{ expense.expenseType }}-->
<!--        </li>-->
<!--      </ul>-->
      <table class="data-table">
        <thead>
          <tr>
            <th>Їжа</th>
            <th>Комунальні</th>
            <th>Інші</th>
            <th>Дата</th>
            <th>Тип Витрат</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="expense in expenses" :key="expense.id">
            <td>{{ expense.foodExpense }}</td>
            <td>{{ expense.utilityExpense }}</td>
            <td>{{ expense.otherExpense }}</td>
            <td>{{ expense.dateExpensePersonal }}</td>
            <td>{{ expense.expenseType }}</td>
          </tr>
        </tbody>
      </table>
    </template>

<!--    TODO: Just example. Should be removed-->
<!--    <h2>Список витрат</h2>-->
<!--    <table class="data-table">-->
<!--      <thead>-->
<!--        <tr>-->
<!--          <th>Їжа</th>-->
<!--          <th>Комунальні</th>-->
<!--          <th>Інші</th>-->
<!--          <th>Дата</th>-->
<!--          <th>Тип Витрат</th>-->
<!--        </tr>-->
<!--      </thead>-->
<!--      <tbody>-->
<!--        <tr>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--          <td>1</td>-->
<!--        </tr>-->
<!--      </tbody>-->
<!--    </table>-->

    <teleport to="body">
      <main-loader v-if="isLoading" />
    </teleport>
  </div>
</template>

<script>
import axios from 'axios'

import CustomInput from '@/components/CustomInput'
import CustomDrop from '@/components/CustomDrop'
import MainLoader from '@/components/MainLoader'

export default {
  name: 'SpendingPage',

  components: {
    CustomInput,
    CustomDrop,
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
      errors: {},
      isLoading: false
    }
  },

  expenseTypeList: [
    {
      text: 'Їжа',
      value: 'FOOD'
    }, {
      text: 'Комунальні витрати',
      value: 'UTILITY'
    }, {
      text: 'Інші витрати',
      value: 'Other'
    }
  ],

  dateRegex: /^\d{4}-\d{2}-\d{2}$/,

  mounted() {
    //Викликаємо GET запит при завантаженні компонентів
    this.getAllExpenses();
  },

  methods: {
    async getAllExpenses() {
      try {
        this.isLoading = true

        const response = await axios.get('/personal_expenses/api/expenses')

        this.expenses = response.data;
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

    reset() {
      this.foodExpense = ''
      this.utilityExpense = ''
      this.otherExpense = ''
      this.dateExpensePersonal = ''
      this.expenseType = ''
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

        this.reset()
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
@import "../css/variables.scss";

.spending {
  width: 600px;
  margin: auto;
  padding: 20px;
  background: $substrate;

  form {
    padding: 0 0 20px;
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
  }

  th {
    text-align: left;
    border-bottom: solid 1px $main;
  }
}
</style>
