package at.qe.backend.api.model.request;

public class TokenRefreshRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        if (refreshToken.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.refreshToken = refreshToken;
    }
}