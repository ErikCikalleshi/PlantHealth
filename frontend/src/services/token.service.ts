import { useStore as tokenStore } from "@/stores/token/token";

class TokenService {
    getLocalRefreshToken(): string {
        return tokenStore().refreshToken || this.getCookie("refreshToken") || "";
    }

    getLocalAccessToken(): string {
        return tokenStore().accessToken || this.getCookie("accessToken") || "";
    }

    updateLocalAccessToken(token: string): void {
        tokenStore().accessToken = token;
        this.setCookie("accessToken", token, 60);
    }

    updateLocalRefreshToken(token: string): void {
        tokenStore().refreshToken = token;
        this.setCookie("refreshToken", token, 3600);
    }


    setUser(user: any): void {
        this.updateLocalAccessToken(user.token);
        this.updateLocalRefreshToken(user.refreshToken);
    }

    removeUser(): void {
        //TODO delete cookies and logout
    }

    private setCookie(name: string, value: string, expireSeconds: number) {
        const d = new Date();
        d.setTime(d.getTime() + expireSeconds * 1000);
        const expires = `expires=${d.toUTCString()}`;
        document.cookie = `${name}=${value};${expires};path=/`;
    }

    private getCookie(name: string): string {
        const cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i];
            if (cookie.startsWith(name + "=")) {
                return cookie.substring(name.length + 1);
            }
        }
        return "";
    }
}

export default new TokenService();