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


// DELETE THIS AFTER SOME TESTING OF NEW AUTH
// export async function refreshUser(token: string) {
//     return await axios.post(API_BASE_URL + 'refresh', {token}, {
//         withCredentials: true,
//     }).then((response) => {
//         let store = tokenStore();
//         store.accessToken = response.data.token;
//     });
// }