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
  <div class="footer-view display-flex h-[30vh] w-[100vw]">
    <div class="wrapper flex">
      <div class="logo h-full flex-1">
        <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
      </div>
      <div class="text-white flex-1 ml-60">
        <p class="font-primary text-[20px]">Documentation:</p>
        <p class="font-light text-[16px]">- All categories</p>
        <p class="font-light text-[16px]">- Product information portal</p>
        <p class="font-light text-[16px]">- Datasheets</p>
      </div>
      <div class="text-white flex-1">
        <p class="font-primary text-[20px]">Company:</p>
        <p class="font-light text-[16px]">- Terms of Services</p>
        <p class="font-light text-[16px]">- Privacy Policy</p>
        <p class="font-light text-[16px]">- GDPR Protection</p>
        <p class="font-light text-[16px]">- Cookies Policy</p>
      </div>
      <div class="text-white flex-1">
        <p class="font-primary text-[20px]">Services:</p>
        <p class="font-light text-[16px]">- Products</p>
        <p class="font-light text-[16px]">- Software</p>
        <p class="font-light text-[16px]">- Hardware</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.footer-view {
  background-color: #040A0F;
}

.logo img {
  width:270px;
  max-width: unset;
  margin-bottom: -20px;
}

.wrapper {
  width: 80%;
  margin: 7vh auto;
}

html {
  overflow-y: auto !important;
}

</style>