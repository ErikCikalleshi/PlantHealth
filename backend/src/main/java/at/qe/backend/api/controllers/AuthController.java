package at.qe.backend.api.controllers;

import at.qe.backend.api.model.request.LoginRequest;
import at.qe.backend.api.model.DTO.UserDTO;
import at.qe.backend.api.model.response.JwtResponse;
import at.qe.backend.configs.security.jwt.models.RefreshToken;
import at.qe.backend.configs.security.jwt.services.AuthService;
import at.qe.backend.configs.security.jwt.JwtUtils;
import at.qe.backend.configs.security.jwt.services.RefreshTokenService;
import at.qe.backend.configs.security.services.UserDetailsImpl;
import at.qe.backend.models.Userx;
import at.qe.backend.services.UserxService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to handle login requests
 **/
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserxService userxService;

    /**
     * records are DTOs (Data Transfer Object) used to return only the fields we want back to the client
     * used for example to not send the password when a user logs in
     */
    record LoginResponse(String accessToken, String refreshToken) {
    }

    /**
     * Handle requests at '/login' and after the validations were done and the tokens were created,
     * the refresh token is being set as a Cookie
     * it returns the access token containing the username (also our UUID)
     */

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));


    }

    record UserResponse(String username, String firstName, String lastName, String email) {
    }

    /**
     * Endpoint '/user' getting all user related data from attribute 'user' that was set in the interceptor
     */
    @GetMapping("/user")
    public UserDTO userInfo(HttpServletRequest request) {
        var user = userxService.loadUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new UserDTO(user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(user.getCreateDate()),
                user.getRoles(),
                user.getEmail());
    }

    record RefreshResponse(String token) {
    }

    record RefreshRequest(String token) {
    }

    /**
     * Endpoint '/refresh' that will take the JWT refresh token from your Cookies and validate it
     * If it's a valid token signed with our secret key, it will respond with a new access token for the user
     */
    @PostMapping("/refresh")
    public RefreshResponse refreshToken(@RequestBody RefreshRequest refreshToken) {
        System.out.println("REFRESH TOKEN: " + refreshToken.token);
        return new RefreshResponse(authService.refreshAccessToken(refreshToken.token).getAccessToken().getToken());
    }

    record LogoutResponse(String response) {
    }

    /**
     * Endpoint '/logout-user' that will remove the refresh token (JWT token) saved in Cookies
     * Not using '/logout' as endpoint, since there is already something from Spring Security that has this endpoint
     * TODO: figure out what Spring Security does with '/logout' endpoint
     */
    @PostMapping("/logout-user")
    public LogoutResponse logout(HttpServletResponse response) {
        var cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new LogoutResponse("You've been logged out successfully.");
    }
}
