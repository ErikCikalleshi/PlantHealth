package at.qe.backend.exceptions.Userx;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "Cannot delete last admin!")
public class LastAdminException extends ResponseStatusException {

    public LastAdminException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

}
