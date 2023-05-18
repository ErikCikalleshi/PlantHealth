<template>
    <div v-if="loading" class="absolute inset-0 bg-gray-300 bg-opacity-50 flex justify-center items-center z-10">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
    name: "LoadingOverlay",
    props: {
        loading: {
            type: Boolean,
            required: true
        }
    },
});
</script>
