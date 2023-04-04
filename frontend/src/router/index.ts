import login from '../views/Login.vue'
import header from '../components/general/header.vue'
import footer from '../components/general/footer.vue'
import gallery from "../views/gallery_test.vue"
import adminManageUsers from '../views/admin/AdminViewUsers.vue'
import adminEditUser from "@/views/admin/AdminEditUser.vue";
import NotFound from "@/views/NotFound.vue";
import Forbidden from "@/views/Forbidden.vue";
import {createRouter, createWebHashHistory} from 'vue-router'
import {useStore as userStore} from "@/stores/user/user";
import dashboard from "@/views/Dashboard.vue";


const routes = [
    {path: '/', component: dashboard, meta: { roles: ['ADMIN', 'GARDENER', 'USER']}},
    {path: '/login', name: 'login', component: login},
    {path: '/gallery', component: gallery, meta: { roles: ['ADMIN', 'GARDENER', 'USER']}},
    {path: '/admin/users', name: 'manage-users', component: adminManageUsers, meta: { roles: ['ADMIN'] }},
    {path: '/admin/users/edit/:username', props: true, name: 'admin-edit-user', component: adminEditUser, meta: { roles: ['ADMIN'] }},
    {path: '/404', component: NotFound},
    {path: '/403', component: Forbidden},
    {path: '/:pathMatch(.*)*', name: 'not-found', component: NotFound },
    {path: '/:pathMatch(.*)', name: 'bad-not-found', component: NotFound },
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})


router.beforeEach((to, from, next) => {
    // check if the route requires authentication
    if (to.matched.some(record => record.meta.roles)) {
        // check if the user is authenticated
        const user = userStore();
        const isAuthenticated = user.username !== '';
        if (!isAuthenticated) {
            next({ path: '/login', query: { redirect: to.fullPath } });
        } else {
            // check if the user has the required roles to access the route
            const requiredRoles = (to.meta as { roles: string[] }).roles;
            const userRoles = user.roles;
            if (requiredRoles.some(role => userRoles.includes(role))) {
                next();
            } else {
                next({ path: '/403' });
            }
        }
    } else {
        next();
    }
});


