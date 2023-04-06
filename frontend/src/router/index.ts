import test from '../views/test.vue'
import login from '../views/Login.vue'
import header from '../components/header.vue'
import footer from '../components/footer.vue'
import gallery from "../views/gallery_test.vue"
import adminManageUsers from '../views/admin/AdminViewUsers.vue'
import adminEditUser from "@/views/admin/AdminEditUser.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
const routes = [
    { path: '/', component: test},
    { path: '/login', component: login},
    { path: '/header', component: header},
    { path: '/footer', component: footer},
    {path: '/gallery', component: gallery},
    { path: '/header', component: header},
    { path: '/admin/users', name: 'manage-users' ,component: adminManageUsers},
    { path: '/admin/users/edit/:username',props:true, name: 'admin-edit-user', component: adminEditUser},
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

