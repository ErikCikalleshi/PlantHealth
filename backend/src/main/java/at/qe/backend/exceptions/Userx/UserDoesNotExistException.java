package at.qe.backend.exceptions.Userx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "")
public class UserDoesNotExistException extends Exception {
}
