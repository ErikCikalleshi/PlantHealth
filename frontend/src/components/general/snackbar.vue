<template>
    <div>
        <v-snackbar
            v-model="snackbar.show"
            :color="snackbar.color"
            :timeout="snackbar.timeout"
        >
            {{ snackbar.message }}
        </v-snackbar>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";


export default defineComponent({
    name: "custom_snackbar",
    data() {
        return {
            snackbar: {
                show: false,
                color: '',
                message: '',
                timeout: 4000, // Adjust the timeout duration as needed
            },
        };
    },
    mounted() {
        let eventSource = new EventSource('http://localhost:9000/emitter');

        eventSource.onmessage = (event) => {

            // Check if event.data is an integer
            const data = parseInt(event.data);
            if (!isNaN(data)) {
                // Display the snackbar
                this.showSnackbar(data);
            }
        };


    },
    methods: {
        showSnackbar(data: number) {
            this.snackbar.show = true;
            this.snackbar.color = 'success'; // Adjust the snackbar color as needed
            this.snackbar.message = `Warnings were disabled on Greenhouse ${data}`;
        },
    },
});
</script>
