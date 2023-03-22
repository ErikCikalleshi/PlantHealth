import test from '../views/test.vue'
import login from '../views/Login.vue'
import header from '../views/header.vue'
import {createRouter, createWebHashHistory} from 'vue-router'
const routes = [
    { path: '/', component: test},
    { path: '/login', component: login},
    { path: '/header', component: header}
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

