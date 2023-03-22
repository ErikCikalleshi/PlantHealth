package at.qe.backend.api.services;

import lombok.Getter;

/**
 * This class creates the access and the refresh JWT Token upon login
* */
@Getter
public class LoginService {
    private final JwtToken accessToken;
    private final JwtToken refreshToken;

    private LoginService(JwtToken accessToken, JwtToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginService of(String username, String accessSecret, String refreshSecret) {
        return new LoginService(
                JwtToken.of(username, 1L, accessSecret),
                JwtToken.of(username, 1440L, refreshSecret)
        );
    }
}
