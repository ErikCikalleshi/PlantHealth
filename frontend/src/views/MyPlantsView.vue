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
                     class="w-[360px] bg-white h-[200px] flex shadow rounded-[5px] p-[14px]">
                    <div class="w-[200px] h-full bg-cover bg-center mr-[14px] rounded relative"
                         :class="greenhouse.status.toLowerCase()"
                         :style="{ background: `linear-gradient(337.44deg, ${greenhouse.status.toLowerCase() === 'offline' ? '#FF6161 0%, rgba(255, 97, 97, 0) 100%)' : (greenhouse.displayLimitExceeded ? '#D4790C 0%, rgba(240, 240, 15, 0) 100%)' : '#449A8B 0%, rgba(68, 154, 139, 0) 100%)')}, url('/src/assets/plant-pic-example.png')` }"></div>
                    <div class="flex justify-space-between flex-col w-full">
                        <div>
                            <img src="/src/assets/alert-remove-outline.svg" class="w-[50px] h-full bg-cover bg-center mr-[10px] mt-[-20px] rounded relative"
                                 :style="{float: `right`, display: `${greenhouse.displayIcon ? 'block' : 'none'}`}"/>
                            <p class="text-[16px] text-primary font-primary">{{ greenhouse.accessPointName }}</p>
                            <h1 class="text-[20px] font-[600] font-primary">{{ greenhouse.name }}</h1>
                            <p class="text-[14px] text-gray-500 font-primary">{{ greenhouse.location }}</p>


                            <p class="font-secondary text-[14px]">{{ truncateString(greenhouse.description, 48) }}</p>
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
    mounted: async function() {
        try {
            const response = await adminGreenhouseService.getAllGreenhousesForCurrentUser();
            this.greenhouses = response.data;
            for (const greenhouse of this.greenhouses) {
                if (greenhouse.displayLimitExceeded) {
                    console.log("xd");
                }
                const accessPointResponse = await adminAccessPointService.getAccesPointByGreenhouseUuid(greenhouse.uuid);
                greenhouse.accessPointName = accessPointResponse.data.name;
            }

            let storedDisplayIcons = JSON.parse(localStorage.getItem('displayIcons') || '{}');

            // Loop through the greenhouses and set the displayIcon property
            for (const greenhouse of this.greenhouses) {
                if (storedDisplayIcons.hasOwnProperty(greenhouse.uuid)) {
                    // Set the displayIcon value from localStorage
                    greenhouse.displayIcon = storedDisplayIcons[greenhouse.uuid];
                }
            }


        } catch (error) {
            console.error(error);
        }
    },
    beforeUnmount() {
        let storedDisplayIcons: { [key: string]: boolean } = {};
        this.greenhouses.forEach((greenhouse: IGreenhouse) => {
            storedDisplayIcons[greenhouse.uuid] = greenhouse.displayIcon;
        });
        localStorage.setItem('displayIcons', JSON.stringify(storedDisplayIcons));
        console.log(JSON.parse(localStorage.getItem('displayIcons') || '{}'));
    }
})
</script>

<style scoped>

</style>