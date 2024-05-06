<template>
  <tr
    class="spending-table-item"
    :class="{ 'not-editable': !isEditing }"
  >
    <td>
      <span class="text">{{ editingExpense.foodExpense }}</span>
      <custom-input
        :id="`${editingExpense.id}-${editingExpense.foodExpense}`"
        :value="editingExpense.foodExpense"
        @change-field="({ value }) => changeField('foodExpense', value)"
      />
    </td>
    <td>
      <span class="text">{{ editingExpense.utilityExpense }}</span>
      <custom-input
        :id="`${editingExpense.id}-${editingExpense.utilityExpense}`"
        :value="editingExpense.utilityExpense"
        @change-field="({ value }) => changeField('utilityExpense', value)"
      />
    </td>
    <td>
      <span class="text">{{ editingExpense.otherExpense }}</span>
      <custom-input
        :id="`${editingExpense.id}-${editingExpense.otherExpense}`"
        :value="editingExpense.otherExpense"
        @change-field="({ value }) => changeField('otherExpense', value)"
      />
    </td>
    <td>
      <span class="text">{{ editingExpense.dateExpensePersonal }}</span>
      <custom-input
        :id="`${editingExpense.id}-${editingExpense.dateExpensePersonal}`"
        :value="editingExpense.dateExpensePersonal"
        type="date"
        @change-field="({ value }) => changeField('dateExpensePersonal', value)"
      />
    </td>
    <td>
      <action-buttons
        :is-editing="isEditing"
        @set-editing="setEditingExpense"
        @cancel-editing="cancelEditingExpense"
        @remove="removeExpense"
        @save="updateExpense"
      />
    </td>
  </tr>
</template>

<script>
import CustomInput from '@/components/CustomInput'
import ActionButtons from '@/components/ActionButtons'

export default {
  name: 'SpendingTableItem',

  components: {
    CustomInput,
    ActionButtons
  },

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
      editingExpense: {}
    }
  },

  created() {
    this.init()
  },

  watch: {
    isEditing() {
      this.init()
    }
  },

  methods: {
    init() {
      console.log('init: ', this.expense);
      this.editingExpense = JSON.parse(JSON.stringify(this.expense))
    },

    changeField(fieldName, value) {
      console.log('changeField fieldName, value: ', fieldName, value);
      this.editingExpense[fieldName] = value
    },

    setEditingExpense() {
      console.log('setEditingExpense this.editingExpense: ', this.editingExpense);
      this.$emit('set-editing-expense-id', this.expense.id)
    },

    cancelEditingExpense() {
      this.$emit('set-editing-expense-id', '')
    },

    updateExpense() {
      this.$emit('update-expense', this.editingExpense)
    },

    removeExpense() {
      this.$emit('remove-expense', this.expense.id)
    }
  }
}
</script>

<style lang="scss">
.spending-table-item {
  .text {
    display: none;
    padding: 12px 10px;
  }

  .custom-input {
    padding: 0;
  }

  &.not-editable {
    .text {
      display: block;
    }

    .custom-input {
      display: none;
    }
  }
}
</style>