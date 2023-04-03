import AuthService from '../services/auth.service';
import type {ActionContext} from "vuex";
import type { Commit } from 'vuex';


interface User {
    accessToken: string;
}

interface AuthState {
    status: {
        loggedIn: boolean;
    };
    user: User | null;
}

const user = JSON.parse(localStorage.getItem('user') || 'null');

const initialState: AuthState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };


export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({commit}:{commit:Commit}, username:string, password:string) {
            return AuthService.login(username, password).then(
                user => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
                },
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({commit}:{commit:Commit}) {
            AuthService.logout();
            commit('logout');
        },
        refreshToken({commit}:{commit:Commit}, accessToken:string) {
            commit('refreshToken', accessToken);
        }
    },
    mutations: {
        loginSuccess(state: AuthState, user:User) {
            state.status.loggedIn = true;
            state.user = user;
        },
        loginFailure(state: AuthState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state: AuthState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        refreshToken(state:AuthState, accessToken:string) {
            state.status.loggedIn = true;
            state.user = { ...state.user, accessToken };
        }
    }
};