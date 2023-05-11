package at.qe.backend.exceptions.Userx;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "User is still responsible for at least one greenhouse.")
public class UserStillHasGreenhousesException extends ResponseStatusException {
    public UserStillHasGreenhousesException(HttpStatusCode status) {
        super(status);
    }

    public UserStillHasGreenhousesException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public UserStillHasGreenhousesException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public UserStillHasGreenhousesException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    protected UserStillHasGreenhousesException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
    }
}
