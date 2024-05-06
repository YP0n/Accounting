<template>
  <ul class="action-buttons">
    <template v-if="!isEditing">
      <li>
        <button
          class="fa fa-pencil"
          @click="editing"
        />
      </li>
    </template>
    <template v-else>
      <li>
        <button
          class="fa fa-trash-o"
          @click="remove"
        />
      </li>
      <li>
        <button
          class="fa fa-times"
          @click="cancel"
        />
      </li>
      <li>
        <button
          class="fa fa-check"
          @click="save"
        />
      </li>
    </template>
  </ul>
</template>

<script>
export default {
  name: 'ActionButtons',

  props: {
    isEditing: {
      type: Boolean,
      default: false
    }
  },

  methods: {
    editing() {
      this.$emit('set-editing')
    },

    cancel() {
      this.$emit('cancel-editing')
    },

    remove() {
      this.$emit('remove')
    },

    save() {
      this.$emit('save')
    },
  }
}
</script>

<style lang="scss">
@import "../css/variables";

.action-buttons {
  width: 124px;
  padding: 3px 0 0;
  font-size: 22px;
  text-align: right;

  li {
    display: inline-block;
    border-right: 1px solid $edit;

    &:last-child {
      border: 0;
    }
  }

  button {
    width: 40px;
    height: 30px;
    color: $edit;
    background: none;
    border: 0;
    text-align: center;
    cursor: pointer;

    &:hover {
      color: $main;
    }

    &.fa-trash-o {
      color: $error;

      &:hover {
        color: $error-hover;
      }
    }

    &.fa-check {
      color: $success;

      &:hover {
        color: $success-hover
      }
    }
  }
}
</style>