<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
    name: "FooterView",
    components: {
        // eslint-disable-next-line vue/no-unused-components
    },
})
</script>

<template>
    <div class="footer-view display-flex w-[100vw]">
        <div class="wrapper gap-14 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
            <img alt="Plant Health Gardening LOGO" width="270" src="../../assets/logo.svg"/>
            <div class="text-white">
                <p class="font-primary text-[20px] mb-3">Documentation:</p>
                <p class="font-secondary text-[16px]">- All categories</p>
                <p class="font-secondary text-[16px]">- Product information portal</p>
                <p class="font-secondary text-[16px]">- Datasheets</p>
            </div>
            <div class="text-white">
                <p class="font-primary text-[20px] mb-3">Company:</p>
                <p class="font-secondary text-[16px]">- Terms of Services</p>
                <p class="font-secondary text-[16px]">- Privacy Policy</p>
                <p class="font-secondary text-[16px]">- GDPR Protection</p>
                <p class="font-secondary text-[16px]">- Cookies Policy</p>
            </div>
            <div class="text-white">
                <p class="font-primary text-[20px] mb-3">Services:</p>
                <p class="font-secondary text-[16px]">- Products</p>
                <p class="font-secondary text-[16px]">- Software</p>
                <p class="font-secondary text-[16px]">- Hardware</p>
            </div>
        </div>
        <div class="flex-1 flex-row">
            <p class="text-center font-secondary text-[16px] text-white opacity-60 mb-[30px]">Copyright ⓒ 2023. PlantHealth. All rights reserved.</p>
        </div>
    </div>
</template>

<style scoped>
.footer-view {
    background-color: #040A0F;
    background-size: cover;
    background-position: center center;
}
.wrapper {
    width: 80%;
    margin: 7vh auto;
}
html {
    overflow-y: auto !important;
}
</style>