<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "NotFound",
})

</script>

<template>
    <v-app>
        <div class="forbidden">
            <div class="h-[100vh] w-[100vw] grid items-center">
                <div class="flex flex-col h-screen justify-between items-center">
                    <img alt="Plant Health LOGO" src="@/assets/logo.svg" class="p-[31px]"/>
                    <p class="text-white font-primary font-normal text-h2 text-center mx-auto">The page you were trying to access could not be found!</p>
                    <p class="text-white font-primary font-normal text-[16px] leading-[15.52px]  p-[31px]">
                        Copyright ⓒ 2023. PlantHealth. All rights reserved.
                    </p>
                </div>
            </div>
        </div>
    </v-app>
</template>

<style>
.forbidden {
    background-image: url("@/assets/login/background.png");
    background-size: cover;
}

html {
    overflow-y: auto !important;
}

</style>
