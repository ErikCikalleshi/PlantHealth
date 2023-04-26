<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import mainContainer from "@/components/general/main_container.vue";
import axios from "axios";
import {API_BASE_URL} from "@/services";
import {useStore as userStore} from "@/stores/user/user";
import PageHeading from "@/components/general/PageHeading.vue";
import HeaderView from "@/components/general/header.vue";
import FooterView from "@/components/general/footer.vue";

export default defineComponent({
    name: "GalleryView",
    components: {
        FooterView,
        HeaderView,
        PageHeading,
        // eslint-disable-next-line vue/no-unused-components
        headerComponent,
        footerComponent,
        mainContainer
    },
    data() {
        return {
            imageUrl: null,
            plantId: -1,
            imgUrls: [],
            greenHouseName: '',
            empty: true,
        };
    },
    methods: {
        async uploadImage() {
            const file = (this.$refs.fileInput as HTMLInputElement).files?.[0];
            if (!file) {
                return;
            }

            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await fetch('https://api.imgur.com/3/image', {
                    method: 'POST',
                    headers: {
                        Authorization: 'Client-ID 3d2e05ae177fb07',
                    },
                    body: formData,
                });

                const data = await response.json();
                this.imageUrl = data.data.link;
                await axios.post(API_BASE_URL + "upload", {
                    username: userStore().username,
                    uploadLink: this.imageUrl,
                    plantId: this.plantId,
                }).then(() => {
                    let response = axios.get(API_BASE_URL + `greenhouse/get-all/${this.plantId}`).then((x) => {
                        this.imgUrls = x.data;
                        if(this.imgUrls.length > 0) {
                            this.empty = false
                        }
                    })
                })
            } catch (error) {
                console.error(error);
            }
        },
    },
    mounted() {
        this.plantId = Number(this.$route.params.id);
        if(this.plantId <= 0)
            return;
        const response = axios.get(API_BASE_URL + `greenhouse/get-all/${this.plantId}`).then((x) => {
            this.imgUrls = x.data;
            console.log(x.data);
            if(this.imgUrls.length > 0) {
                this.empty = false
            }
        })
        const ghName = axios.get(API_BASE_URL + `greenhouse/get-name/${this.plantId}`).then((x) => {
            this.greenHouseName = x.data;
        })
    }
});
</script>

<template>
    <v-app v-if="!empty">
        <header-component/>
        <main-container negative>
            <div class="flex justify-space-between mb-10">
                <page-heading class="text-white">Gallery {{ greenHouseName }}</page-heading>
                <div>
                    <v-file-input type="file" label="Upload Image" ref="fileInput" @change="uploadImage" class="w-[190px]"
                                  color="primary"
                                  bg-color="primary"
                                  variant="solo"
                    ></v-file-input>
                </div>
            </div>
            <div class="flex flex-wrap gap-3">
                <img v-for="(imgUrl, index) in imgUrls" :key="index" :src="imgUrl" alt="refresh browser" referrerpolicy="no-referrer" />
            </div>
        </main-container>
        <footer-component/>
    </v-app>
    <v-app v-if="empty">
        <header-view presentation class="h-full">
            <div class="container mx-auto flex justify-space-between mb-10">
                <page-heading class="text-white">Gallery {{ greenHouseName }}</page-heading>
                <div>
                    <v-file-input type="file" label="Upload Image" ref="fileInput" @change="uploadImage" class="w-[190px]"
                                  color="primary"
                                  bg-color="primary"
                                  variant="solo"
                    ></v-file-input>
                </div>
            </div>
            <div class="flex justify-center">
                <h1 class="font-primary text-[32px] text-white">Be the first one to upload a picture for this greenhouse.</h1>
            </div>
        </header-view>
        <footer-view></footer-view>
    </v-app>
</template>

<style>

html { overflow-y: auto !important; }

</style>
