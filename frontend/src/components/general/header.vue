<script lang="ts">
import {defineComponent, mergeProps} from "vue";
import {OhVueIcon} from "oh-vue-icons";
import {useStore as userStore, useStore} from "@/stores/user/user";
import VDropdown from "@/components/general/v-dropdown.vue";
import AuthService from "@/services/auth.service";
import type IUser from "@/interfaces/user/IUser";
import Dashboard_button from "@/components/general/dashboard_button.vue";
import Custom_snackbar from "@/components/general/snackbar.vue";
export default defineComponent({
    name: "HeaderView",
    props: {
        presentation: Boolean,
    },
    data() {
        return {
            store: useStore(),
            menu: false,
            menuDashboard: false,
            hyperLinks: [
                {
                    title: 'Home',
                    route: '/',
                },
                {
                    title: 'Dashboard',
                    route: '/dashboard',
                },
                // {
                //     title: 'About',
                //     route: '#about',
                // },
                {
                    title: 'Logout',
                    route: '/logout',
                },

            ],
        }
    },
    components: {
        Custom_snackbar,
        Dashboard_button,
        VDropdown,
        // eslint-disable-next-line vue/no-unused-components
        OhVueIcon
    },
    methods: {
        async logout() {
            let response = await AuthService.logout();
            if (response.status === 200) this.$router.push({name: "login"});
        },
        mergeProps
    },
    setup() {
        const user: IUser = userStore()
        const isAdmin = user.roles.includes('ADMIN')
        const isGardener = user.roles.includes('GARDENER')
        return {
            isAdmin,
            isGardener,
        }
    },
})
</script>

<template>
    <custom_snackbar></custom_snackbar>
    <div :class="{ 'h-[300px]': !presentation }" class="header-view w-[100vw] font-primary">
        <div class="md:py-7 py-15 px-6 container mx-auto flex items-center justify-between text-white font-primary text-[16px]">
            <div class="flex logo">
                <router-link to="/">
                    <img alt="Plant Health Gardening LOGO" class="w-[200px] md:w-[270px]" src="../../assets/logo.svg"/>
                </router-link>
            </div>

            <!--              <dashboard_button v-if="isAdmin" title="Users" icon="mdi-account-group" description="View and Manage all users" @click="$router.push({name:'manage-users'})"/>-->
            <!--                <dashboard_button v-if="isAdmin" title="Audit Log" icon="mdi-shield-lock" description="View audit logs" @click="$router.push('/admin/audit-log')"/>-->
            <!--                <dashboard_button title="All Plants" icon="mdi-sprout" description="View all plants" @click="$router.push('/dashboard/plants')"/>-->
            <!--                <dashboard_button v-if="isAdmin" title="Access Points" icon="mdi-raspberry-pi" description="View and Manage all access points" @click="$router.push({name:'manage-accessPoints'})"/>-->
            <!--                <dashboard_button v-if="isGardener" title="My Plants" icon="mdi-sprout-outline" description="View and manage my plants"  @click="$router.push('/dashboard/my_plants')"/>-->

            <div class="hidden xl:flex gap-[100px] justify-between align-center">
                <!--                <router-link v-for="(hyperLink, key) of hyperLinks" :key="key" :to="hyperLink.route">{{-->
                <!--                    hyperLink.title-->
                <!--                    }}-->
                <!--                </router-link>-->
            </div>
            <v-menu transition="slide-y-transition" v-if="store.username.length > 0">
                <template v-slot:activator="{ props: menu }">
                    <v-btn variant="text" size="x-large" class="hidden xl:flex" v-bind="menu">
                        <template v-slot:prepend>
                            <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
                        </template>
                        <span>{{ store.firstname }}</span>
                    </v-btn>
                </template>
                <v-list class="font-primary align-center">
                    <v-list-item link v-if="isAdmin" @click="$router.push('/admin/audit-log')" title="Audit Log"/>
                    <v-list-item link v-if="isAdmin" @click="$router.push({name:'manage-users'})" title="Users"/>
                    <v-list-item link v-if="isAdmin" @click="$router.push({name:'manage-accessPoints'})"
                                 title="Access Points"/>
                    <v-list-item link v-if="isGardener" @click="$router.push('/dashboard/my_plants')"
                                 title="Manage Plants"/>
                    <v-list-item link @click="$router.push('/dashboard/plants')" title="All Plants"/>
                    <v-list-item link @click="logout()">
                        <v-list-item-title>Logout</v-list-item-title>
                    </v-list-item>

                </v-list>
            </v-menu>
            <v-btn variant="text" size="x-large" class="hidden xl:flex" @click="$router.push({name: 'login'})" v-else>
                <template v-slot:prepend>
                    <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
                </template>
                <span>Login</span>
            </v-btn>
            <v-btn icon variant="text" size="x-large" class="flex xl:hidden" @click="menu = true;">
                <oh-icon name="hi-menu-alt-3" scale="2"></oh-icon>
            </v-btn>
        </div>
        <Transition name="slide-in-right">
            <div v-if="menu" class="z-50 fixed right-0 top-0 bg-black h-[100vh] w-[300px] p-10 flex flex-col gap-14">
                <oh-icon name="pr-times" scale="2" @click="menu = false;"></oh-icon>

                <div class="flex  gap-[20px] flex-col justify-between text-[24px]">
                    <router-link v-for="(hyperLink, key) of hyperLinks" :key="key" :to="hyperLink.route" @click="(hyperLink.title.toLowerCase() === 'logout') && logout()">
                        {{ hyperLink.title }}
                    </router-link>

                </div>
                <div class="text-[20px]">
                    <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
                    {{ store.firstname }}
                </div>
                <!--                <v-menu>-->
                <!--                    <template v-slot:activator="{ props: menu }">-->
                <!--                        <div class="flex gap-4 items-center" v-bind="menu">-->
                <!--                            <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown">-->
                <!--                                <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>-->
                <!--                            </button>-->
                <!--                            <div>{{ store.firstname }}</div>-->
                <!--                        </div>-->
                <!--                    </template>-->
                <!--                    <v-list>-->
                <!--                        <v-list-item>-->
                <!--                            <v-list-item-title>Logout</v-list-item-title>-->
                <!--                        </v-list-item>-->
                <!--                    </v-list>-->
                <!--                </v-menu>-->
            </div>
        </Transition>
        <slot></slot>
    </div>
</template>

<style scoped>
.header-view {
    background-image: url("@/assets/header/background.png");
    background-size: cover;
    background-position: center center;
}

.logo img {
    max-width: unset;
}

.navigation p {
    padding: 0 50px;
}

html {
    overflow-y: auto !important;
}
</style>