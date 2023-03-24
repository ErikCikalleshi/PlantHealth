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
      <div class="wrapper">
        <div class="logo">
          <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
        </div>
      <div class="flex navigation">
        <div class="justify-evenly">
        <p class="text-white font-primary text-[20px]">Documentation:</p>
        <div class="stack text-white font-primary">
          <p>- All categories</p>
          <p>- Product information portal</p>
          <p>- Datasheets</p>
        </div>
        </div>
        <div class="justify-evenly">
          <p class="text-white font-primary text-[20px]">Company:</p>
          <div class="stack text-white font-primary font-weight-light">
            <p>- Terms of Services</p>
            <p>- Privacy Policy</p>
            <p>- GDPR Protection</p>
            <p>- Cookies Policy</p>
          </div>
        </div>
        <div class="justify-evenly">
          <p class="text-white font-primary text-[20px]">Services:</p>
          <div class="stack text-white font-primary font-weight-light">
            <p>- Products</p>
            <p>- Software</p>
            <p>- Hardware</p>
          </div>
        </div>
      </div>
      </div>
    </div>
</template>

<style scoped>
.footer-view {
  background-color: #040A0F;
}

.logo {
  flex: 1;
}

.navigation {
  flex:3;
  justify-content: space-evenly
}
.stack {
  display: flex;
  flex-direction: column;
}

.wrapper {
  width: 80%;
  margin: 7vh auto;
  align-items: center;
  display: flex;
}
html { overflow-y: auto !important; }

</style>
