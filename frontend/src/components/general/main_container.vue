<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: "MainComponent",
  components: {
    // eslint-disable-next-line vue/no-unused-component
  }, props: {negative: Boolean}
})
</script>

<template>
  <div class="container mx-auto px-6 text-white font-primary text-[16px]" :class="{negative}">
    <slot></slot>
  </div>
</template>

<style scoped>
.main-container {
  padding-bottom: 20vh;
  box-sizing: border-box;
  margin: 0;
}
.negative {
  margin-top: -133px;
}
/*html { overflow-y: auto !important; }*/
</style>
