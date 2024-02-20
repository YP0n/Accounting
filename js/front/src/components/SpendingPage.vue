<template>
  <div>
  <form class="spending" @submit="sendSpending">
    <div class="row">
      <label for="food">
        Їжа:
      </label>
      <input
        id="food"
        type="text"
        v-model="foodExpense"
      />
    </div>
    <div class="row">
      <label for="utility">
        Комунальні витрати:
      </label>
      <input
        id="utility"
        type="text"
        v-model="utilityExpense"
      />
    </div>
    <div class="row">
      <label for="other">
        Інші витрати:
      </label>
      <input
        id="other"
        type="text"
        v-model="otherExpense"
      />
    </div>
    <div class="row">
      <label for="date">
        Дата:
      </label>
      <input
        id="date"
        type="date"
        v-model="dateExpensePersonal"
        @change="validateDate"
      />
    </div>
    <div class="row">
      <label for="type">
        Tип витрат:
      </label>
      <select
        id="type"
        v-model="expenseType"
      >
        <option value="FOOD">Їжа</option>
        <option value="UTILITY">Комунальні витрати</option>
        <option value="OTHER">Інші витрати</option>
      </select>
    </div>
    <button type="submit">Додати</button>
  </form>

    <div>
      <h2>Список витрат</h2>
      <ul>
        <li v-for="expense in expenses" :key="expense.id">
          {{ expense.foodExpense }} - {{ expense.utilityExpense }} - {{ expense.otherExpense }} - {{ expense.dateExpensePersonal }} - {{ expense.expenseType }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SpendingPage',

  data() {
    return {
      foodExpense: '',
      utilityExpense: '',
      otherExpense: '',
      dateExpensePersonal: '',
      expenseType: '',
      expenses: [] //Масив для збереження списку витрат
    }
  },

  mounted() {
    //Викликаємо GET запит при завантаженні компонентів
    this.getAllExpenses();
  },

  methods: {
    getAllExpenses() {
      axios.get('/personal_expenses/api/expenses')
          .then(response => {
            this.expenses = response.data;
            console.log('Список витрат успішно отриманий:', this.expenses);
          })
          .catch(error => {
            console.error('Помилка при отриманні списку витрат:', error);
          });
    },

    validateDate() {
      if (!this.dateExpensePersonal) {
        console.error('Дата не вказана');
        return false; // Повертаємо false, оскільки дата не вказана
      }

      const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
      if (!dateRegex.test(this.dateExpensePersonal)) {
        console.error('Неправильний формат дати');
        return false; // Повертаємо false, оскільки дата має неправильний формат
      }
      // Додаткові перевірки, якщо потрібно
      return true; // Повертаємо true, якщо всі перевірки успішні
    },
    sendSpending(e) {
      e.preventDefault();
      console.log('Дата перед відправленням:', this.dateExpensePersonal); // Виводимо значення дати в консоль
      // Перевірка на null перед викликом методу validateDate()
      if (!this.dateExpensePersonal) {
        console.error('Дата не вказана');
        return; // Виходимо з методу, якщо дата не вказана
      }

      // Виклик методу validateDate() тільки якщо дата не є null
      if (!this.validateDate()) {
        console.error('Неправильний формат дати');
        return; // Виходимо з методу, якщо формат дати неправильний
      }
      axios.post('/personal_expenses/api/expenses', {
        foodExpense: this.foodExpense,
        utilityExpense: this.utilityExpense,
        otherExpense: this.otherExpense,
        dateExpensePersonal: this.dateExpensePersonal,
        expenseType: this.expenseType
      })
          .then(response => {
            console.log(response.data)
      // Очистити поля після успішного додавання витрати
      this.foodExpense = '';
      this.utilityExpense = '';
      this.otherExpense = '';
      this.dateExpensePersonal = '';
      this.expenseType = '';
    })
.catch(error => {
  console.error('Помилка при додаванні витрати:', error);
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
