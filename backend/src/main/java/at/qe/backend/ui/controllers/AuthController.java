package at.qe.backend.ui.controllers;

import at.qe.backend.configs.security.jwt.JwtUtils;
import at.qe.backend.configs.security.jwt.exception.TokenRefreshException;
import at.qe.backend.configs.security.jwt.models.RefreshToken;
import at.qe.backend.configs.security.jwt.services.RefreshTokenService;
import at.qe.backend.configs.security.services.UserDetailsImpl;
import at.qe.backend.helper.JSONDateFormatHelper;
import at.qe.backend.models.Userx;
import at.qe.backend.models.request.LoginRequest;
import at.qe.backend.models.request.TokenRefreshRequest;
import at.qe.backend.models.response.JwtResponse;
import at.qe.backend.models.response.TokenRefreshResponse;
import at.qe.backend.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to handle user authorization requests
 **/
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserxService userxService;

    /**
     * Handle requests at '/login' and after the validations were done and the tokens were created,
     * the refresh token is being set as a Cookie
     * it returns the access token, refresh token and the user's data
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.id());
        Userx user = userxService.loadUser(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), JSONDateFormatHelper.format(user.getCreateDate()), user.getEmail(), user.getRoles()));
    }


    /**
     * Endpoint '/refreshtoken' that will refresh the access token and return it to the user
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser).map(user -> {
            String token = jwtUtils.generateTokenFromUsername(user.getUsername());
            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    record LogoutResponse(String response) {
    }

    /**
     * Endpoint '/logout-user' that will remove the refresh token (JWT token) saved in Cookies and database
     */
    @PostMapping("/logout-user")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        refreshTokenService.findByToken(requestRefreshToken).ifPresent(refreshTokenService::deleteRefreshToken);
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new LogoutResponse("Logout successful!"));
    }
}
