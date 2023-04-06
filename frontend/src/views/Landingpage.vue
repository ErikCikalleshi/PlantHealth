<template>
  <div>
    <header-view></header-view>
  </div>
</template>

<script lang="ts">
import {useStore} from '@/stores/user/user';
import HeaderView from "@/components/general/header.vue";
export default {
  name: 'LandingPage',
  components: {HeaderView},
  data() {
    return {
      store: useStore(),
    }
  }
}
</script>

<style scoped>
</style>