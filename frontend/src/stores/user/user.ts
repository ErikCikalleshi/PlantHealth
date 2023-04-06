import {defineStore} from "pinia";
import type IUser from "@/interfaces/user/IUser";

const defaultState = {
    username: '',
    firstname: '',
    lastname: '',
    created:'',
    email: '',
    roles: [],
}

export const useStore = defineStore({
    id: 'user',
    state: (): IUser => ( {...defaultState}),
    actions: {
        reset() {
            Object.assign(this, defaultState)
        }
    },
    persist: true,
});