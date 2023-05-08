<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex center justify-space-between mb-10">
                <page-heading class="text-white">Users</page-heading>
                <div class="flex items-center">
                    <div class="ml-auto">
                        <add-users-dialog-form :users="users"/>
                    </div>
                    <div class="w-[220px]">
                        <v-text-field
                                :loading="loading"
                                density="compact"
                                variant="solo"
                                label="Search user"
                                append-inner-icon="mdi-magnify"
                                single-line
                                hide-details
                                v-model="searchValue"
                                @click:append-inner="loading = true;"
                        ></v-text-field>
                    </div>

                </div>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-x-4 gap-y-8">
                <user_card v-for="user in userList" :key="user.username" :user="user"
                           class="w-full" @confirm="deleteUser(user)"/>
            </div>
        </main-container>
      <footer-component class="mt-auto"/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AdminUserService from "@/services/admin/AdminUserService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import type IUser from "@/interfaces/user/IUser";
import user_card from "@/components/admin/user_card.vue";
import AddUsersDialogForm from "@/components/admin/add_user.vue";

export default defineComponent({
    name: "adminManageUsers",
    components: {
        AddUsersDialogForm,
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
        user_card,
    },
    data() {
        return {
            users: [] as IUser[],
            searchValue: '',
            isOpen: false,
            selectedRoles: [],
            loading: false,
        }
    },
    methods: {
        getAllUsers() {
            AdminUserService.getAllUsers().then((response) => {
                this.users = response.data;
            })
        },
        deleteUser(user: IUser | null) {
            if (user == null) {
                return;
            }
            AdminUserService.deleteUser(user.username).then(() => {
                this.users.splice(this.users.indexOf(user), 1);
            })
        },
    },
    computed: {
        userList() {
            if (this.searchValue.trim().length > 0) {
                return this.users.filter((user) => {
                    return user.firstname.toLowerCase().includes(this.searchValue.toLowerCase()) ||
                        user.lastname.toLowerCase().includes(this.searchValue.toLowerCase()) ||
                        user.username.toLowerCase().includes(this.searchValue.toLowerCase())
                })
            }
            return this.users;
        }
    },
    created() {
        this.getAllUsers()
    }
})
</script>