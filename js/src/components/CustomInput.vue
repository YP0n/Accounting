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
      :value="value"
      :readonly="readonly"
      :disabled="disabled"
      autocomplete="off"
      @input="changeField"
      @change="changeField"
    />
    <error :error="error" />
  </div>
</template>

<script>
import Error from '@/components/Error'

export default {
  name: 'CustomInput',

  components: {
    Error
  },

  props: {
    id: {
      type: String,
      default: ''
    },
    label: {
      type: String,
      default: ''
    },
    value: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    readonly: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
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
        this.$emit('change-field', value)
      }
    }
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.custom-input {
  padding: 0 0 20px;

  &.has-error input {
    border-color: $error;
    background: $error-background;
  }

  input {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    line-height: 1.2;
    color: $main;
    outline: none;
    background: $field-background;
    border: solid $main;
    border-width: 0 0 1px;
    transition: border-color .3s, background .3s;

    &:focus {
      background: $alt-field-background;
    }
  }
}
</style>
