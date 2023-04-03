import {API_BASE_URL} from "@/services";
import {useStore as userStore} from "@/stores/user/user";
import {useStore as tokenStore} from "@/stores/token/token";
import axios from "axios";
import type IUser from "@/interfaces/user/IUser";

export async function login(username: string, password: string) {
    return await axios.post(API_BASE_URL + 'login', {
        username,
        password
    })
}

export async function setUser(token: string) {
    return await axios.get(API_BASE_URL + 'user', {
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