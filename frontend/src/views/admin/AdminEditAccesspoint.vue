<template>
    <v-app>
        <header-component/>
        <main-container negative>
            <div class="flex flex-wrap">
                <page-heading>Edit Access Point</page-heading>
                <div class="mx-auto mt-36 mb-8 w-full h-full text-black">
                    <div class="grid grid-cols-10 grid-rows-2 gap-6 mb-6">
                        <div class="col-span-3">
                            <v-label text="Name"/>
                            <v-text-field v-model="accessPoint.name" color="primary" hide-details type="text"
                                          placeholder="Name"/>
                        </div>
                        <div class=" row-span-2 col-span-4">
                            <v-label text="Description"/>
                            <v-textarea v-model="accessPoint.description" color="primary" hide-details type="text"
                                        placeholder="Interval"/>
                        </div>
                        <div class="col-span-2">
                            <v-label text="Interval"/>
                            <div class="flex">
                                <v-text-field v-model="accessPoint.transmissionInterval" color="primary"
                                              hide-details type="text"
                                              placeholder="Interval"/>
                                <v-tooltip location="top">
                                    <template v-slot:activator="{props}">
                                        <v-icon class="mx-2" icon="mdi-information-outline" size="auto"
                                                v-bind="props"></v-icon>

                                    </template>
                                    <span> The interval in which the access point will send data to the server (in seconds). </span>
                                </v-tooltip>
                            </div>

                        </div>
                        <div class="col-span-3">
                            <v-label text="Location"/>
                            <v-text-field v-model="accessPoint.location" color="primary" hide-details type="text"
                                          placeholder="Location"/>
                        </div>
                        <div class="col-span-2">
                            <v-label text="Published"/>
                            <v-checkbox v-model="accessPoint.published" color="primary" hide-details/>
                        </div>
                    </div>
                    <div>
                        <v-btn class="font-primary text-white bg-primary" @click="updateAccessPoint(accessPoint)">Save
                            changes
                        </v-btn>

                    </div>
                    <div class="mb-3 mt-6">
                        <span class="text-lg">Plants</span>
                        <div class="grid gap-3 grid-cols-3 w-1/3">
                            <add-greenhouse-dialog-form :greenhouses="items" :access-point-id="accessPoint.id"/>
                            <v-btn color="primary" @click="downloadQrCodes(itemsSelected)" :disabled="!displayBulkEdit">
                                Print Selected
                            </v-btn>
                            <!--                            TODO open confirm dialog before deleting-->
                            <v-btn color="error" @click="deleteDialog=true; itemToDelete = itemsSelected"
                                   :disabled="!displayBulkEdit">Delete Selected
                            </v-btn>
                        </div>
                    </div>

                    <div>
                        <EasyDataTable
                                :loading="loading"
                                :headers="headers"
                                :key="items.length"
                                :items="items"
                                v-model:items-selected="itemsSelected"
                                :alternating="true">
                            <template #item-greenhousePublished="item">
                                <v-checkbox v-model="item.greenhousePublished" color="primary" hide-details disabled/>
                            </template>
                            <template #item-greenhouseStatus="item">
                                <span :class="' text-' +getColorByStatus(item.greenhouseStatus)">{{
                                    item.greenhouseStatus
                                    }}</span>
                            </template>
                            <template #item-operation="item">
                                <div class="flex">
                                    <v-btn variant="plain" icon="mdi-qrcode"
                                           @click="downloadQrCode(item)"/>
                                    <v-btn variant="plain" color="error" icon="mdi-delete-outline"
                                           @click="deleteDialog=true; itemToDelete = item"/>
                                    <edit-greenhouse-dialog-form :greenhouseUUID="item.greenhouseUUID" :greenhouses="items"/>
                                </div>
                            </template>
                        </EasyDataTable>
                    </div>
                    <v-dialog v-model="deleteDialog" width="auto">
                        <v-card class="font-primary" title="Confirm Delete"
                                text="Are you sure you want to delete this plant? This cannot be undone.">
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="primary" variant="tonal"
                                       @click="deleteDialog = false; itemToDelete = null;">Close
                                </v-btn>
                                <v-btn color="error" variant="tonal" @click="deleteGreenhouse(itemToDelete)">Delete
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </div>
            </div>

        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import type IAccessPoint from "@/interfaces/IAccessPoint";
import AdminAccessPointService from "@/services/admin/AdminAccessPointService";
import type {Header, Item} from "vue3-easy-data-table";
import type IGreenhouse from "@/interfaces/IGreenhouse";
import JSZip from "jszip";
import QRCode from "qrcode";
import AddGreenhouseDialogForm from "@/components/admin/add_greenhouse.vue";
import EditGreenhouseDialogForm from "@/components/gardener/edit_greenhouse.vue";


