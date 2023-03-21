package at.qe.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The sensor with the given type does not exists")
public class SensorNotFoundException extends Exception {
}
