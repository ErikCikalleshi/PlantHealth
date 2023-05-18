import api from "./api";
import TokenService from "./token.service";

class AuthService {
    login(username: string, password: string) {
        return api
            .post("login", {
                username,
                password
            })
            .then((response) => {
                if (response.data.token) {
                    TokenService.setUser(response.data);
                }
                return response;
            }).catch((reason) => {
                return reason;
            });
    }

    logout() {
        let refreshToken = TokenService.getLocalRefreshToken();
        return api
            .post("logout-user", {refreshToken})
            .then((response) => {
                if (response.status === 200) {
                    TokenService.removeUser();
                }
                return response
            }).catch((response) => {
                TokenService.removeUser();
                return response;
            })
    };


    async refreshAccessToken() {
        let refreshToken = TokenService.getLocalRefreshToken();
        return await
            // set validate status to null to avoid recursive call by interceptor
            api.request({
                url: "refreshtoken",
                method: "POST",
                data: {refreshToken},
                validateStatus: null
            })
                .then((response) => {
                    if (response.status === 200) {
                        let {accessToken, refreshToken: newRefreshToken} = response.data;
                        TokenService.updateLocalAccessToken(accessToken);
                        TokenService.updateLocalRefreshToken(newRefreshToken);
                    } else {
                        TokenService.removeUser();
                    }

                    return response.data.accessToken;
                }).catch(() => {
                TokenService.removeUser();
                return "";
            });
    }
}

export default new AuthService();
