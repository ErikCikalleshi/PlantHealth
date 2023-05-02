import login from '../views/Login.vue'
import landingpage from "@/views/Landingpage.vue";
import gallery from "../views/gallery_test.vue"
import adminManageUsers from '../views/admin/AdminViewUsers.vue'
import adminManageAccessPoints from '../views/admin/AdminViewAccessPoints.vue'
import adminEditUser from "@/views/admin/AdminEditUser.vue";
import NotFound from "@/views/NotFound.vue";
import Forbidden from "@/views/Forbidden.vue";
import {createRouter, createWebHistory} from 'vue-router'
import {useStore as userStore} from "@/stores/user/user";
import dashboard from "@/views/Dashboard.vue";
import PlantsView from "@/views/PlantsView.vue";
import authService from "@/services/auth.service";
import TokenService from "@/services/token.service";
import adminEditAccesspoint from "@/views/admin/AdminEditAccesspoint.vue";
import adminAuditLog from "@/views/admin/AdminViewAuditLog.vue";

const routes = [
    {path: '/', component: landingpage},
    {path: '/dashboard', component: dashboard, meta: { roles: ['ADMIN', 'GARDENER', 'USER']}},
    {path: '/login', name: 'login', component: login},
    {path: '/gallery/:id', component: gallery, meta: { roles: ['ADMIN', 'GARDENER', 'USER']}},
    {path: '/admin/users', name: 'manage-users', component: adminManageUsers, meta: { roles: ['ADMIN'] }},
    {path: '/admin/users/edit/:username', props: true, name: 'admin-edit-user', component: adminEditUser, meta: { roles: ['ADMIN'] }},
    {path: '/dashboard/plants', component: PlantsView},
    {path: '/admin/access-points', name: 'manage-accessPoints', component: adminManageAccessPoints, meta: { roles: ['ADMIN'] }},
    {path: '/admin/access-points/edit/:apId', props: true, name: 'admin-edit-access-point', component: adminEditAccesspoint, meta: { roles: ['ADMIN'] }},
    {path: '/admin/audit-log', name: 'view-audit-logs', component: adminAuditLog, meta: { roles: ['ADMIN'] }},
    {path: '/404', component: NotFound},
    {path: '/403', component: Forbidden},
    {path: '/:pathMatch(.*)*', name: 'not-found', component: NotFound },
    {path: '/:pathMatch(.*)', name: 'bad-not-found', component: NotFound },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})


router.beforeEach(async(to, from, next) => {
    // check if the route requires authentication

    const accessToken = TokenService.getLocalAccessToken()
    const refreshToken = TokenService.getLocalRefreshToken()
    if (!accessToken && refreshToken) {
        await authService.refreshAccessToken();
    }

    if (to.matched.some(record => record.meta.roles)) {
        // check if the user is authenticated
        const user = userStore();
        const isAuthenticated = user.username !== '' && TokenService.getLocalAccessToken();
        if (!isAuthenticated) {
            console.log(to.fullPath)
            next({ name: 'login', query: { redirect: to.fullPath } });
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
