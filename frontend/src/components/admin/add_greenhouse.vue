<template>
    <v-dialog v-model="addGreenhouseDialog" persistent width="1400">
        <template v-slot:activator="{ props }">
            <v-btn color="primary" v-bind="props">Add Plant</v-btn>
        </template>
        <v-card class="pa-5">
            <v-form ref="addUserForm">
                <v-card-title>
                    <span class="text-xl">Add new Plant</span>
                </v-card-title>
                <v-card-text>
                    <v-row>
                        <v-col cols="12" sm="6" md="5">
                            <v-text-field label="Name*" required
                                          :rules="[v => !!v || 'Item is required']"
                                          v-model="newGreenhouse.name"/>
                        </v-col>
                        <v-col cols="12" sm="6" md="5">
                            <v-text-field label="Location*" required
                                          :rules="[v => !!v || 'Item is required']"
                                          v-model="newGreenhouse.location"/>
                        </v-col>
                        <v-col cols="12" sm="6" md="2">
                            <v-select
                                    v-model="newGreenhouse.gardener"
                                    :items="gardeners"
                                    :item-title="getDisplayName"
                                    :rules="[v => !!v || 'Item is required']"
                                    label="Gardener"
                                    return-object

                            />
                        </v-col>
                    </v-row>
                    <v-row no-gutters>
                        <v-col cols="12">
                            <v-textarea label="Description"
                                        v-model="newGreenhouse.description"
                            />
                        </v-col>
                    </v-row>
                    <v-row no-gutters>
                        <v-col cols="12">
                            <v-checkbox label="Published" required
                                        v-model="newGreenhouse.published"/>
                        </v-col>
                    </v-row>
                    <v-card-title>Sensors</v-card-title>
                    <div class="flex grid-cols-6 gap-3 mb-5">
                        <v-card v-for="sensor in newGreenhouse.sensors" class="w-full" :subtitle="sensor.sensorType">
                            <!--                                <v-card-subtitle>-->
                            <!--                                    <span class="text-sm">{{ sensor.sensorType }}</span>-->
                            <!--                                </v-card-subtitle>-->
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

                    <v-spacer/>
                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="error" variant="flat"
                           @click="resetNewGreenhouse(); addGreenhouseDialog = false">
                        Cancel
                    </v-btn>
                    <v-btn color="primary" variant="flat" @click="addNewGreenhouse()">Save</v-btn>
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
    name: "addGreenhouseDialogForm",
    props: {
        greenhouses: {
            type: Array<Item>,
            required: true
        },
        accessPointId: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
            addGreenhouseDialog: false,
            gardeners: [] as IUser[],
            newGreenhouse: {
                uuid: -1,
                id: -1,
                name: '',
                description: '',
                location: '',
                gardener: null as unknown as IUser,
                lastContact: '',
                status: '',
                published: false,
                sensors: this.getSensorsEmpty()
            } as IGreenhouse,

        }
    },
    methods: {
        getDisplayName(item: IUser) {
            return `${item.firstname} ${item.lastname}`;
        },
        getAllGardeners() {
            AdminUserService.getAllUsers().then((response) => {
                if (response.status === 200) {
                    const tmp: IUser[] = response.data;
                    this.gardeners = tmp.filter((user) => user.roles.includes('GARDENER'));
                    // this.newGreenhouse.gardener = this.gardeners[0];
                }
            })

        },
        getSensorsEmpty() {
            const sensorTypes = [
                "AIR_PRESSURE",
                "AIR_QUALITY",
                "HUMIDITY_AIR",
                "HUMIDITY_DIRT",
                "LIGHT",
                "TEMPERATURE",
            ];
            return sensorTypes.map((sensorType) => ({
                id: null,
                sensorType: sensorType,
                limitLower: null,
                limitUpper: null,
                limitThresholdMinutes: null,
            }));

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
        resetNewGreenhouse() {
            this.newGreenhouse = {
                uuid: -1,
                id: -1,
                name: '',
                description: '',
                location: '',
                gardener: null as unknown as IUser,
                lastContact: '',
                status: '',
                published: false,
                sensors: this.getSensorsEmpty()
            } as IGreenhouse;
        },
        required(v: any) {
            return !!v || 'Field is required'
        },
        async addNewGreenhouse() {
            const form = this.$refs.addUserForm as { validate: () => Promise<{ valid: boolean }> };
            const {valid} = await form.validate();
            if (valid) {
                AdminGreenhouseService.addGreenhouseToAccessPoint(this.newGreenhouse, this.accessPointId).then((response) => {
                    if (response.status === 201) {
                        const greenhouse: IGreenhouse = response.data;
                        console.log(greenhouse);
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
                        this.greenhouses.push(item);
                        this.addGreenhouseDialog = false;
                        this.resetNewGreenhouse();
                    }
                })
            }
        }
    },
    created() {
        this.getAllGardeners();
    }
})
</script>