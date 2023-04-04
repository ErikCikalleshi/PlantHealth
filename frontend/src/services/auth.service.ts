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
            });
    }
}

export default new AuthService();
