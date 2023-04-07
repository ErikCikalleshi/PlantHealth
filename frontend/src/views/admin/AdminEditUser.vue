<template>
  <v-app>
    <header-component/>
    <main-container negative>
      <div class="flex flex-wrap">
        <page-heading>Edit User</page-heading>
        <div class="mx-auto my-12 w-full h-full text-black flex-wrap flex">
          <div class=" w-1/2">
            <div class="flex-wrap flex align-center">
              <div class="rounded-full w-64 h-64 object-cover shadow-lg">
                <v-img class="block rounded-full bg object-scale-down"
                       src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg"></v-img>
              </div>
              <div class="pl-20">
                <div>
                  <div class="flex flex-col space-y-4">
                    <v-label text="Roles"/>
                    <div class="flex flex-row space-x-4">
                      <button v-bind:class="{'bg-primary text-white': user.roles.includes('USER'), 'bg-gray-300 text-gray-700': !user.roles.includes('USER')}"
                              v-on:click="toggleUserRole('USER')"
                              class="px-4 py-2 rounded-lg shadow-md transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2 focus:ring-offset-gray-100"
                      >
                        User
                      </button>
                      <button v-bind:class="{'bg-primary text-white': user.roles.includes('GARDENER'), 'bg-gray-300 text-gray-700': !user.roles.includes('GARDENER')}"
                              v-on:click="toggleUserRole('GARDENER')"
                              class="px-4 py-2 rounded-lg shadow-md transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2 focus:ring-offset-gray-100"
                      >
                        Gardener
                      </button>
                      <button v-bind:class="{'bg-primary text-white': user.roles.includes('ADMIN'), 'bg-gray-300 text-gray-700': !user.roles.includes('ADMIN')}"
                              v-on:click="toggleUserRole('ADMIN')"
                              class="px-4 py-2 rounded-lg shadow-md transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2 focus:ring-offset-gray-100"
                      >
                        Admin
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="grid gap-6 mb-6">
              <div>
                <v-label text="Username"/>
                <v-text-field v-model="user.username" color="primary" hide-details type="text" readonly
                              placeholder="Username"/>
              </div>
              <div>
                <v-label text="Firstname"/>
                <v-text-field v-model="user.firstname" color="primary" hide-details type="text"
                              placeholder="Firstname"/>
              </div>
              <div>
                <v-label text="Lastname"/>
                <v-text-field v-model="user.lastname" color="primary" hide-details type="text"
                              placeholder="Lastname"/>
              </div>
              <div>
                <v-label text="E-Mail"/>
                <v-text-field v-model="user.email" color="primary" hide-details type="text"
                              placeholder="E-Mail"/>
              </div>
            </div>
            <v-btn class="font-primary text-white bg-primary" @click="updateUser(user)">
              Save changes
            </v-btn>
          </div>
        </div>
      </div>
    </main-container>
    <footer-component/>
  </v-app>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import AdminUserService from "@/services/admin/AdminUserService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import type IUser from "@/interfaces/user/IUser";
export default defineComponent({
  name: "adminEditUser",
  components: {
    headerComponent,
    footerComponent,
    mainContainer,
    PageHeading,
  },
  props: {
    username: {
      type: String,
      default: 'default username'
    },
  },
  setup(props) {
    let user = ref<IUser>({username: '', firstname: '', lastname: '', created: '', email: '', roles: []});
    AdminUserService.getSingleUser(props.username).then((response) => {
      user.value = (response.data);
    })
    return {
      user
    }
  },
  methods: {
    toggleUserRole(role: string) {
      if (this.user.roles.includes(role)) {
        this.user.roles = this.user.roles.filter(item => item !== role)
      } else {
        this.user.roles.push(role)
      }
    },
    updateUser(user: IUser) {
      AdminUserService.updateUser(user);
    }
  }
})
</script>