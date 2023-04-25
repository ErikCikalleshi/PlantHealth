package at.qe.backend.exceptions.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoBearerTokenError extends ResponseStatusException {
    public NoBearerTokenError() {
        super(HttpStatus.FORBIDDEN, "No access token found.");
    }
}
