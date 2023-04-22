<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import mainContainer from "@/components/general/main_container.vue";

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
            <img alt="glasshouse" src="@/assets/house.png"/>
            <img alt="glasshouse" src="@/assets/house.png"/>

            <v-btn color="primary">Upload Image</v-btn>
        </main-container>
        <footer-component/>
    </v-app>
</template>

<style>

html { overflow-y: auto !important; }

</style>
