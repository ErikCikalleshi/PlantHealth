import type IUser from "@/interfaces/user/IUser";
import api from "@/services/api";

class AdminUserService {
    async getAllUsers() {
        return await api.get('admin/get-all-users', {
        });
    }

    async deleteUser(username: string) {
        return await api.delete('admin/delete-user/' + username, {
        });
    }

    async getSingleUser(username: string) {
        return await api.get('admin/get-single-user/' + username, {
        });
    }

    async updateUser(user: IUser) {
        return await api.patch('admin/update-user/', JSON.parse(JSON.stringify(user)), {
        });
    }
}
export default new AdminUserService();
