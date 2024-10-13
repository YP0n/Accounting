<template>
  <div class="data-table-item">
    <div class="data-table-cell">
      <date-picker
        :class="{ readonly: !isEditing}"
        :dates="editingExpense.dateExpensePersonal"
        :error="error"
        @change="({ startDate }) => changeField('dateExpensePersonal', startDate)"
      />
    </div>
    <div class="data-table-cell">
      <custom-input
        :value="editingExpense.foodExpense"
        :readonly="!isEditing"
        @change-field="value => changeField('foodExpense', value)"
      />
    </div>
    <div class="data-table-cell">
      <custom-input
        :value="editingExpense.utilityExpense"
        :readonly="!isEditing"
        @change-field="value => changeField('utilityExpense', value)"
      />
    </div>
    <div class="data-table-cell">
      <custom-input
        :value="editingExpense.otherExpense"
        :readonly="!isEditing"
        @change-field="value => changeField('otherExpense', value)"
      />
    </div>
    <div class="data-table-cell">
      <action-buttons
        :is-editing="isEditing"
        @set-editing="setEditingExpense"
        @cancel-editing="cancelEditingExpense"
        @remove="removeExpense"
        @save="updateExpense"
      />
    </div>
  </div>
</template>

<script>
import DatePicker from '@/components/DatePicker'
import CustomInput from '@/components/CustomInput'
import ActionButtons from '@/components/ActionButtons'

import { DATE_REGEX } from '@/constants'

export default {
  name: 'SpendingTableItem',

  components: {
    DatePicker,
    CustomInput,
    ActionButtons
  },

  dateRegex: DATE_REGEX,

  props: {
    expense: {
      type: Object,
      default: () => ({})
    },
    isEditing: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      editingExpense: {},
      error: ''
    }
  },

  created() {
    this.init()
  },

  methods: {
    init() {
      this.editingExpense = JSON.parse(JSON.stringify(this.expense))
    },

    changeField(fieldName, value) {
      this.editingExpense[fieldName] = value
    },

    setEditingExpense() {
      this.$emit('set-editing-expense-id', this.expense.id)
    },

    cancelEditingExpense() {
      this.init()
      this.$emit('set-editing-expense-id', '')
    },

    updateExpense() {
      const isValid = this.validation()

      if(isValid) {
        this.$emit('update-expense', this.editingExpense)
        this.cancelEditingExpense()
      }
    },

    removeExpense() {
      this.$emit('remove-expense', this.expense.id)
    },

    validation() {
      this.error = ''

      if (!this.editingExpense.dateExpensePersonal) {
        this.error = 'Не вказана дата'
        return false
      }

      if (!this.$options.dateRegex.test(this.editingExpense.dateExpensePersonal)) {
        this.error = 'Невірний формат дати'
        return false
      }

      return true
    },
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.data-table-item {
  display: table;
  table-layout: fixed;
  width: 100%;
  background: $field-background;

  &:nth-child(odd) {
    background: $alt-field-background;
  }

  .data-table-cell {
    padding: 5px 10px;
  }

  .date-picker-wrapper {
    width: 100%;
    padding: 0;

    .dp--clear-btn {
      padding: 10px 0 10px 10px;
    }

    &.readonly {
      pointer-events: none;

      input {
        border-color: transparent;
      }

      .dp--clear-btn {
        display: none;
      }
    }
  }

  .dp__input_icons {
    padding: 0;
  }

  .dp__outer_menu_wrap {
    left: -10px !important;
  }

  .dp__arrow_top,
  .dp__arrow_bottom {
    left: 65px;
  }

  .custom-input {
    padding: 0;

    input {
      padding: 10px 0;
      background: transparent;

      &[readonly] {
        border-color: transparent;
      }
    }

    &.has-error input {
      color: $error;
    }
  }

  .date-picker input {
    padding-left: 26px;
  }

  .text {
    display: none;
  }

  &.not-editable {
    .custom-input input {
      border-color: transparent;
    }
    //.text {
    //  display: block;
    //}
    //
    //.custom-input {
    //  display: none;
    //}
  }
}
</style>