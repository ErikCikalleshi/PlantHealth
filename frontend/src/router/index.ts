import test from '../views/test.vue'
import login from '../views/Login.vue'
import header from '../views/header.vue'
import footer from '../views/footer.vue'
import {createRouter, createWebHashHistory} from 'vue-router'
const routes = [
    { path: '/', component: test},
    { path: '/login', component: login},
    { path: '/header', component: header},
    { path: '/footer', component: footer}
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

