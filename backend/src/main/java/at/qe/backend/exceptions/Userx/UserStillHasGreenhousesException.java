package at.qe.backend.exceptions.Userx;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User is still responsible for at least one greenhouse.")
public class UserStillHasGreenhousesException extends ResponseStatusException {

    public UserStillHasGreenhousesException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
