package at.qe.backend.models.response;

import at.qe.backend.models.UserRole;
import java.util.Set;

public record JwtResponse (String token, String type, String refreshToken, Long id, String username, String firstname, String lastname, String createDate, String email, Set<UserRole> roles){
    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String firstname, String lastname, String createDate, String email, Set<UserRole> roles) {
        this(accessToken, "Bearer", refreshToken, id, username, firstname, lastname, createDate, email, roles);
    }
}