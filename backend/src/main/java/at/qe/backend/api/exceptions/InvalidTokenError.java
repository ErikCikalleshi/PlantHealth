package at.qe.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenError extends ResponseStatusException {
    public InvalidTokenError() {
        super(HttpStatus.BAD_REQUEST, "Invalid token.");
    }
}
