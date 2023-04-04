<template>
    <article class="overflow-hidden rounded-2xl shadow-xl border border-primary bg-white pa-3 font-roboto h-full">
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
                <div v-for="role in user.roles.sort()">
                    <p class="my-1 border-2 text-sm rounded-lg mx-1 px-1"
                       :class="'border-'+getColorByRole(role) + ' text-' +getColorByRole(role)">
                        {{ role }}
                    </p>
                </div>
            </div>
            <div class="flex justify-center gap-4 h-full">
                <button class="bg-primary rounded-lg px-3"
                        @click="$router.push({name:'admin-edit-user', params:{username: user.username}})">
                    Edit User
                </button>
                <button @click="$emit('delete-user')" class="bg-plant-health-red text-white rounded-lg px-3 py-1">
                    Delete User
                </button>
            </div>
        </div>
    </article>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import type IUser from "@/interfaces/user/IUser";

export default defineComponent({
    data() {
        return {
            deleteUserModalVisible: false,
            userToDelete: null as unknown as IUser,
        }
    },
    props: {
        user: {
            type: Object as () => IUser,
            required: true
        },
    },
    methods: {
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
    }
});
</script>