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
            <div class="grid grid-cols-5">
                <div v-for="(greenhouse, index) in greenhouses" :key="index" class="w-[387px] bg-white h-[200px] flex rounded-[5px]">
                    <img src="@/assets/plant-pic-example.png" alt="not loaded" class="p-[14px]"/>
                    <div class="p-[15px]">
                        <h2 class="text-[16px] text-primary font-primary">Location: {{ greenhouse.location }}</h2>
                        <h1 class="text-[20px] font-[600] font-primary">{{ greenhouse.name }}</h1>
                        <p class="font-secondary text-[14px]">{{ greenhouse.description }}</p>
                        <router-link to="/{{greenhouse.id}}" tag="button" class="">
                            <img src="@/assets/login/icons/arrow-back.svg" alt="goto-plant" class="rotate-[180deg]"/>
                        </router-link>
                    </div>
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
import axios from "axios";
interface GreenhouseData {
    id: number,
    name: string,
    location: string,
    description: string
}
export default defineComponent({
    name: "PlantsView",
    components: {user_card, headerComponent, footerComponent, mainContainer, PageHeading, MainComponent, HeaderView},
    data() {
        return {
            greenhouses: [] as GreenhouseData[],
        }
    },
    methods: {
      test() {
          console.log("entered test method:")
          for(let i = 0; i < this.greenhouses.length; i++) {
              console.log(this.greenhouses[i].id);
              console.log(this.greenhouses[i].name);
              console.log(this.greenhouses[i].location);
              console.log(this.greenhouses[i].description);
          }
      }
    },
    mounted() {
        axios.get("http://localhost:9000/greenhouse/get").then((response =>  {
            this.greenhouses = response.data;
            console.log(response.data as GreenhouseData);
            this.test();
        }))

    }
})
</script>

<style scoped>

</style>