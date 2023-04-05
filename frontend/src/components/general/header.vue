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
      menu: false,
      hyperLinks: [
        {
          title: 'Home',
          route: '/',
        },
        {
          title: 'About',
          route: '/about',
        },
        {
          title: 'Services',
          route: '/services',
        },
        {
          title: 'Prices',
          route: '/prices',
        },
        {
          title: 'Contact',
          route: '/contact',
        },
      ]
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
  <div class="header-view h-[30vh] w-[100vw] font-primary">
    <div class="py-15 px-6 container mx-auto flex items-center justify-between text-white font-primary text-[16px]">
      <div class="flex logo">
        <img alt="Plant Health Gardening LOGO" class="w-[200px] md:w-[270px]" src="../../assets/logo.svg"/>
      </div>
      <div class="hidden xl:flex gap-[100px] justify-between">
        <router-link v-for="(hyperLink, key) of hyperLinks" :key="key" :to="hyperLink.route">{{ hyperLink.title }}</router-link>
      </div>
      <v-dropdown placement="right" class="hidden xl:flex">
        <template v-slot:button>
          <div class="flex gap-4 items-center">
            <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown">
              <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
            </button>
            <div>{{ store.firstname }}</div>
          </div>
        </template>
        <template v-slot:content>
          <a class="flex w-full justify-between items-center rounded px-2 py-1 my-1 hover:bg-primary hover:text-white"
             @click="logout">Logout</a>
        </template>
      </v-dropdown>
      <v-btn icon variant="text" size="x-large" class="flex xl:hidden" @click="menu = true;">
        <oh-icon name="hi-menu-alt-3" scale="2"></oh-icon>
      </v-btn>
    </div>
    <Transition name="slide-in-right">
      <div v-if="menu" class="fixed right-0 top-0 bg-black h-[100vh] w-[300px] p-10 flex flex-col gap-14">
        <oh-icon name="pr-times" scale="2" @click="menu = false;"></oh-icon>
        <div class="flex gap-[20px] flex-col justify-between text-[24px]">
          <router-link v-for="(hyperLink, key) of hyperLinks" :key="key" :to="hyperLink.route">{{ hyperLink.title }}</router-link>
        </div>
        <v-dropdown placement="right" class="flex">
          <template v-slot:button>
            <div class="flex gap-4 items-center">
              <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown">
                <oh-icon class="fill-white" name="hi-solid-user" scale="1.5"></oh-icon>
              </button>
              <div>{{ store.firstname }}</div>
            </div>
          </template>
          <template v-slot:content>
            <a class="flex w-full justify-between items-center rounded px-2 py-1 my-1 hover:bg-primary hover:text-white"
               @click="logout">Logout</a>
          </template>
        </v-dropdown>
      </div>
    </Transition>
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
  margin-bottom: -20px;
}
.navigation p {
  padding: 0 50px;
}
html {
  overflow-y: auto !important;
}
</style>