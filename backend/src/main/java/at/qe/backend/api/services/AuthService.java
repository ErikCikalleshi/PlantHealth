package at.qe.backend.api.services;

import at.qe.backend.api.exceptions.InvalidTokenError;
import at.qe.backend.models.Userx;
import at.qe.backend.repositories.UserxRepository;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Class to validate credentials upon login
 * */
@Service
public class AuthService {
    private final UserxRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final String accessSecret;
    private final String refreshSecret;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
    /**
     * initialized Logger to use in the Service
     */

    public AuthService(UserxRepository userRepo, PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-secret}")String accessSecret,
                       @Value("${application.security.refresh-secret}")String refreshSecret) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
    }
    /**
     * Method that gets a username and a password
     *  - searches for the user in the database
     *  - validates password
     *  - creates access & refresh JWT Tokens
     */

    /**
     * LOG writes a log message to the console
     * if the user is not found, or the password is incorrect
     * needs to be extended to write to file by adding properties
     */
    public LoginService login(String username, String password) {
        var user = userRepo.findFirstByUsername(username);
        if(user == null) {
            LOG.error("User does not exist.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials.");
        }


        if(!passwordEncoder.matches(password, user.getPassword())) {
            LOG.error("Password is incorrect.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials.");
        }
        else { LOG.info("User logged in successfully.");}
        return LoginService.of(user.getUsername(), accessSecret, refreshSecret);
    }

    /**
     * Method that returns the user from the database based on the token. The token contains the claim 'username'
     * @param token as a string
     * @return user as Userx
     * @throws InvalidTokenError if the token expired/wrong signing key...
     */
    public Userx getUserFromToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Base64.getEncoder().encode(accessSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            var username = claims.get("username", String.class);
            return userRepo.findFirstByUsername(username);
        } catch (Exception ex) {
            throw new InvalidTokenError();
        }
    }
    /**
     * Method will take the refreshToken and validate it, if it's valid, it will generate a new access token for the user
     * */
    public LoginService refreshAccessToken(String refreshToken) {
        var username = JwtToken.from(refreshToken, refreshSecret);
        return LoginService.of(username, accessSecret, JwtToken.of(refreshToken));
    }
}
