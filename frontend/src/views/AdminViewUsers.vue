<script lang="ts">
import {defineComponent} from "vue";
import AdminUserService from "@/services/admin/AdminUserService";
import {useStore} from "@/stores/user/user";
import headerComponent from "@/components/header.vue";
import footerComponent from "@/components/footer.vue";
import mainContainer from "@/components/main_container.vue";
import PageHeading from "@/components/PageHeading.vue";


export default defineComponent({
  name: "AdminManageUsers",
  components: {
    headerComponent,
    footerComponent,
    mainContainer,
    PageHeading
  },
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
    <header-component/>
    <main-container negative>
      <div class="flex flex-wrap">
        <page-heading>Users</page-heading>
        <div class="mx-auto my-12">
          <div class="flex flex-wrap -mx-7">
            <div v-for="user in users" class="my-2 px-7 w-full md:w-1/2 lg:my-16 lg:px-16 lg:w-1/3 xl:w-1/4 2xl:w-1/5">
              <article class="overflow-hidden rounded-2xl shadow-xl border border-primary  bg-white pa-3 font-roboto">
                <div class="flex grid grid-cols-3 gap-4 pb-3">
                  <img alt="PlaceholderUserProfilePicture" class="block rounded-full"
                       src="https://picsum.photos/80/80/?random">
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
                <div class="flex my-3 flex-wrap flex-row justify-center">
                  <div v-for="role in user.roles">
                    <p class="border-2 my-1 border-black text-sm rounded-lg mx-1 px-1">{{ role }}</p>
                  </div>
                </div>
                <div class="flex justify-center gap-4">
                  <button class="bg-primary rounded-lg px-3" py-1>Edit User</button>
                  <button class="bg-plant-health-red text-white rounded-lg px-3 py-1">Delete User</button>
                </div>
              </article>
            </div>


          </div>
        </div>
      </div>
    </main-container>
    <footer-component/>
  </v-app>
</template>