<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div>
                <div class="flex center justify-space-between mb-10">
                    <page-heading>Users</page-heading>
                    <div class="flex items-center justify-start align-center">
                        <div class="relative text-gray-50 focus-within:text-gray-400">
                            <input type="text" v-model="searchValue" name="q" class="py-2 text-sm text-gray-50 placeholder-gray-50 text-right focus-within:placeholder-gray-400  bg-gray-100 bg-opacity-50 rounded-md pl-10 pr-5 focus:outline-none focus:bg-white focus:text-gray-900" placeholder="Search user..." autocomplete="off">
                            <span class="absolute inset-y-0 left-0 flex items-center pl-2">
                              <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" class="w-6 h-6"><path
                                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                          </span>
                        </div>
                    </div>
                </div>
                <div class="mx-auto w-full h-full flex justify-center">
                    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-x-4 gap-y-8">
                        <user_card v-for="user in userList" :key="user.username" :user="user"
                                   @delete-user="selectedUser = user" class="w-full"/>
                        <Modal :is-visible="selectedUser !== null" @cancel="selectedUser = null"
                               @confirm="deleteUser(selectedUser)">
                            <template v-slot:title>
                                <p>Confirm Delete</p>
                            </template>
                            <template v-slot:body>
                                <p>Are you sure you want to delete this user? This cannot be undone.</p>
                            </template>
                            <template v-slot:confirm-button>
                                <button class="bg-plant-health-red text-white rounded-lg ml-3 px-3 py-1">Delete</button>
                            </template>
                        </Modal>
                    </div>
                </div>
            </div>
        </main-container>
        <footer-component/>
    </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AdminUserService from "@/services/admin/AdminUserService";
import headerComponent from "@/components/header.vue";
import footerComponent from "@/components/footer.vue";
import mainContainer from "@/components/main_container.vue";
import PageHeading from "@/components/PageHeading.vue";
import Modal from "@/components/Modal.vue";
import type IUser from "@/interfaces/user/IUser";
import {useStore as tokenStore} from "@/stores/token/token";
import user_card from "@/components/user_card.vue";

export default defineComponent({
    name: "adminManageUsers",
    components: {
        headerComponent,
        footerComponent,
        mainContainer,
        Modal,
        PageHeading,
        user_card,
    },
    data() {
        return {
            users: [] as IUser[],
            deleteUserModalVisible: false,
            selectedUser: null as IUser | null,
            tokenStore: tokenStore(),
            searchValue: '',
            isOpen: false,
            selectedRoles: []
        }
    },
    methods: {
        getAllUsers() {
            AdminUserService.getAllUsers(this.tokenStore.accessToken).then((response) => {
                this.users = response.data;
            })
        },
        deleteUser(user: IUser | null) {
            if (user == null) {
                return;
            }
            AdminUserService.deleteUser(this.tokenStore.accessToken, user.username).then(() => {
                this.users.splice(this.users.indexOf(user), 1);
            })
            this.selectedUser = null;
            this.deleteUserModalVisible = false;
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