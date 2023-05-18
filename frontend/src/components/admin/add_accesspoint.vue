<template>
    <v-dialog v-model="addAccessPointDialog" persistent width="1024">
        <template v-slot:activator="{ props }">
            <v-btn icon="mdi-plus" v-bind="props" class="mr-5"></v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="text-xl">Create new Access Point</span>
            </v-card-title>
            <v-form ref="addUserForm">
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12" sm="6" md="5">
                                <v-text-field label="Name*" required
                                              :rules="[v => !!v || 'Item is required']"
                                              v-model="newAccessPoint.name"/>
                            </v-col>
                            <v-col cols="12" sm="6" md="5">
                                <v-text-field label="Location*" required
                                              :rules="[v => !!v || 'Item is required']"
                                              v-model="newAccessPoint.location"/>
                            </v-col>
                            <v-col cols="12" sm="6" md="2">
                                <v-text-field label="Interval(s)*" required
                                              :rules="transmissionIntervalRules"
                                              v-model="newAccessPoint.transmissionInterval"/>
                            </v-col>
                            <v-col cols="12">
                                <v-textarea label="Description"
                                              v-model="newAccessPoint.description"
                                />
                            </v-col>
                            <v-col cols="12">
                                <v-checkbox label="Published" required
                                              v-model="newAccessPoint.published"/>
                            </v-col>
                        </v-row>
                    </v-container>

                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="error" variant="flat"
                           @click="resetNewAccessPoint(); addAccessPointDialog = false">
                        Cancel
                    </v-btn>
                    <v-btn color="primary" variant="flat" @click="addNewAccessPoint()">Save</v-btn>
                </v-card-actions>
            </v-form>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">

import {defineComponent} from "vue";
import type IAccessPoint from "@/interfaces/IAccessPoint";
import AdminAccessPointService from "@/services/admin/AdminAccessPointService";

export default defineComponent({
    name: "addAccessPointDialogForm",
    props: {
        accessPoints: {
            type: Array<IAccessPoint>,
            required: false
        },
    },
    data() {
        return {
            addAccessPointDialog: false,
            newAccessPoint: {
                id: -1,
                name: '',
                description: '',
                location: '',
                transmissionInterval: 600,
                greenhouses: [],
                lastContact: '',
                status: '',
                published: false,
            } as IAccessPoint,
            transmissionIntervalRules: [
                (v: any) => !!v || 'Item is required',
                (v: any) => !isNaN(v) || 'Only numeric values are allowed',
                (v: number) => (v && v > 0) || 'Transmission interval must be greater than 0',
            ],
        }
    },
    methods: {
        resetNewAccessPoint() {
            this.newAccessPoint = {
                id: -1,
                name: '',
                description: '',
                location: '',
                transmissionInterval: 600,
                greenhouses: [],
                lastContact: '',
                status: '',
                published: false,
            } as IAccessPoint;
        },
        required(v: any) {
            return !!v || 'Field is required'
        },
        async addNewAccessPoint() {
            const form = this.$refs.addUserForm as { validate: () => Promise<{ valid: boolean }> };
            const {valid} = await form.validate();
            if (valid) {
                AdminAccessPointService.createNewAccessPoint(this.newAccessPoint).then((response) => {
                    if (response.status===201){
                        const accessPoint:IAccessPoint = response.data;
                        if (this.accessPoints){
                            this.accessPoints.push(accessPoint);
                        }
                        this.addAccessPointDialog = false;
                        this.resetNewAccessPoint();
                    }
                })
            }
        }
    },
})
</script>