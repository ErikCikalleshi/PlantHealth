package at.qe.backend.models.request;

public class TokenRefreshRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        if (refreshToken==null|| refreshToken.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.refreshToken = refreshToken;
    }
}