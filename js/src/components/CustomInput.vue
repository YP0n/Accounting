<template>
  <div
    class="custom-input"
    :class="{'has-error': error}"
  >
    <label
      v-if="label"
      :for="id"
    >
      {{ label }}
    </label>
    <input
      id="id"
      :type="type"
      autocomplete="off"
      @input="changeField"
      @change="changeField"
    />
    <span v-if="error" class="error">
      {{ error }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'CustomInput',

  props: {
    id: {
      type: String,
      default: '',
      required: true
    },
    label: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    value: {
      type: String,
      default: ''
    },
    error: {
      type: String,
      default: ''
    },
  },

  methods: {
    changeField(e) {
      const value = e.target.value

      if(value !== this.value) {
        this.$emit('change-field', {
          fieldName: this.id,
          value
        })
      }
    }
  }
}
</script>

<style lang="scss">
@import "../css/variables.scss";

.custom-input {
  padding: 0 0 20px;
  overflow: hidden;
  position: relative;

  &.has-error input {
    border-color: $error;
  }

  input {
    float: left;
    width: 100%;
    height: 40px;
    padding: 10px;
    color: $main;
    outline: none;
    background: $field-background;
    border: solid $main;
    border-width: 0 0 1px;
    transition: border-color 0.3s, background 0.3s;

    &:focus {
      background: $alt-field-background;
    }
  }
}
</style>