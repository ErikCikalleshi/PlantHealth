class TokenService {
    getLocalRefreshToken(): string | undefined {
        const user = JSON.parse(localStorage.getItem("user") || "");
        return user?.refreshToken;
    }

    getLocalAccessToken(): string | undefined {
        const user = JSON.parse(localStorage.getItem("user") || "");
        return user?.accessToken;
    }

    updateLocalAccessToken(token: string): void {
        let user = JSON.parse(localStorage.getItem("user") || "");
        user.accessToken = token;
        localStorage.setItem("user", JSON.stringify(user));
    }

    getUser(): any {
        return JSON.parse(localStorage.getItem("user") || "");
    }

    setUser(user: any): void {
        console.log(JSON.stringify(user));
        localStorage.setItem("user", JSON.stringify(user));
    }

    removeUser(): void {
        localStorage.removeItem("user");
    }
}

export default new TokenService();
