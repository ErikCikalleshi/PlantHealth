<script lang="ts">
import {useStore as userStore} from "@/stores/user/user";
import {useStore as tokenStore} from "@/stores/token/token";
import * as service from "@/services/user";
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
      this.setCookie('accessToken', newValue, 60);
      await service.setUser(newValue);
    },
    'tokenStore.refreshToken'(newValue: string, _) {
      this.setCookie('refreshToken', newValue, 3600);
    },
  },
  methods: {
    setCookie(key: string, value: string, expire: number) {
      this.$cookies.set(key, value, expire);
    }
  },
  async created() {
    let accessToken = this.$cookies.get('accessToken');
    if(accessToken) {
      await service.setUser(accessToken);
      return;
    }
  }
})
</script>

<template>
  <router-view></router-view>
</template>