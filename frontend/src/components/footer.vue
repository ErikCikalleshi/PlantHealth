<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "FooterView",
  components: {
    // eslint-disable-next-line vue/no-unused-components
  },
})
</script>

<template>
    <div class="footer-view pb-[0px]">
      <div class="wrapper">
        <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
      </div>
    </div>
</template>

<style scoped>
.footer-view,h1 {
  display:flex;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 20vh;
  background-color: #040A0F;
  background-size: 1920px 323px;
}

.wrapper {
  width: 80%;
  margin: 0 auto;
}
html { overflow-y: auto !important; }

</style>
