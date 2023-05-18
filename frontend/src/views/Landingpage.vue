<template>
    <v-app>
        <header-view presentation>
            <div class="container mx-auto h-[64vh]">
<!--                <v-carousel height="64vh"-->
<!--                            theme="light"-->
<!--                            cycle-->
<!--                            hide-delimiter-background-->
<!--                >-->
<!--                    <v-carousel-item>-->
                        <div class="carousel__item">
                            <h2 class="text-[44px] md:text-[60px]">Start tracking your plants with <br><span
                                    class="text-[54px] md:text-[80px]">Plant Health</span></h2>
                            <router-link class="dashboard-button" to="/dashboard" tag="button">View Dashboard
                            </router-link>
                        </div>
<!--                    </v-carousel-item>-->
<!--                    <v-carousel-item>-->
<!--                        <div class="carousel__item">-->
<!--                            <h2 class="text-[44px] md:text-[60px]">Start tracking your plants with <br><span-->
<!--                                    class="text-[54px] md:text-[80px]">Plant Health</span></h2>-->
<!--                            <router-link class="dashboard-button" to="/dashboard" tag="button">View Dashboard-->
<!--                            </router-link>-->
<!--                        </div>-->
<!--                    </v-carousel-item>-->
<!--                    <v-carousel-item>-->
<!--                        <div class="carousel__item">-->
<!--                            <h2 class="text-[44px] md:text-[60px]">Start tracking your plants with <br><span-->
<!--                                    class="text-[54px] md:text-[80px]">Plant Health</span></h2>-->
<!--                            <router-link class="dashboard-button" to="/dashboard" tag="button">View Dashboard-->
<!--                            </router-link>-->
<!--                        </div>-->
<!--                    </v-carousel-item>-->
<!--                </v-carousel>-->
            </div>
        </header-view>
        <div class="container mx-auto">
            <div class="grid grid-cols-1 xl:grid-cols-2 mt-[114px] place-items-center gap-[40px]">
                <div class="flex flex-col">
                    <div class="flex gap-2">
                        <img alt="leaf" src="../assets/leaf.svg"/>
                        <div>
                            <h2 class="font-primary text-primary text-uppercase text-[24px] leading-[23.28px]">About
                                Plant Health</h2>
                            <p class="font-secondary text-[16px] leading-[15.52px] mt-[4px]">Everything you need to know
                                about Plant Health.</p>
                        </div>
                    </div>
                    <p class="font-secondary text-[20px] leading-[138%] pt-[50px]">
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

<!--                        On the web server, gardeners can see the change in the sensor values via charts, allowing them-->
<!--                        to detect any issues and take necessary action to maintain the health of the plant.-->
<!--                        Non-registered users can upload images of the greenhouse or plant via the QR code on the-->
<!--                        mini-greenhouse. Registered users can view all plants in the system and browse through the-->
<!--                        uploaded pictures, providing an easy way to monitor the progress of each plant.-->

<!--                        Our goal at PlantHealth is to provide a user-friendly, efficient, and effective service that-->
<!--                        helps companies take care of their plants in a simple and hassle-free way. Contact us today to-->
<!--                        learn more about how our service can benefit your company's plant management needs.-->
                    </p>
