import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { router } from "@/router";
import App from './App.vue'
import VueCookies from "vue-cookies";

import './assets/main.css'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

//OhVueIcons
import { OhVueIcon, addIcons } from "oh-vue-icons";
import { FaUserCircle, HiSolidUser } from "oh-vue-icons/icons";


addIcons(FaUserCircle, HiSolidUser);

const themeConfigs = {
    dark: false,
    colors: {
        primary: '#449A8B'
    }
}
const vuetify = createVuetify({
    components,
    directives,
    theme: {
        defaultTheme: 'themeConfigs',
        themes: {
            themeConfigs,
        }
    }
})
const app = createApp(App);
app.component("oh-icon", OhVueIcon);

app.use(createPinia())
app.use(VueCookies)
app.use(router)
app.use(vuetify).mount('#app')
