package at.qe.backend.models.request;

public class LoginRequest {
    private String username;


    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }
}
