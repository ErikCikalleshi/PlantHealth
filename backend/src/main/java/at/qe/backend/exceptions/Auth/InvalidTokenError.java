package at.qe.backend.exceptions.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenError extends ResponseStatusException {
    public InvalidTokenError() {
        super(HttpStatus.BAD_REQUEST, "Invalid token.");
    }
}
