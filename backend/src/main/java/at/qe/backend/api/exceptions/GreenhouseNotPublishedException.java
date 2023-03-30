package at.qe.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The greenhouse is not published")
public class GreenhouseNotPublishedException extends Exception {
}
