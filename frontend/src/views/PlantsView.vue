<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex center justify-space-between mb-10">
                <page-heading class="text-white">All Plants</page-heading>
                <div class="w-[220px]">
                    <v-text-field
                        :loading="loading"
                        density="compact"
                        variant="solo"
                        label="Search plant"
                        append-inner-icon="mdi-magnify"
                        single-line
                        hide-details
                        v-model="searchValue"
                        @click:append-inner="loading = true;"
                    ></v-text-field>
                </div>
            </div>
            <div class="flex flex-wrap gap-3 ">
                <div v-for="(greenhouse, index) in greenhousesList" :key="index" class="w-[360px] bg-white h-[200px] flex shadow rounded-[5px] p-[14px]">
                    <div class="w-[200px] h-full bg-cover bg-center mr-[14px] rounded relative" :class="greenhouse.status.toLowerCase()"
                         :style="{ background: `linear-gradient(337.44deg, ${greenhouse.status.toLowerCase() === 'offline' ? '#FF6161 0%, rgba(255, 97, 97, 0) 100%)' : '#449A8B 0%, rgba(68, 154, 139, 0) 100%)'}, url('/src/assets/plant-pic-example.png')` }"></div>
                    <div class="flex justify-space-between flex-col w-full">
                        <div>
                            <h2 class="text-[16px] text-primary font-primary">Location: {{ greenhouse.location }}</h2>
                            <h1 class="text-[20px] font-[600] font-primary">{{ greenhouse.name }}</h1>
                            <p class="font-secondary text-[14px]">{{ greenhouse.description }}</p>
                        </div>
                        <div tabindex="1" role="button" @click="navigateToGallery(greenhouse.id)" class="w-full flex justify-end ">
                            <oh-icon name="fa-arrow-right" scale="1.6" color="#449a8b"></oh-icon>
                        </div>
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
import axios from "axios";
import {API_BASE_URL} from "@/services";
interface GreenhouseData {
    id: number,
    name: string,
    location: string,
    description: string,
    status: string
}
export default defineComponent({
    name: "PlantsView",
    components: {headerComponent, footerComponent, mainContainer, PageHeading, MainComponent, HeaderView},
    data() {
        return {
            greenhouses: [] as GreenhouseData[],
            loading: false,
            searchValue: ''
        }
    },
    methods: {
        navigateToGallery(id: number) {
            this.$router.push(`/gallery/${id}`)
        }
    },
    computed: {
        greenhousesList() {
            if (this.searchValue.trim().length > 0) {
                return this.greenhouses.filter((greenhouse) => {
                    return greenhouse.name.toLowerCase().includes(this.searchValue.toLowerCase()) ||
                        greenhouse.location.toLowerCase().includes(this.searchValue.toLowerCase())
                })
            }
            return this.greenhouses;
        }
    },
    mounted() {
        axios.get(API_BASE_URL + "greenhouse/get").then((response =>  {
            this.greenhouses = response.data;
        }))
    }
})
</script>

<style scoped>
.offline::after {
    width: 16px;
    height: 16px;
    background: #FF6161;
    border: 2px solid #FFFFFF;
    content: '';
    position: absolute;
    bottom: -8px;
    right: -8px;
    border-radius: 25rem;
}
.online::after {
    width: 16px;
    height: 16px;
    background: #449A8B;
    border: 2px solid #FFFFFF;
    content: '';
    position: absolute;
    bottom: -8px;
    right: -8px;
    border-radius: 25rem;
}
</style>