import {API_BASE_URL} from "@/services";
import axios from "axios";
import type IUser from "@/interfaces/user/IUser";

class AdminUserService {
    async getAllUsers(token: string) {
        return await axios.get(API_BASE_URL + 'admin/get-all-users', {
            headers: {
                Authorization: 'Bearer ' + token
            }
        });
    }

    async deleteUser(token: string, username: string) {
        return await axios.delete(API_BASE_URL + 'admin/delete-user/' + username, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        });
    }

    async getSingleUser(token: string, username: string) {
        return await axios.get(API_BASE_URL + 'admin/get-single-user/' + username, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        });
    }

    async updateUser(token: string, user: IUser) {
        return await axios.patch(API_BASE_URL + 'admin/update-user/', JSON.parse(JSON.stringify(user)), {
            headers: {
                Authorization: 'Bearer ' + token
            }
        });
    }
}

export default new AdminUserService();
