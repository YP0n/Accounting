<template>
  <div class="date-picker-wrapper">
    <VueDatePicker
      class="date-picker custom-input"
      :class="{ 'has-error': error }"
      :model-value="currentDates"
      :format="formatDate"
      :range="range"
      :multi-calendars="multiCalendars"
      :placeholder="placeholder"
      @update:model-value="changeDate"
    />
    <error :error="error" />
  </div>
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { isArray } from 'lodash'

import Error from '@/components/Error'

import { getFormattedDate } from '@/utils'

export default {
  name: 'DatePicker',

  components: {
    VueDatePicker,
    Error
  },

  props: {
    dates: {
      type: [String, Object, Array],
      default: ''
    },
    range: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    },
    error: {
      type: String,
      default: ''
    },
  },

  data() {
    return {
      currentDates: []
    }
  },

  computed: {
    multiCalendars() {
      return this.range ? { solo: true } : null
    }
  },

  watch: {
    dates: {
      handler: function(dates) {
        if(isArray(dates)) {
          this.currentDates = [this.parseDate(dates[0]), this.parseDate(dates[1])]
        } else {
          this.currentDates = this.parseDate(dates)
        }
      },
      immediate: true
    }
  },

  methods: {
    getPickerFormattedDate(date) {
      if(date) {
        if(this.range) {
          let startDate = date[0]
          let endDate = date[1]

          startDate = getFormattedDate(startDate)
          endDate = getFormattedDate(endDate)

          return {
            fullStringDate: `${startDate} ${endDate ? '-' : ''} ${endDate ? endDate : ''}`,
            startDate,
            endDate
          }
        } else {
          const startDate = getFormattedDate(date)

          return {
            fullStringDate: startDate,
            startDate
          }
        }
      }

      return {}
    },

    parseDate(dateString) {
      if(!dateString) return

      const [day, month, year] = dateString.split('/').map(Number);

      return new Date(year, month - 1, day);
    },

    formatDate(date) {
      const { fullStringDate } = this.getPickerFormattedDate(date)

      return fullStringDate
    },

    changeDate(date) {
      const { startDate, endDate } = this.getPickerFormattedDate(date)

      this.$emit('change', { date, startDate, endDate })
    }
  }
}
</script>

<style lang="scss">
@import '@/css/variables';

.date-picker-wrapper {
  padding: 0 0 20px;

  .custom-input {
    padding: 0;
  }
}

.date-picker {
  .dp__input_icon {
    transform: translateY(-55%);
  }

  &.custom-input input {
    padding: 10px 10px 10px 36px;
    border-radius: 0;
  }

  .dp__input:hover:not(.dp__input_focus) {
    border-color: $main;
  }

  .dp--tp-wrap {
    display: none;
  }

  .dp__selection_preview {
    min-width: 160px;
    font-size: 14px;
  }

  .dp__action_button {
    width: 100%;
    height: auto;
    padding: 12px;
    margin: 0 0 0 10px;
    font: 500 14px/1.2 Arial, Verdana, sans-serif;
    color: #fff;
    text-transform: uppercase;
    background: $button;
    transition: background 0.3s;
    box-shadow: $box-shadow;
    border: 0;
    border-radius: 0;
    cursor: pointer;

    &:hover {
      background: $button-hover;
    }

    &.disabled:hover {
      background: $button;
    }

    &.dp__action_cancel {
      color: $gray;
      background: $white;

      &:hover {
        background: $light-gray;
      }
    }
  }
}
</style>