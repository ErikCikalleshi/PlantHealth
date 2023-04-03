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

export default defineComponent({
    name: "adminManageUsers",
    components: {
        headerComponent,
        footerComponent,
        mainContainer,
        Modal,
        PageHeading,
    },
    data() {
        return {
            users: [] as IUser[],
            deleteUserModalVisible: false,
            userToDelete: null as unknown as IUser,
            tokenStore: tokenStore(),
        }
        const colorVariants = {
            USER: 'border-role-user',
            GARDENER: 'border-role-gardener',
            ADMIN: 'border-role-admin',
        }
    },
    methods: {
        getAllUsers() {
            AdminUserService.getAllUsers(this.tokenStore.accessToken).then((response) => {
                this.users = response.data;
            })
        },
        confirmDeleteUser(user: IUser) {
            this.deleteUserModalVisible = true;
            this.userToDelete = user;
        },
        deleteUser(user: IUser) {
            AdminUserService.deleteUser(this.tokenStore.accessToken, user.username).then(() => {
                this.users.splice(this.users.indexOf(user), 1);
            })
            this.deleteUserModalVisible = false;
        },
        getColorByRole(role: String) {
            switch (role) {
                case "GARDENER":
                    return "role-gardener"
                case "ADMIN":
                    return "role-admin"
                default:
                    return "role-user"
            }
        },
    },
    created() {
        this.getAllUsers()
    }
})
</script>

<template>
    <v-app>
        <header-component/>
        <main-container negative>
            <div class="flex flex-wrap">
                <page-heading>Users</page-heading>
                <div class="mx-auto my-12 w-full h-full">
                    <div class="flex flex-wrap justify-center -mx-7 -my-2">
                        <div v-for="user in users" :key="user.username"
                             class="my-2 px-7 w-full md:w-1/2 lg:my-16 lg:px-16 lg:w-1/3 xl:w-1/4 2xl:w-1/5">
                            <article
                                    class="overflow-hidden rounded-2xl shadow-xl border border-primary  bg-white pa-3 font-roboto h-full">
                                <div class="flex grid grid-cols-3 gap-4 pb-3">
                                    <img alt="PlaceholderUserProfilePicture" class="block rounded-full bg"
                                         src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg">
                                    <header class="col-span-2">
                                        <h3 class="text-sm text-center no-underline hover:underline text-black">
                                            {{ user.firstname }} {{ user.lastname }}
                                        </h3>
                                        <p class="text-center text-xs pb-3">{{ user.created }}</p>
                                    </header>

                                </div>
                                <div class="flex align-center">
                                    <v-icon icon="mdi-account" size="19px" class="px-3"></v-icon>
                                    <p class="text-xs">{{ user.username }}</p>
                                </div>
                                <div class="flex align-center overflow-auto">
                                    <v-icon icon="mdi-email-outline" size="20px" class="px-3"></v-icon>
                                    <p class="text-xs">{{ user.email }}</p>
                                </div>
                                <div class="flex flex-col">
                                    <div class="flex flex-wrap  my-3 justify-center">
                                        <div v-for="role in user.roles">
                                            <p class="my-1 border-2 text-sm rounded-lg mx-1 px-1"
                                               :class="'border-'+getColorByRole(role) + ' text-' +getColorByRole(role)">
                                                {{ role }}</p>
                                        </div>
                                    </div>
                                    <div class="flex justify-center gap-4 h-full">
                                        <button class="bg-primary rounded-lg px-3" @click="$router.push({name:'admin-edit-user', params:{username: user.username}})">Edit User</button>
                                        <button @click="confirmDeleteUser(user)"
                                                class="bg-plant-health-red text-white rounded-lg px-3 py-1">Delete User
                                        </button>
                                    </div>
                                </div>
                            </article>
                        </div>
                        <Modal :is-visible="deleteUserModalVisible" @cancel="deleteUserModalVisible = false"
                               @confirm="deleteUser(userToDelete)">
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