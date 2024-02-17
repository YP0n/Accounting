<template>
  <form class="spending" @submit="sendSpending">
    <div class="row">
      <label for="food">
        Їжа:
      </label>
      <input
        id="food"
        type="text"
        v-model="food"
      />
    </div>
    <div class="row">
      <label for="food">
        Комунальні витрати:
      </label>
      <input
        id="utility"
        type="text"
        v-model="utility"
      />
    </div>
    <div class="row">
      <label for="other">
        Інші витрати:
      </label>
      <input
        id="other"
        type="text"
        v-model="other"
      />
    </div>
    <div class="row">
      <label for="date">
        Дата:
      </label>
      <input
        id="date"
        type="date"
        v-model="date"
      />
    </div>
    <div class="row">
      <label for="type">
        Tип витрат:
      </label>
      <select
        id="type"
        v-model="type"
      >
        <option value="Їжа">Їжа</option>
        <option value="Напої">Напої</option>
      </select>
    </div>
    <button type="submit">Додати</button>
  </form>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SpendingPage',

  data() {
    return {
      food: '',
      utility: '',
      other: '',
      date: '',
      type: ''
    }
  },

  methods: {
    sendSpending(e) {
      e.preventDefault()
      console.log(this.food ,
          this.utility,
          this.other,
          this.date,
          this.type);

      axios.post('/url', {
        food: this.food ,
        utility: this.utility,
        other: this.other,
        date: this.date,
        type: this.type
      })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
    },

    changeField(fieldName, e) {
      const { value } = e.target
      this[fieldName] = value
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

</style>
