import {useStore as userStore} from "@/stores/user/user";
import type IUser from "@/interfaces/user/IUser";
import api from "@/services/api";

export async function setUser(token: string) {
    return await api.get('user', {
        headers: {
            Authorization: 'Bearer ' + token
        }
    }).then((response) => {
        let store = userStore();
        store.$state = response.data as IUser;
    });
}