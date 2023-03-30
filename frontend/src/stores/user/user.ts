import {defineStore} from "pinia";
import type IUser from "@/interfaces/user/IUser";

export const useStore = defineStore({
    id: 'user',
    state: (): IUser => ({
        username: '',
        firstname: '',
        lastname: '',
        created:'',
        email: '',
        roles: [],
    }),

})