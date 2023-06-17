<template>
    <div>
        <v-snackbar
            v-model="snackbar.show"
            :color="snackbar.color"
            :message="snackbar.message"
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
                color: "",
                message: "",
                timeout: 4000, // Adjust the timeout duration as needed
            },
            eventSource: null as EventSource | null,
        };
    },
    mounted: async function() {
        console.log("mounted")
        const response = await adminGreenhouseService.getAllGreenhousesForCurrentUser();
        const greenhouses = response.data;
        let storedDisplayIcons = JSON.parse(localStorage.getItem("displayIcons") || "{}");

        // Loop through the greenhouses and set the displayIcon property
        for (const greenhouse of greenhouses) {
            if (storedDisplayIcons.hasOwnProperty(greenhouse.uuid)) {
                // Set the displayIcon value from localStorage
                greenhouse.displayIcon = storedDisplayIcons[greenhouse.uuid];
            }
        }

        if (!this.eventSource) {
            this.eventSource = new EventSource("http://localhost:9000/emitter");

            this.eventSource.onmessage = (event) => {
                console.log(parseInt(event.data));
                // Check if event.data is an integer
                const data = parseInt(event.data);

                if (!isNaN(data)) {
                    // Display the snackbar
                    this.showSnackbar(data);
                    const selectedGreenhouse = greenhouses.find((greenhouse: any) => greenhouse.uuid === data);
                    if (selectedGreenhouse) {
                        selectedGreenhouse.displayIcon = true;
                        console.log("inside");
                        // Update the stored displayIcon value in localStorage
                        storedDisplayIcons[selectedGreenhouse.uuid] = true;
                        localStorage.setItem("displayIcons", JSON.stringify(storedDisplayIcons));
                        console.log(JSON.parse(localStorage.getItem("displayIcons") || "{}"));
                    }
                }
            };
        }
    },
    methods: {
        showSnackbar(data: number) {
            console.log("showing snackbar");
            this.snackbar.show = true;
            this.snackbar.color = "success"; // Adjust the snackbar color as needed
            this.snackbar.message = `Warnings were disabled on Greenhouse ${data}`;
            this.snackbar.timeout = 4000; // Adjust the timeout duration as needed
        },
    },
    unmounted() {
        this.eventSource?.close()
        this.snackbar.show = false;
        this.snackbar.color = ""; // Adjust the snackbar color as needed
        this.snackbar.message = "";
        this.snackbar.timeout = 4000; // Adjust the timeout duration as needed
    },
});
</script>
