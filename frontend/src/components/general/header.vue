<script lang="ts">
import {defineComponent} from "vue";
import {OhVueIcon} from "oh-vue-icons";
import {useStore} from "@/stores/user/user";
import VDropdown from "@/components/general/v-dropdown.vue";
import api from "@/services/api";
import AuthService from "@/services/auth.service";
import login from "@/views/Login.vue";

export default defineComponent({
    name: "HeaderView",
    data() {
        return {
            store: useStore(),
        }
    },
    components: {
        VDropdown,
        // eslint-disable-next-line vue/no-unused-components
        OhVueIcon
    },
    methods: {
        async logout() {
            let response = await AuthService.logout();
            if (response.status === 200) this.$router.push({name: "login"});
        },
    }
})
</script>

<template>
    <div class="header-view h-[30vh] w-[100vw]">
        <div class="pt-15 w-4/5 mx-auto flex justify-between text-white font-primary text-[16px]">
            <div class="flex logo">
                <img alt="Plant Health Gardening LOGO" src="../../assets/logo.svg"/>
            </div>
            <div class="flex gap-10 justify-between">
                <p class="">Home</p>
                <p>About</p>
                <p>Services</p>
                <p>Prices</p>
                <p class="">Contact</p>
                <div class="pl-40 flex align-center">
                    <v-dropdown placement="right">
                        <template v-slot:button>
                            <div>{{ store.firstname }}</div>
                            <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown">
                                <oh-icon class="fill-white ml-4" name="hi-solid-user" scale="1.5"></oh-icon>
                            </button>
                        </template>
                        <template v-slot:content>
                            <a class="flex w-full justify-between items-center rounded px-2 py-1 my-1 hover:bg-primary hover:text-white"
                               @click="logout">Logout</a>
                        </template>

                    </v-dropdown>

                </div>
            </div>

        </div>
    </div>
</template>

<style scoped>
.header-view {
    background-image: url("@/assets/header/background.png");
    background-size: cover;
    background-position: center center;
}

.logo img {
    width: 270px;
    max-width: unset;
    margin-bottom: -20px;
}

.navigation p {
    padding: 0 50px;
}

html {
    overflow-y: auto !important;
}

</style>
