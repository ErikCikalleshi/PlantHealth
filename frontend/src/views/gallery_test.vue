<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/footer.vue";
import headerComponent from "@/components/header.vue";
import mainContainer from "@/components/main_container.vue";

export default defineComponent({
  name: "GalleryView",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    headerComponent,
    footerComponent,
    mainContainer
  },
})
</script>

<template>
  <v-app>
    <header-component/>
    <main-container negative>
      <img alt="Random Logo" src="/src/assets/image4.png"/>
      <br>
      <br><br><br><br><br><br><br><br><br>
      <br><br><br><br><br><br><br><br><br>
      <div class="font-primary text-black text-[30px]">
      <p class="text-center">
        random glasshouse</p>
        <p class="text-center">|</p>
      <img alt="Random Logo" src="/src/assets/image4.png"/>
      </div>
    </main-container>
    <footer-component/>
  </v-app>
</template>

<style>

html { overflow-y: auto !important; }

</style>
