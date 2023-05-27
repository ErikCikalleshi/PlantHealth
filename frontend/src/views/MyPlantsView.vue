<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex center justify-space-between mb-10">
                <page-heading class="text-white">My Plants</page-heading>
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
                <div v-for="(greenhouse, index) in greenhousesList" :key="index"
                     class="w-[360px] bg-white h-[200px] flex shadow rounded-[5px]">
                    <img src="@/assets/plant-pic-example.png" alt="not loaded" class="p-[14px]"/>
                    <div class="p-[15px] flex justify-space-between flex-col w-full">
                        <div>
                            <p class="text-[16px] text-primary font-primary">{{ greenhouse.accessPointName }}</p>
                            <h1 class="text-[20px] font-[600] font-primary">{{ greenhouse.name }}</h1>
                            <p class="text-[14px] text-gray-500 font-primary">{{ greenhouse.location }}</p>

                            <p class="font-secondary text-[14px]">{{ truncateString(greenhouse.description,48 )}}</p>
                        </div>
                        <div class="flex justify-start items-center">
                            <edit-greenhouse-dialog-form :greenhouseUUID="greenhouse.uuid"
                                                         :greenhouses="greenhousesList"/>
                            <v-btn variant="plain" icon="mdi-chart-bar" color="#449a8b"
                                   @click="navigateToCharts(greenhouse.uuid)"/>
                        </div>
                    </div>
                </div>
            </div>

        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import PageHeading from "@/components/general/PageHeading.vue";
import mainContainer from "@/components/general/main_container.vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import EditGreenhouseDialogForm from "@/components/gardener/edit_greenhouse.vue";
import adminGreenhouseService from "@/services/admin/AdminGreenhouseService";
import type IGreenhouse from "@/interfaces/IGreenhouse";
import adminAccessPointService from "@/services/admin/AdminAccessPointService";

export default defineComponent({
    name: "PlantsView",
    components: {
        EditGreenhouseDialogForm,
        headerComponent, footerComponent, mainContainer, PageHeading
    },
    data() {
        return {
            greenhouses: [] as IGreenhouse[],
            loading: false,
            searchValue: ''
        }
    },
    methods: {
        navigateToCharts(id: number) {
            this.$router.push(`/charts/${id}`)
        },
        truncateString(str: string, num: number) {
            if (str.length > num) {
                return str.slice(0, num) + "...";
            } else {
                return str;
            }
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
        adminGreenhouseService.getAllGreenhousesForCurrentUser().then((response => {
            this.greenhouses = response.data;
            this.greenhouses.forEach((greenhouse) => {
                adminAccessPointService.getAccesPointByGreenhouseUuid(greenhouse.uuid).then((response) => {
                    greenhouse.accessPointName = response.data.name;
                })
            })
        }))
    }
})
</script>

<style scoped>

</style>