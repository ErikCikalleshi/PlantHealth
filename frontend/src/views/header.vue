<script lang="ts">
import {defineComponent} from "vue";
import { OhVueIcon } from "oh-vue-icons";

export default defineComponent({
  name: "HeaderView",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    OhVueIcon
  },
})
</script>

<template>
  <v-app>
    <div class="header-view h-[100vh] w-[100vw]">
      <div class="flex text-white font-primary text-[16px] pl-[175px] pt-[86px]">
        <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
        <div class="pt-[5px] flex justify-space-between w-[100vw]">
      <p class="pl-[254px]">Home</p>
      <p>About</p>
      <p>Services</p>
      <p>Prices</p>
      <p class="pr-[258.75px]">Contact</p>
      </div>
        <div class="pt-[1.5px] pr-[244.75px]"> <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
        </div>
      </div>
    </div>

  </v-app>
</template>

<style>
.header-view {
  background-image: url("@/assets/header/background.png");
}
html { overflow-y: auto !important; }

</style>
