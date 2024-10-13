<template>
  <form
    novalidate
    @submit="addExpense"
  >
    <h2>Додати витрати</h2>
    <date-picker
      :dates="dateExpense"
      :error="errors.dateExpense"
      @change="({ startDate }) => changeField('dateExpense', startDate)"
    />
    <custom-input
      id="foodExpense"
      label="Їжа"
      :value="foodExpense"
      :error="errors.foodExpense"
      @change-field="value => changeField('foodExpense', value)"
    />
    <custom-input
      id="utilityExpense"
      label="Комунальні витрати"
      :value="utilityExpense"
      :error="errors.utilityExpense"
      @change-field="value => changeField('utilityExpense', value)"
    />
    <custom-input
      id="otherExpense"
      label="Інші витрати"
      :value="otherExpense"
      :error="errors.otherExpense"
      @change-field="value => changeField('otherExpense', value)"
    />
    <button
      class="button"
      type="submit"
    >
      Додати
    </button>
  </form>
</template>

<script>
import DatePicker from '@/components/DatePicker'
import CustomInput from '@/components/CustomInput'

import { getFormattedDate } from '@/utils'

import { DATE_REGEX } from '@/constants'

export default {
  name: 'AddSpending',

  components: {
    DatePicker,
    CustomInput
  },

  dateRegex: DATE_REGEX,

  data() {
    return {
      dateExpense: getFormattedDate(new Date()),
      foodExpense: '',
      utilityExpense: '',
      otherExpense: '',
      errors: {},
    }
  },

  methods: {
    changeField(fieldName, value) {
      this[fieldName] = value
    },

    validate() {
      this.errors.dateExpense = ''

      if (!this.dateExpense) {
        this.errors.dateExpense = 'Не вказана дата'
        return false
      }

      if (!this.$options.dateRegex.test(this.dateExpense)) {
        this.errors.dateExpense = 'Невірний формат дати'
        return false
      }

      return true
    },

    async addExpense(e) {
      e.preventDefault();

      const isValid = this.validate()

      if (!isValid) {
        return
      }

      this.$emit('add-expense', {
        dateExpense: this.dateExpense,
        foodExpense: this.foodExpense,
        utilityExpense: this.utilityExpense,
        otherExpense: this.otherExpense
      })
    }
  }
}
</script>

<style scoped>

</style>