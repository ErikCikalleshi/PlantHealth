import type IUser from "@/interfaces/user/IUser";
import api from "@/services/api";
import {API_BASE_URL} from "@/services";

class AdminUserService {
    async getAllUsers(token: string) {
        return await api.get('admin/get-all-users', {
        });
    }

    async deleteUser(token: string, username: string) {
        return await api.delete('admin/delete-user/' + username, {
        });
    }

    async getSingleUser(token: string, username: string) {
        return await api.get('admin/get-single-user/' + username, {
        });
    }

    async updateUser(token: string, user: IUser) {
        return await api.patch('admin/update-user/', JSON.parse(JSON.stringify(user)), {
        });
    }
}
export default new AdminUserService();
