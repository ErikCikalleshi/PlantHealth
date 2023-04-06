package at.qe.backend.api.model.response;

import at.qe.backend.models.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String createDate;
    private String email;
    private Set<UserRole> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String firstname, String lastname, String createDate, String email, Set<UserRole> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createDate = createDate;
        this.email = email;
        this.roles = roles;
    }

}