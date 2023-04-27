<template>
    <v-dialog v-model="editGreenhouseDialog" persistent width="1400">
        <template v-slot:activator="{ props }">
            <v-btn variant="plain" icon="mdi-pencil-outline" v-bind="props" @click="loading=true; init()"/>
        </template>
        <v-card class="pa-5" :loading="loading">
            <template slot="progress">
                <v-overlay absolute class="d-flex flex-column text-center">
                    <div>
                        <v-progress-circular size="500" color="accent " :value="50" indeterminate>
                            <span>Loading</span>
                        </v-progress-circular>
                    </div>
                </v-overlay>
            </template>


            <v-form ref="addUserForm">
                <v-card-title>
                    <span class="text-xl">Add new Plant</span>
                </v-card-title>
                <v-card-text>
                    <v-row>
                        <v-col cols="12" sm="6" md="5">
                            <v-text-field label="Name*" required
                                          :rules="[v => !!v || 'Item is required']"
                                          v-model="greenhouse.name"/>
                        </v-col>
                        <v-col cols="12" sm="6" md="5">
                            <v-text-field label="Location*" required
                                          :rules="[v => !!v || 'Item is required']"
                                          v-model="greenhouse.location"/>
                        </v-col>
                        <v-col cols="12" sm="6" md="2">
                            <v-select
                                    v-model="greenhouse.gardener"
                                    :items="gardeners"
                                    :item-title="getDisplayName"
                                    :rules="[v => !!v || 'Item is required']"
                                    label="Gardener"
                                    return-object

                            />
                        </v-col>
                    </v-row>
                    <v-row no-gutters>
                        <v-col cols="12" class="my-0">
                            <v-textarea label="Description"
                                        v-model="greenhouse.description"
                            />
                        </v-col>
                    </v-row>
                    <v-row no-gutters>
                        <v-col cols="12">
                            <v-checkbox label="Published" required
                                        v-model="greenhouse.published"/>
                        </v-col>
                    </v-row>
                    <v-card-title>Sensors</v-card-title>
                    <div class="flex grid-cols-6 gap-3 mb-5">
                        <v-card v-for="sensor in greenhouse.sensors" class="w-full" :subtitle="sensor.sensorType">
                            <v-card-text>
                                <v-text-field label="max val*" required :rules="[v => !!v || 'Item is required']"
                                              v-model="sensor.limitUpper" :prefix="getUnitByType(sensor.sensorType)"/>
                                <v-text-field label="min val*" required :rules="[v => !!v || 'Item is required']"
                                              v-model="sensor.limitLower" :prefix="getUnitByType(sensor.sensorType)"/>
                                <v-text-field label="notify after*" required :rules="[v => !!v || 'Item is required']"
                                              v-model="sensor.limitThresholdMinutes" prefix="min"/>
                            </v-card-text>
                        </v-card>
                    </div>


                    <!--                    </v-container>-->

                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="error" variant="flat"
                           @click="editGreenhouseDialog = false">
                        Cancel
                    </v-btn>
                    <v-btn color="primary" variant="flat" @click="saveGreenhouse()">Save</v-btn>
                </v-card-actions>
            </v-form>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">

import {defineComponent} from "vue";
import type IGreenhouse from "@/interfaces/IGreenhouse";
import type IUser from "@/interfaces/user/IUser";
import AdminUserService from "@/services/admin/AdminUserService";
import AdminGreenhouseService from "@/services/admin/AdminGreenhouseService";
import type {Item} from "vue3-easy-data-table";

export default defineComponent({
    name: "editGreenhouseDialogForm",
    props: {
        greenhouseUUID: {
            type: Number,
            required: true
        },
        greenhouses: {
            type: Array<Item>,
            required: true
        },
    },
    data() {
        return {
            loading: false,
            editGreenhouseDialog: false,
            gardeners: [] as IUser[],
            greenhouse: {} as IGreenhouse,
        }
    },
    methods: {
        getDisplayName(item: IUser) {
            return `${item.firstname} ${item.lastname}`;
        },
        async getAllGardeners() {
            await AdminUserService.getAllUsers().then((response) => {
                if (response.status === 200) {
                    const tmp: IUser[] = response.data;
                    this.gardeners = tmp.filter((user) => user.roles.includes('GARDENER'));
                }
            })
        },
        async loadGreenhouse() {
            await AdminGreenhouseService.getGreenhouse(this.greenhouseUUID).then((response) => {
                if (response.status === 200) {
                    this.greenhouse = response.data;

                }
            })
        },
        async init() {
            console.log(this.loading);
            await this.getAllGardeners();
            await this.loadGreenhouse();
            // this.loading= false;
            console.log(this.loading);
        },
        getUnitByType(type: string) {
            switch (type) {
                case "AIR_PRESSURE":
                    return "hPa";
                case "AIR_QUALITY":
                    return "%";
                case "HUMIDITY_AIR":
                    return "%";
                case "HUMIDITY_DIRT":
                    return "%";
                case "LIGHT":
                    return "lx";
                case "TEMPERATURE":
                    return "°C";
                default:
                    return "";
            }
        },
        required(v: any) {
            return !!v || 'Field is required'
        },
        async saveGreenhouse() {
            const form = this.$refs.addUserForm as { validate: () => Promise<{ valid: boolean }> };
            const {valid} = await form.validate();
            if (valid) {
                AdminGreenhouseService.updateGreenhouse(this.greenhouse).then((response) => {
                    if (response.status === 200) {
                        const greenhouse: IGreenhouse = response.data;
                        const item = {
                            greenhouseName: greenhouse.name,
                            greenhouseId: greenhouse.id,
                            greenhouseUUID: greenhouse.uuid,
                            greenhouseDescription: greenhouse.description,
                            greenhouseLocation: greenhouse.location,
                            greenhouseGardener: greenhouse.gardener.firstname + " " + greenhouse.gardener.lastname,
                            greenhousePublished: greenhouse.published,
                            greenhouseStatus: greenhouse.status,
                            greenhouseLastContact: greenhouse.lastContact,
                            greenhouseActions: ""
                        } as Item;
                        //replace item in greenhouses
                        const itemInGreenhouses  = this.greenhouses.find((item) => item.greenhouseUUID === this.greenhouseUUID);
                        if (itemInGreenhouses !== undefined) {
                            this.greenhouses.splice(this.greenhouses.indexOf(itemInGreenhouses), 1, item);
                            this.editGreenhouseDialog = false;
                        }
                    }
                })
            }
        }
    },
    created() {

    }
})
</script>