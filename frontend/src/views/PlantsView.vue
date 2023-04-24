<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex center justify-space-between mb-10">
                <page-heading class="text-white">All Plants</page-heading>
                <div class="w-[220px]">
<!--                    <v-text-field-->
<!--                        :loading="loading"-->
<!--                        density="compact"-->
<!--                        variant="solo"-->
<!--                        label="Search plant"-->
<!--                        append-inner-icon="mdi-magnify"-->
<!--                        single-line-->
<!--                        hide-details-->
<!--                        v-model="searchValue"-->
<!--                        @click:append-inner="loading = true;"-->
<!--                    ></v-text-field>-->
                </div>
            </div>
            <div class="w-[387px] bg-white h-full flex rounded-[5px]">
                <img src="@/assets/house.png" alt="not loaded" class="p-[14px] w-[136px] h-[211px]"/>
                <div class="p-[15px]">
                    <h2 class="text-[16px] text-primary font-primary">Location: XYZ</h2>
                    <h1 class="text-[20px] font-[600] font-primary">Plant Name</h1>
                    <p class="font-secondary text-[14px]">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin metus nulla, accumsan ut suscipit bibendum, volutpat nec eros. Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    <router-link to="/dashboard/plants/{id}" tag="button" class="">
                        <img src="@/assets/login/icons/arrow-back.svg" alt="goto-plant" class="rotate-[180deg]"/>
                    </router-link>
                </div>
            </div>
        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import HeaderView from "@/components/general/header.vue";
import {defineComponent} from "vue";
import MainComponent from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import mainContainer from "@/components/general/main_container.vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import user_card from "@/components/admin/user_card.vue";

export default defineComponent({
    name: "PlantsView",
    components: {user_card, headerComponent, footerComponent, mainContainer, PageHeading, MainComponent, HeaderView},
    data() {
        return {

        }
    },
    methods: {

    },
})
</script>

<style scoped>

</style>