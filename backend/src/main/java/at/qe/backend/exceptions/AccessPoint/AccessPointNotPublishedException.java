package at.qe.backend.exceptions.AccessPoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The accessPoint is not published")
public class AccessPointNotPublishedException extends Exception {
}
