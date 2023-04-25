package at.qe.backend.exceptions.Userx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The user already exists")
public class UserAlreadyExistsException extends Exception {
}
