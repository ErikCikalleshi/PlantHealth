import { createApp, watch } from 'vue'
import { createPinia } from 'pinia'
import { router } from "@/router";
import App from './App.vue'
import VueCookies from "vue-cookies";
import setupInterceptors from './services/setupInterceptors';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';
import './assets/main.css'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

//OhVueIcons
import { OhVueIcon, addIcons } from "oh-vue-icons";
import { FaUserCircle, HiSolidUser, HiMenuAlt3, PrTimes, FaArrowRight, IoCloseSharp} from "oh-vue-icons/icons";
import VueApexCharts from "vue3-apexcharts";



addIcons(FaUserCircle, HiSolidUser, HiMenuAlt3, PrTimes, FaArrowRight, IoCloseSharp);

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

setupInterceptors();



const app = createApp(App);
app.component("oh-icon", OhVueIcon);
app.component('EasyDataTable', Vue3EasyDataTable);

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(pinia)
app.use(VueCookies)
app.use(router)
app.use(vuetify).mount('#app')
app.use(VueApexCharts);
