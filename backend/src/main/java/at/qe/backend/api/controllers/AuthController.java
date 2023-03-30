package at.qe.backend.api.controllers;

import at.qe.backend.api.services.AuthService;
import at.qe.backend.models.Userx;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* Controller to handle login requests
**/
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AuthController {
    @Autowired
    private AuthService authService;
    /**
     * records are DTOs (Data Transfer Object) used to return only the fields we want back to the client
     * used for example to not send the password when a user logs in
    * */

    record LoginRequest(String username, String password){}
    record LoginResponse(String accessToken, String refreshToken){}

    /**
     * Handle requests at '/login' and after the validations were done and the tokens were created,
     * the refresh token is being set as a Cookie
     * it returns the access token containing the username (also our UUID)
    * */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var loginService = authService.login(loginRequest.username(), loginRequest.password());
//        var cookie = new Cookie("refreshToken", loginService.getRefreshToken().getToken());
//        cookie.setMaxAge(3600);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
        return new LoginResponse(loginService.getAccessToken().getToken(), loginService.getRefreshToken().getToken());
    }

    record UserResponse(String username, String firstName, String lastName, String email){}
    /**
     * Endpoint '/user' getting all user related data from attribute 'user' that was set in the interceptor
     * */
    @GetMapping("/user")
    public UserResponse userInfo(HttpServletRequest request) {
        var user = (Userx) request.getAttribute("user");
        return new UserResponse(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    record RefreshResponse(String token) {}
    record RefreshRequest(String token) {}
    /**
     * Endpoint '/refresh' that will take the JWT refresh token from your Cookies and validate it
     * If it's a valid token signed with our secret key, it will respond with a new access token for the user
     * */
    @PostMapping("/refresh")
    public RefreshResponse refreshToken(@RequestBody RefreshRequest refreshToken) {
        System.out.println("REFRESH TOKEN: " + refreshToken.token);
        return new RefreshResponse(authService.refreshAccessToken(refreshToken.token).getAccessToken().getToken());
    }

    record LogoutResponse(String response) {}
    /**
     * Endpoint '/logout-user' that will remove the refresh token (JWT token) saved in Cookies
     * Not using '/logout' as endpoint, since there is already something from Spring Security that has this endpoint
     * TODO: figure out what Spring Security does with '/logout' endpoint
     * */
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
