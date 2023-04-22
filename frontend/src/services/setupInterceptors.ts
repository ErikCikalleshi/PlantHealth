import axiosInstance from "./api";
import TokenService from "./token.service";
import axios from "axios";
import api from "./api";
import authService from "@/services/auth.service";


//Modify our axios instance to always send the correct auth token
const setup = () => {
    axiosInstance.interceptors.request.use(
        (config) => {
            let token = TokenService.getLocalAccessToken();
            if (token) {
                config.headers["Authorization"] = 'Bearer ' + token;  // for Spring Boot back-end
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (res) => {
            return res;
        },
        async (error) => {
            let originalRequest = error.config;

            if (error.response?.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;
                try {
                    await authService.refreshAccessToken();
                    originalRequest.headers.Authorization = "Bearer "+TokenService.getLocalAccessToken();
                    return axiosInstance(originalRequest);
                } catch (error) {
                    TokenService.removeUser();
                    // TODO handle refresh token error
                }
            }
            return Promise.reject(error);
        }
    );
};

export default setup;
