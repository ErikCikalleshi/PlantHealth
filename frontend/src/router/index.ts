import test from '../views/test.vue'
import login from '../views/Login.vue'
import header from '../components/header.vue'
import footer from '../components/footer.vue'
import gallery from "../views/gallery_test.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
const routes = [
    { path: '/', component: test},
    { path: '/login', component: login},
    { path: '/header', component: header},
    { path: '/footer', component: footer},
    {path: '/gallery', component: gallery}
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

