package at.qe.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The greenhouse does not have a sensor of the given type")
public class SensorNotFoundException extends Exception {
}
