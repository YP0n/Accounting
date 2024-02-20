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
        <option value="Їжа">Їжа</option>
        <option value="Комунальні витрати">Комунальні витрати</option>
        <option value="Інші">Інші витрати</option>
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

    sendSpending(e) {
      e.preventDefault();
      axios.post('/personal_expenses/api/expenses', {
        foodExpense: this.foodExpense,
        utilityExpense: this.utilityExpense,
        otherExpense: this.otherExpense,
        datePersonalExpense: this.dateExpensePersonal,
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
