<template>
    <v-app>
        <custom_snackbar></custom_snackbar>
        <header-component/>
        <main-container negative class="mb-10">
            <div>
                <page-heading>Welcome back,</page-heading>
                <p class="my-3 font-secondary text-[20px]">{{ userStore.firstname }}</p>
            </div>
            <div class="mt-5 grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-x-8 gap-y-8">
                <dashboard_button v-if="isAdmin" title="Users" icon="mdi-account-group" description="View and Manage all users" @click="$router.push({name:'manage-users'})"/>
                <dashboard_button v-if="isAdmin" title="Audit Log" icon="mdi-shield-lock" description="View audit logs" @click="$router.push('/admin/audit-log')"/>
                <dashboard_button title="All Plants" icon="mdi-sprout" description="View all plants" @click="$router.push('/dashboard/plants')"/>
                <dashboard_button v-if="isAdmin" title="Access Points" icon="mdi-raspberry-pi" description="View and Manage all access points" @click="$router.push({name:'manage-accessPoints'})"/>
                <dashboard_button v-if="isGardener" title="My Plants" icon="mdi-sprout-outline" description="View and manage my plants"  @click="$router.push('/dashboard/my_plants')"/>
            </div>
        </main-container>
        <footer-component class="mt-auto" />
    </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import {useStore as userStore} from "@/stores/user/user";
import type IUser from "@/interfaces/user/IUser";
import Dashboard_button from "@/components/general/dashboard_button.vue";
import Custom_snackbar from "@/components/general/snackbar.vue";

export default defineComponent({
    name: "Dashboard",
    components: {
        Dashboard_button,
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
        Custom_snackbar,
    },
    data() {
        return {
            userStore: userStore(),
        }

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