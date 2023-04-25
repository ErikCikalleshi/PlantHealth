<template>
    <v-dialog v-model="addGreenhouseDialog" persistent width="1400">
        <template v-slot:activator="{ props }">
            <v-btn color="primary" v-bind="props">Add Plant</v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="text-xl">Add new Plant</span>
            </v-card-title>
            <v-form ref="addUserForm">
                <v-card-text>
                    <v-container>
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
                                    v-if="gardeners.length > 0"
                                        v-model="newGreenhouse.gardener"
                                        :items="gardeners.map(gardener => ({
                                            firstname: gardener.firstname,
                                            lastname: gardener.lastname,
                                            gardener: {
                                              ...gardener,
                                              title: `${gardener.firstname} ${gardener.lastname}`
                                            }
                                          }))"
                                        :rules="[v => !!v || 'Item is required']"
                                        item-title="gardener.title"
                                        item-text="gardener"
                                        return-object
                                    label="Gardener"

                                />
                            </v-col>
                            <v-col cols="12">
                                <v-textarea label="Description"
                                            v-model="newGreenhouse.description"
                                />
                            </v-col>
                            <v-col cols="12">
                                <v-checkbox label="Published" required
                                            v-model="newGreenhouse.published"/>
                            </v-col>
                            <v-row>
                                <v-col cols="6">

                                </v-col>
                            </v-row>
                        </v-row>
                    </v-container>

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

export default defineComponent({
    name: "addGreenhouseDialogForm",
    props: {
        greenhouses: {
            type: Array<IGreenhouse>,
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
        getAllGardeners() {
            AdminUserService.getAllUsers().then((response) => {
                if (response.status === 200) {
                    const tmp: IUser[] = response.data;
                    this.gardeners = tmp.filter((user) => user.roles.includes('GARDENER'));
                    // this.newGreenhouse.gardener = this.gardeners[0];
                }
            })

        },
        getSensorsEmpty(){
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
                        this.greenhouses.push(greenhouse);
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