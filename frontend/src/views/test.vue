<template>
  <div>{{store.username}} <oh-icon name="fa-user-circle" scale="1"></oh-icon></div>
</template>

<script lang="ts">
import {useStore} from "@/stores/user/user";
export default {
  name: "test-l",
  data() {
    return {
      store: useStore(),

    }
  }
}
</script>

<style scoped>

</style>