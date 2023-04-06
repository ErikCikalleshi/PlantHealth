<script lang="ts">
import {useStore as userStore} from "@/stores/user/user";
import {useStore as tokenStore} from "@/stores/token/token";
import {defineComponent} from "vue";

export default defineComponent({
  data() {
    return {
      userStore: userStore(),
      tokenStore: tokenStore(),
    }
  },
  watch: {
    async 'tokenStore.accessToken'(newValue: string, _) {
      this.setCookie('accessToken', newValue, 3600); //1 hour
    },
    'tokenStore.refreshToken'(newValue: string, _) {
      this.setCookie('refreshToken', newValue, 86400); //1 day
    },
  },
  methods: {
    setCookie(key: string, value: string, expire: number) {
      this.$cookies.set(key, value, expire);
    }
  },
})
</script>

<template>
  <router-view></router-view>
</template>