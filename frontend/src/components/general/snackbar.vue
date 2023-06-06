<template>
    <div>
        <!-- Your component template -->

        <!-- Snackbar component -->
        <v-snackbar v-model="showSnackbar" :color="snackbarColor">
            {{ snackbarMessage }}
        </v-snackbar>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import * as Stomp from 'stompjs';
//import * as SockJS from 'sockjs-client';


export default defineComponent({
    name: "snackbar",
    data() {
        return {
            showSnackbar: false,
            snackbarMessage: '',
            snackbarColor: ''
        };
    },
    created() {
        // const  eventSource = new EventSource('http://localhost:8080/api/snackbar');
        //
        // eventSource.addEventListener('myEvent', (event) => {
        //     console.log(event.data);
        //     this.snackbarMessage = event.data;
        //     this.showSnackbar = true;
        //     this.snackbarColor = 'success';
        // });
        // client.connect({}, function() {
        //     console.log("Connected to WebSocket server.");
        //     client.subscribe("/topic/snackbar", (message: string) => {
        //         console.log("Message: " + message);
        //         // this.snackbarMessage = message;
        //         // this.showSnackbar = true;
        //         // this.snackbarColor = 'success';
        //     });
        // });
    }
});
</script>
