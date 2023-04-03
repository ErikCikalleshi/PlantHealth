import { useStore as tokenStore } from "@/stores/token/token";

class TokenService {
    getLocalRefreshToken(): string {
        return tokenStore().refreshToken || localStorage.getItem("refreshToken") || "";
    }

    getLocalAccessToken(): string {
        return tokenStore().accessToken || localStorage.getItem("accessToken") || "";
    }

    updateLocalAccessToken(token: string): void {
        tokenStore().accessToken = token;
        localStorage.setItem("accessToken", token);
    }

    updateLocalRefreshToken(token: string): void {
        tokenStore().refreshToken = token;
        localStorage.setItem("refreshToken", token);
    }

    getUser(): any {
        const userStr = localStorage.getItem("user");
        return userStr ? JSON.parse(userStr) : null;
    }

    setUser(user: any): void {
        this.updateLocalAccessToken(user.token);
        this.updateLocalRefreshToken(user.refreshToken);
        localStorage.setItem("user", JSON.stringify(user));
    }

    removeUser(): void {
        localStorage.removeItem("user");
    }
}

export default new TokenService();