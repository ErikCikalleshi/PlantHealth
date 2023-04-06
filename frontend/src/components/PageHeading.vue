<template>
  <h1 :class="computedClasses">
    <slot/>
  </h1>
</template>

<script lang="ts">
export default {
  props: {
    size: {
      type: String,
      default: '[32px]',
    },
    font: {
      type: String,
      default: 'primary',
    },
    textColor: {
      type: String,
      default: 'gray-900',
    },
    darkTextColor: {
      type: String,
      default: 'white',
    },
  },
  computed: {
    computedClasses():string[] {
      return [
        `text-${this.size}`,
        `font-${this.font}`,
        `leading-none`,
        `tracking-tight`,
        `text-${this.textColor}`,
        `dark:text-${this.darkTextColor}`,
      ];
    },
  },
};
</script>
