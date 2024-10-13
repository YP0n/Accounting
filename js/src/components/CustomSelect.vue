<template>
  <div
    :id="id"
    class="custom-drop"
    :class="{
      opened: isDropVisible,
      'has-error': error
    }"
  >
    <label v-if="label">
      {{ label }}
    </label>
    <span
      class="custom-drop-opener"
      @click="toggleDrop"
    >
      {{ selected.text }}
    </span>
    <error :error="error" />
    <transition name="fade">
      <ul
        v-if="isDropVisible"
        class="custom-drop-drop"
      >
        <li
          v-for="item in list"
          :key="item.value"
          class="custom-drop-item"
          :class="{ selected: item.value === (selected && selected.value) }"
          @click="select(item)"
        >
          {{ item.text }}
        </li>
      </ul>
    </transition>
  </div>
</template>

<script>
import Error from '@/components/Error'

export default {
  name: 'CustomSelect',

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
    list: {
      type: Array,
      default: () => ([])
    },
    selected: {
      type: Object,
      default: () => ({})
    },
    error: {
      type: String,
      default: ''
    }
  },

  data() {
    return {
      el: null,
      isDropVisible: false
    }
  },

  mounted() {
    this.el = document.getElementById(this.id)
  },

  beforeUnmount() {
    window.removeEventListener('click', this.handleClickOutSide)
  },

  methods: {
    select(item) {
      this.$emit('select', { fieldName: this.id, value: item })
      this.hideDrop()
    },

    handleClickOutSide(e) {
      if (!this.el.contains(e.target)){
        this.hideDrop()
      }
    },

    toggleDrop() {
      this.isDropVisible = !this.isDropVisible

      if (this.isDropVisible) {
        window.addEventListener('click', this.handleClickOutSide)
      } else {
        window.removeEventListener('click', this.handleClickOutSide)
      }
    },

    hideDrop () {
      this.isDropVisible = false
    }
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.custom-drop {
  margin: 0 0 20px;
  position: relative;

  &.has-error {
    border-color: $error;
  }

  &.opened {
    .custom-drop-opener {
      background: $alt-field-background;
    }
  }
}

.custom-drop-opener {
  display: block;
  height: 40px;
  padding: 10px;
  color: $main;
  background: $field-background;
  border-bottom: 1px solid $main;
  transition: border-color 0.3s, background 0.3s;
  cursor: pointer;
}

.custom-drop-drop {
  position: absolute;
  left: 0;
  top: 100%;
  width: 100%;
  margin: 2px 0 0;
  background: $alt-field-background;
  box-shadow: $box-shadow;
  transition: background 0.3s;
}

.custom-drop-item {
  padding: 10px;
  cursor: pointer;

  &:hover,
  &.selected {
    background: $field-background;;
  }
}
</style>