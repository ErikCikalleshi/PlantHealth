import {defineStore} from "pinia";
export const useStore = defineStore({
    id: 'user',
    state: (): IToken => ({
        accessToken: '',
        refreshToken: ''
    }),
})

export interface IToken {
    accessToken: string,
    refreshToken: string,
}