import {defineStore} from "pinia";

const defaultState = {
    accessToken: '',
    refreshToken: ''
}
export const useStore = defineStore({
    // id has to be unique or logout won't work (reset function not callled in here)
    id: 'token',
    state: (): IToken => ({...defaultState}),
    actions: {
        reset() {
            Object.assign(this, defaultState);
        }
    }
});

export interface IToken {
    accessToken: string,
    refreshToken: string,
}