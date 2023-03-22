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
    * */
    record LoginRequest(String username, String password){}
    record LoginResponse(String token){}

    /**
     * Handle requests at '/login' and after the validations were done and the tokens were created,
     * the refresh token is being set as a Cookie
     * it returns the access token containing the username (also our UUID)
    * */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var loginService = authService.login(loginRequest.username(), loginRequest.password());
        var cookie = new Cookie("refreshToken", loginService.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/login");
        response.addCookie(cookie);
        return new LoginResponse(loginService.getAccessToken().getToken());
    }

    record UserResponse(String username, String firstName, String lastName, String email){}
    @GetMapping("/user")
    public UserResponse userInfo(HttpServletRequest request) {
        var user = (Userx) request.getAttribute("user");
        return new UserResponse(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
