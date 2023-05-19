<template>
  <h1 :class="computedClasses">
    <slot/>
  </h1>
</template>

<script lang="ts">
export default {
  computed: {
    computedClasses():string[] {
      return [
        "text-[32px]",
        "font-primary",
        "leading-none",
        "tracking-tight",
        "text-gray-0",
      ];
    },
  },
};
</script>
