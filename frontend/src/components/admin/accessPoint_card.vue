<template>
    <v-card class="overflow-hidden shadow-xl border border-primary pa-3 font-primary">
        <div class="flex gap-3 pb-3">
            <header class="flex flex-col gap-2 w-full">
                <div class="flex justify-between">
                    <h3 class="no-underline text-black font-semibold">
                        {{ accessPoint.name }}
                    </h3>
                    <v-icon icon="mdi-earth-off" color="error" v-if="!accessPoint.published"/>
                    <v-icon icon="mdi-earth" color="primary" v-if="accessPoint.published"/>
                </div>
                <div class="flex flex-wrap gap-1 rounded">
                    <p class="border-[1.5px] rounded-full px-2 text-xs"
                       :class="'border-'+getColorByStatus(accessPoint.status) + ' text-' +getColorByStatus(accessPoint.status)">
                        {{ accessPoint.status }}
                    </p>
                    <p class="ml-2 text-xs text-gray-500">{{ accessPoint.lastContact }}</p>
                </div>
                <div>
                    <div class="flex align-center gap-2">
                        <v-icon icon="mdi-identifier" size="24px"></v-icon>
                        <p class="text-xs text-gray-500">{{ accessPoint.id }}</p>
                    </div>
                    <div class="flex align-center gap-2">
                        <v-icon icon="mdi-map-marker" size="24px"></v-icon>
                        <p class="text-xs text-gray-500">{{ accessPoint.location }}</p>
                    </div>
                    <div class="flex align-center gap-2">
                        <v-icon icon="mdi-information-outline" size="24px"></v-icon>
                        <p class="text-xs text-gray-500">{{ truncateString(accessPoint.description, 40) }}</p>
                    </div>
                </div>
            </header>
        </div>
        <div class="grid grid-cols-2 gap-2 items-end">
            <v-btn variant="flat" color="primary"
                   @click="$router.push({name:'admin-edit-access-point', params:{apId: Number(accessPoint.id)}})">
                Edit
            </v-btn>
            <v-btn variant="flat" color="error">
                Delete

                <v-dialog
                        v-model="deleteDialog"
                        activator="parent"
                        width="auto"
                >
                    <v-card class="font-primary">
                        <v-card-title>
                            Confirm Delete
                        </v-card-title>
                        <v-divider></v-divider>
                        <v-card-text>
                            Are you sure you want to delete this Access Point? All connected greenhouses will also be removed!<br>
                            This cannot be undone.
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    color="primary"
                                    variant="tonal"
                                    @click="deleteDialog = false;"
                            >
                                Close
                            </v-btn>
                            <v-btn
                                    color="error"
                                    variant="tonal"
                                    @click="emitConfirm"
                            >
                                Delete
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-btn>
        </div>
    </v-card>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import type IAccessPoint from "@/interfaces/IAccessPoint";

export default defineComponent({
    data() {
        return {
            deleteUserModalVisible: false,
            deleteDialog: false,
        };
    },
    props: {
        accessPoint: {
            type: Object as () => IAccessPoint,
            required: true,
        },

    },
    methods: {
        getColorByStatus(status: String) {
            switch (status.toUpperCase()) {
                case "OFFLINE":
                    return "status-offline"
                case "ONLINE":
                    return "status-online"
                default:
                    return "role-offline"
            }
        },
        truncateString(str: string, num: number) {
            if (str.length > num) {
                return str.slice(0, num) + "...";
            } else {
                return str;
            }
        }
    },
    emits: ['confirm'],
    setup(props, {emit}) {
        const emitConfirm = () => {
            emit('confirm');
        };
        return {emitConfirm};
    },
});
</script>