export default defineComponent({
    name: "adminEditAccessPoint",
    computed: {
        displayBulkEdit(): boolean {
            return this.itemsSelected.length > 0;
        }
    },
    data() {
        return {
            deleteDialog: false,
            itemToDelete: null as Item | Item[] | null,
            addNewGreenhouseDialog: false,
        };
    },
    components: {
        EditGreenhouseDialogForm,
        AddGreenhouseDialogForm,
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
    },
    props: {
        apId: {
            //type is string as vue can't handle numbers in the url
            type: String,
            required: true
        },
    },
    setup(props) {
        let accessPoint = ref<IAccessPoint>({
            id: 0,
            name: '',
            location: '',
            description: '',
            transmissionInterval: 0,
            greenhouses: [],
            lastContact: '-',
            status: 'offline',
            published: false
        });
        let items = ref<Item[]>([]);

        let loading = ref<boolean>(true);
        AdminAccessPointService.getSingleAccessPoint(Number(props.apId)).then((response) => {
            accessPoint.value = (response.data);
            items.value = response.data.greenhouses.sort((a: IGreenhouse, b: IGreenhouse) => a.id - b.id).map((greenhouse: IGreenhouse) => {
                return {
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
                }
            });
            loading.value = false;
        });

        const headers: Header[] = [
            {text: 'Id', value: 'greenhouseId', sortable: true},
            {text: 'Name', value: 'greenhouseName', sortable: true},
            {text: 'Location', value: 'greenhouseLocation', sortable: true},
            {text: 'Gardener', value: 'greenhouseGardener', sortable: true},
            {text: 'Description', value: 'greenhouseDescription', sortable: true},
            {text: 'Published', value: 'greenhousePublished', sortable: false},
            {text: 'Status', value: 'greenhouseStatus', sortable: true},
            {text: 'Last contact', value: 'greenhouseLastContact'},
            {text: 'Actions', value: "operation"},
        ];
        let itemsSelected = ref<Item[]>([]);
        return {accessPoint, items, headers, loading, itemsSelected}
    },
    methods: {
        updateAccessPoint(accessPoint: IAccessPoint) {
            AdminAccessPointService.updateAccessPoint(accessPoint).then((response) => {
                this.accessPoint = response.data;
            });
        },
        deleteGreenhouse(item: Item | Item[] | null) {
            if (item != null) {
                if (Array.isArray(item)) {
                    item.forEach((i) => {
                        console.log(i.greenhouseId)
                        this.items = this.items.filter((item) => item.greenhouseId != i.greenhouseId);
                        AdminAccessPointService.deleteGreenhouseByIdAndAccessPoint(i.greenhouseId, this.accessPoint.id)
                    })
                } else {
                    this.items = this.items.filter((i) => i.greenhouseId != item.greenhouseId);
                    AdminAccessPointService.deleteGreenhouseByIdAndAccessPoint(item.greenhouseId, this.accessPoint.id)
                }
                this.deleteDialog = false;
            }
        },
        getColorByStatus(status: String) {
            if (status==undefined){
                return "status-offline";
            }
            switch (status.toUpperCase()) {
                case "OFFLINE":
                    return "status-offline"
                case "ONLINE":
                    return "status-online"
                default:
                    return "role-offline"
            }
        },
        generateQrCode(uuid: number): Promise<Blob> {
            const url = `/upload-image/${uuid}`
            const canvas = document.createElement('canvas')
            const size = 1024
            canvas.width = size
            canvas.height = size
            return new Promise((resolve, reject) => {
                QRCode.toCanvas(canvas, url, {width: size}, (error: any) => {
                    if (error) {
                        reject(error)
                        return
                    }
                    canvas.toBlob((blob) => {
                        if (blob) {
                            resolve(blob)
                        } else {
                            reject(new Error('Failed to generate QR code image'))
                        }
                    })
                })
            })
        },
        downloadQrCode(item: Item) {
            const uuid = item.greenhouseUUID
            const fileName = item.greenhouseName + '(' + item.greenhouseUUID + ')'
            this.generateQrCode(uuid).then((blob) => {
                const url = URL.createObjectURL(blob)
                const a = document.createElement('a')
                a.href = url
                a.download = fileName
                document.body.appendChild(a)
                a.dispatchEvent(new MouseEvent('click'))
                URL.revokeObjectURL(url)
                document.body.removeChild(a)
            }).catch((error) => {
                console.error(error)
            })
        },
        downloadQrCodes(items: Item[]) {
            const promises = items.map((item) => {
                const uuid = item.greenhouseUUID
                const fileName = item.greenhouseName + '(' + item.greenhouseUUID + ').png'
                return this.generateQrCode(uuid).then((blob) => {
                    return {blob, fileName}
                }).catch((error) => {
                    console.error(`Error generating QR code for ${fileName}: ${error}`)
                    return null
                })
            })

            Promise.all(promises).then((results) => {
                const zip = new JSZip()
                results.forEach((result) => {
                    if (result) {
                        const {blob, fileName} = result
                        zip.file(fileName, blob, {binary: true})
                    }
                })
                zip.generateAsync({type: 'blob'}).then((zipBlob: Blob | MediaSource) => {
                    const url = URL.createObjectURL(zipBlob)
                    const a = document.createElement('a')
                    a.href = url
                    a.download = 'qr_codes.zip'
                    document.body.appendChild(a)
                    a.dispatchEvent(new MouseEvent('click'))
                    URL.revokeObjectURL(url)
                    document.body.removeChild(a)
                }).catch((error: any) => {
                    console.error('Error creating zip file: ' + {error})
                })
            })
        },
    }
})
</script>