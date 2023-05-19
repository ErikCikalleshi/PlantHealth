<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex-wrap gap-2 flex center justify-space-between mb-10">
                <page-heading class="text-white">Access Points</page-heading>
                <div class="flex items-center">
                    <div class="ml-auto">
                        <add-access-point-dialog-form :accessPoints="accessPointList"/>
                    </div>
                    <div class="w-[220px]">
                        <v-text-field
                                :loading="loading"
                                density="compact"
                                variant="solo"
                                label="Search..."
                                append-inner-icon="mdi-magnify"
                                single-line
                                hide-details
                                v-model="searchValue"
                                @click:append-inner="loading = true;"
                        ></v-text-field>
                    </div>
                </div>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-x-4 gap-y-8">
                <accessPoint_card v-for="accessPoint in accessPointList" :key="accessPoint.id"
                                  :accessPoint="accessPoint"
                                  class="w-full" @confirm="deleteAccessPoint(accessPoint)"/>
            </div>
        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AdminAccessPointService from "@/services/admin/AdminAccessPointService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import accessPoint_card from "@/components/admin/accessPoint_card.vue";
import type IAccessPoint from "@/interfaces/IAccessPoint";
import AddAccessPointDialogForm from "@/components/admin/add_accesspoint.vue";

export default defineComponent({
    name: "adminManageAccessPoints",
    components: {
        AddAccessPointDialogForm,
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
        accessPoint_card,
    },
    data() {
        return {
            accessPoints: [] as IAccessPoint[],
            searchValue: '',
            isOpen: false,
            selectedRoles: [],
            loading: false,
        }
    },
    methods: {
        getAllAccessPoints() {
            AdminAccessPointService.getAllAccessPoints().then((response) => {
                this.accessPoints = response.data;
            })

        },
        deleteAccessPoint(accessPoint: IAccessPoint | null) {
            if (accessPoint == null) {
                return;
            }
            AdminAccessPointService.deleteAccessPoint(accessPoint.id).then(() => {
                this.accessPoints.splice(this.accessPoints.indexOf(accessPoint), 1);
            })
        },
    },
    computed: {
        accessPointList() {
            if (this.searchValue.trim().length > 0) {
                return this.accessPoints.filter((accessPoints) => {
                    return accessPoints.name.toLowerCase().includes(this.searchValue.toLowerCase()) ||
                        accessPoints.status.toLowerCase().includes(this.searchValue.toLowerCase()) ||
                        accessPoints.location.toLowerCase().includes(this.searchValue.toLowerCase())
                })
            }
            return this.accessPoints;
        }
    },
    created() {
        this.getAllAccessPoints();
    }
})
</script>