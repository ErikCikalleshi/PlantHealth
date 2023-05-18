<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
    name: "About",
})
</script>

<template class="bg-amber">
    <v-app>
        <div class="about">
            <div class="grid items-center">
                <div class="flex flex-col h-screen justify-between items-center">
                    <img alt="Plant Health LOGO" src="@/assets/logo.svg" class="p-[31px]"/>
                    <p class="text-white font-primary font-normal text-h2 leading-[15.52px] mx-auto">About us</p>
                    <div class="grid grid-cols-1 xl:grid-cols-2 mt-[114px] place-items-center gap-[40px] mx-32">
                        <span class="justify-center text-left bg-gray-100 pa-10 rounded font-secondary text-[18px] leading-[138%]">
                                                At PlantHealth, we believe that keeping your plants healthy and thriving should be simple and
                        hassle-free. Our innovative service makes it easy to monitor the vital data of various plants in
                        mini-greenhouses using state-of-the-art technology. <br>
                        Each plant is equipped with an Arduino controller that collects and transmits data to a
                        Raspberry Pi access point via Bluetooth Low Energy (BLE) technology. With multiple access points
                        available, each with many plants, our system can keep track of all plants in a company,
                        providing real-time data about their growth and health.<br>
                        Our service is designed with simplicity and efficiency in mind. Only admins and gardeners have
                        the ability to manage sensor limits, ensuring that each plant receives the optimal conditions
                        for its growth. Additionally, only admins can manage the transmission interval, giving them
                        complete control over how often data is transmitted to the web server.

                        On the web server, gardeners can see the change in the sensor values via charts, allowing them
                        to detect any issues and take necessary action to maintain the health of the plant.
                        Non-registered users can upload images of the greenhouse or plant via the QR code on the
                        mini-greenhouse. Registered users can view all plants in the system and browse through the
                        uploaded pictures, providing an easy way to monitor the progress of each plant.

                        Our goal at PlantHealth is to provide a user-friendly, efficient, and effective service that
                        helps companies take care of their plants in a simple and hassle-free way. Contact us today to
                        learn more about how our service can benefit your company's plant management needs.
                        </span>
                        <img alt="projectPic" src="@/assets/projectExample.png"/>
                    </div>
                    <p class="text-white font-primary font-normal text-[16px] leading-[15.52px]  p-[31px]">
                        Copyright ⓒ 2023. PlantHealth. All rights reserved.
                    </p>
                </div>
            </div>
        </div>
    </v-app>
</template>

<style>
.about {
    background-color: black;
    background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0.0), rgba(0, 0, 0, 1)), url("@/assets/login/background.png");
    background-size: cover;
    height: auto;

}

html {
  background-color: black;
  overflow-y: auto !important;
}

</style>
