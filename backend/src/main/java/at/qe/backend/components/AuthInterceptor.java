package at.qe.backend.components;

import at.qe.backend.api.exceptions.NoBearerTokenError;
import at.qe.backend.configs.security.jwt.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * Class with a custom interceptor that handles requests sent to '/user' endpoint
 * This class will get called before it reaches the endpoint
 * */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Method will try to find the access token and if it's valid, the user info will be set on the attribute 'user'
     * otherwise it will return 'HttpStatus.FORBIDDEN'
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NoBearerTokenError();
        }
        request.setAttribute("user", authService.getUserFromToken(authorizationHeader.substring(7)));
        return true;
    }
}
