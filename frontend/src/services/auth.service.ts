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
                if (response.data.accessToken) {
                    TokenService.setUser(response.data);
                }
                return response;
            });
    }

    logout() {
        TokenService.removeUser();
    }
}

export default new AuthService();
