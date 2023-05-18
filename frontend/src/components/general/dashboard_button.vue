<template>
    <article class="cursor-pointer hover:text-primary overflow-hidden rounded-md shadow-xl border border-primary bg-white pa-5 h-full">
        <div class="flex align-center">
            <div class="rounded-full bg-gray-100 h-24 w-24 flex items-center justify-center circle-icon flex-shrink-0">
                <v-icon :icon="icon" size="60" class="text-primary"></v-icon>
            </div>
            <div class="mx-10">
                <p class="font-primary text-[32px] font-weight-bold">{{ title }}</p>
                <p class="font-secondary text-[18px]">{{ description }}</p>
            </div>
        </div>
    </article>
</template>
<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
    props: {
        title: {
            required: true
        },
        description: {
            required: true
        },
        icon: {
            type: String as () => string,
            required: true
        },
    },
})

</script>