<script lang="ts">
import {defineComponent} from "vue";
import {OhVueIcon} from "oh-vue-icons";
import AdminUserService from "@/services/admin/AdminUserService";
import {useStore} from "@/stores/user/user";


export default defineComponent({
  name: "AdminManageUsers",
  data() {
    return {
      users: [],
      useStore: useStore(),
    }
  },
  methods: {
    getAllUsers() {
      AdminUserService.getAllUsers().then((response) => {
        this.users = response.data;
        console.log(response.data);
      })
    }
  },
  created() {
    this.getAllUsers()
  }
})
</script>

<template>
  <v-app>
    <div class="container my-12 mx-auto px-4 md:px-12">
      <div class="flex flex-wrap -mx-1 lg:-mx-4">
        <div v-for="user in users" class="my-1 px-1 w-full md:w-1/2 lg:my-4 lg:px-4 lg:w-1/3">
          <article class="max-w-sm overflow-hidden rounded-lg shadow-lg p-2 md:p-4">
              <div class="flex grid grid-cols-3 gap-4 pb-5">
                <img alt="PlaceholderUserProfilePicture" class="block rounded-full"
                     src="https://picsum.photos/80/80/?random">
                <header class="col-span-2">
                  <h3 class="text-lg">
                    <p class="text-center no-underline hover:underline text-black">
                      {{ user.firstname }} {{ user.lastname }}
                    </p>
                  </h3>
                  <p class="text-center pb-5" >{{ user.created }}</p>
                  <div class="flex flex-row justify-center">
                    <div v-for="role in user.roles">
                      <p class="border-2 border-black rounded-lg mx-1 px-1">{{ role }}</p>
                    </div>
                  </div>

                </header>
              </div>
              <div class="flex">
                <v-icon icon="mdi-email-outline" size="25px" class="px-6"></v-icon>
                <p>{{ user.email }}</p>
              </div>
              <div class="flex">
                <v-icon icon="mdi-email-outline" size="25px" class="px-6"></v-icon>
                <p>{{ user.username }}</p>
              </div>
              <div class="flex">
                <button class="btn btn-primary">Edit User</button>
              </div>
          </article>


        </div>


      </div>
    </div>
  </v-app>
</template>