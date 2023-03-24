<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "MainComponent",
  components: {
    // eslint-disable-next-line vue/no-unused-component
  }, props:{negative:Boolean}
})
</script>

<template>
  <div class="main-container" :class="{negative}">
    <slot></slot>
  </div>
</template>

<style scoped>

.main-container {
  padding-bottom: 20vh;
  width: 80%;
  margin: 0 auto;
}

.negative {
  margin-top: -100px;
}

html { overflow-y: auto !important; }

</style>