<!--                    <router-link class="text-primary font-primary text-[20px] underline hover:no-underline mt-[20px]" to="about">View-->
<!--                        More</router-link>-->
                </div>
                <img alt="projectPic" src="@/assets/projectExample.png"/>
            </div>
            <div class="w-full bg-[#040A0F] rounded-[16px] mt-[110px] mb-[110px]">
                <div class="p-[75px]">
                    <div class="flex gap-4">
                        <img alt="leaf" src="@/assets/leaf.svg"/>
                        <div>
                            <h2 class="text-uppercase font-primary text-primary text-[24px] leading-[23.28px] pb-[4px]">
                                Contact</h2>
                            <p class="font-secondary text-gray-500 text-[16px] leading-[15.52px] mt-[4px]">If you have
                                any enquiries, fell free to contact us.</p>
                        </div>
                    </div>
                    <div class="grid grid-cols-1 xl:grid-cols-2 pt-[60px] gap-[100px]">
                        <div class="flex flex-col items-end">
                            <div class="w-full">
                                <h2 class="font-primary text-[20px] text-white">Full Name</h2>
                                <v-text-field v-model="fullName"
                                              color="primary"
                                              hide-details
                                              type="text"
                                              variant="outlined"
                                              placeholder="Please enter your name"
                                              bg-color="white"
                                              class="w-full font-primary"
                                ></v-text-field>
                            </div>
                            <div class="w-full">
                                <h2 class="font-primary text-[20px] text-white pt-[16px]">Email</h2>
                                <v-text-field v-model="email"
                                              color="primary"
                                              hide-details
                                              type="text"
                                              variant="outlined"
                                              placeholder="Please enter your email address"
                                              bg-color="white"
                                              class="w-full font-primary"
                                ></v-text-field>
                            </div>
                            <div class="w-full">
                                <h2 class="font-primary text-[20px] text-white pt-[16px]">Comments</h2>
                                <v-textarea v-model="description"
                                            color="primary"
                                            hide-details
                                            type="text"
                                            variant="outlined"
                                            placeholder="Description"
                                            bg-color="white"
                                            class="w-full h-[200px] font-primary"
                                ></v-textarea>
                            </div>
                            <v-btn height="60" width="160" color="primary" class="text-lg tracking-normal">
                                <img class="pr-[10px]" alt="send" src="@/assets/send.svg"/>Send
                            </v-btn>
                        </div>
                        <div>
                            <div class="flex">
                                <img alt="social-media-facebook" src="@/assets/facebook-sign.svg" class="pr-[33px]"/>
                                <img alt="social-media-facebook" src="@/assets/facebook-sign.svg" class="pr-[33px]"/>
                                <img alt="social-media-facebook" src="@/assets/facebook-sign.svg" class="pr-[33px]"/>
                                <img alt="social-media-facebook" src="@/assets/facebook-sign.svg"/>
                            </div>
                            <h2 class="text-white font-primary pt-[32px]">Working hours:</h2>
                            <p class="text-white font-secondary">— Monday: 08:00 - 17:00</p>
                            <p class="text-white font-secondary">— Tuesday: 08:00 - 17:00</p>
                            <p class="text-white font-secondary">— Wednesday: 08:00 - 17:00</p>
                            <p class="text-white font-secondary">— Thursday: 08:00 - 17:00</p>
                            <p class="text-white font-secondary">— Friday: 08:00 - 17:00</p>
                            <div class="pt-[70px]">
                                <p class="font-secondary text-white">Phone Number: <span
                                        class="font-primary text-white">+43 000 000 000</span></p>
                                <p class="font-secondary text-white">Customer Services: <span
                                        class="font-primary text-white">customerservices@plant-health.at</span></p>
                                <p class="font-secondary text-white">Sales: <span class="font-primary text-white">sales@plant-health.at</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer-view></footer-view>
    </v-app>
</template>

<script lang="ts">
import {useStore} from '@/stores/user/user';
import HeaderView from "@/components/general/header.vue";
import FooterView from "@/components/general/footer.vue";
import {router} from "@/router";
import about from "@/views/About.vue";

export default {
    name: 'LandingPage',
    computed: {
        about() {
            return about
        }
    },
    methods: {
        router() {
            return router
        }
    },
    components: {FooterView, HeaderView},
    data() {
        return {
            store: useStore(),
            fullName: '',
            email: '',
            description: ''
        }
    }
}
</script>

<style>
.carousel__item {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    gap: 43px;
    justify-content: center;
    align-items: center;
}

.carousel__item .dashboard-button {
    padding: 18px 35px;
    font-size: 20px;
    background: white;
    outline: none;
}

.v-carousel__controls {
    background: transparent;
}

.v-carousel__controls .v-btn__content {
    color: #ffffff;
}

.carousel__item h2 {
    letter-spacing: -0.05em;
    text-transform: uppercase;
    font-weight: 700;
    line-height: 92.5%;
    color: white;
    text-align: center;
    width: 80%;
}

.carousel__item span {
    line-height: 92.5%;
}
</style>