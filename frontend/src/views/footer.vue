<script lang="ts">
import {defineComponent} from "vue";
import { OhVueIcon } from "oh-vue-icons";

export default defineComponent({
  name: "FooterView",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    OhVueIcon
  },
})
</script>

<template>
  <v-app>
    <div class="footer-view pb-[0px]">
      <div class="pl-[174px] pb-[207px]">
        <img alt="Plant Health Gardening LOGO" src="@/assets/logo.svg"/>
      </div>
    </div>
  </v-app>
</template>

<style>
.footer-view {
  background-color: #040A0F;;
  background-size: 1920px 323px;
}
html { overflow-y: auto !important; }

</style>
