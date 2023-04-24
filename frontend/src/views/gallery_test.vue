<script lang="ts">
import {defineComponent} from "vue";
import footerComponent from "@/components/general/footer.vue";
import headerComponent from "@/components/general/header.vue";
import mainContainer from "@/components/general/main_container.vue";
import axios from "axios";

export default defineComponent({
    name: "GalleryView",
    components: {
        // eslint-disable-next-line vue/no-unused-components
        headerComponent,
        footerComponent,
        mainContainer
    },
    data() {
        return {
            imageUrl: null,
            plantId: 1,
            imgUrls: [],
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
                await axios.post("http://localhost:9000/upload", {
                    userId: 1,
                    uploadLink: this.imageUrl,
                    plantId: 1
                }).then(() => {
                    let response = axios.get(`http://localhost:9000/greenhouse/get-all/${this.plantId}`).then((x) => {
                        this.imgUrls = x.data;
                    })
                })
            } catch (error) {
                console.error(error);
            }
        }
    },
    mounted() {
        const response = axios.get(`http://localhost:9000/greenhouse/get-all/${this.plantId}`).then((x) => {
            this.imgUrls = x.data;
            console.log(x.data);
        })
    }
});
</script>

<template>
    <v-app>
        <header-component/>
        <main-container negative>
            <div class="grid grid-cols-5">
                <img v-for="(img, index) in imgUrls" :key="index" :src="img" alt="failed" referrerpolicy="no-referrer" />
            </div>

            <input type="file" ref="fileInput" @change="uploadImage" />

        </main-container>
        <footer-component/>
    </v-app>
</template>

<style>

html { overflow-y: auto !important; }

</style>
