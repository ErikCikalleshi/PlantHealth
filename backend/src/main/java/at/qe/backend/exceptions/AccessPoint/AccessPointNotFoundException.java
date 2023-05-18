package at.qe.backend.exceptions.AccessPoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The access-point does not exist")
public class AccessPointNotFoundException extends Exception {
}
