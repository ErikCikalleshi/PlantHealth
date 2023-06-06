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
import adminGreenhouseService from "@/services/admin/AdminGreenhouseService";


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
    mounted: async function() {
        const response = await adminGreenhouseService.getAllGreenhousesForCurrentUser();
        const greenhouses = response.data;
        let storedDisplayIcons = JSON.parse(localStorage.getItem('displayIcons') || '{}');

        // Loop through the greenhouses and set the displayIcon property
        for (const greenhouse of greenhouses) {
            if (storedDisplayIcons.hasOwnProperty(greenhouse.uuid)) {
                // Set the displayIcon value from localStorage
                greenhouse.displayIcon = storedDisplayIcons[greenhouse.uuid];
            }
        }
        let eventSource = new EventSource('http://localhost:9000/emitter');

        eventSource.onmessage = (event) => {

            // Check if event.data is an integer
            const data = parseInt(event.data);

            if (!isNaN(data)) {
                // Display the snackbar
                this.showSnackbar(data);
                const selectedGreenhouse = greenhouses.find((greenhouse: any) => greenhouse.uuid === data);
                if (selectedGreenhouse) {
                    selectedGreenhouse.displayIcon = true;

                    // Update the stored displayIcon value in localStorage
                    storedDisplayIcons[selectedGreenhouse.uuid] = true;
                    localStorage.setItem('displayIcons', JSON.stringify(storedDisplayIcons));
                    console.log(JSON.parse(localStorage.getItem('displayIcons') || '{}'));
                }
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
