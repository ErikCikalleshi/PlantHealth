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
    <div class="header-view h-[30vh] w-[100vw]">
      <div class="flex wrapper text-white font-primary text-[16px]">
        <div class="logo">
          <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
        </div>
        <div class="pt-[5px] flex navigation">
      <p class="pl-[254px]">Home</p>
      <p>About</p>
      <p>Services</p>
      <p>Prices</p>
      <p class="pr-[258.75px]">Contact</p>
      </div>
        <div class=" user-icon pt-[1.5px] pr-[244.75px]"> <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
        </div>
      </div>
    </div>
</template>

<style scoped>
.header-view {
  background-image: url("@/assets/header/background.png");
  background-size: cover;
  background-position: center center;
}

.wrapper {
  width: 80%;
  margin: 0 auto;
  align-items: center;
  height: 15vh;
}
.user-icon {
  flex:1;
}

.logo {
  flex:1;
}
.logo img {
  width:270px;
  max-width: unset;
  margin-bottom: -20px;
}
.navigation {
  display:flex;
  justify-content: space-evenly;
  flex:10;
}
.navigation p {
  padding:0 50px;
}

html { overflow-y: auto !important; }

</style>
