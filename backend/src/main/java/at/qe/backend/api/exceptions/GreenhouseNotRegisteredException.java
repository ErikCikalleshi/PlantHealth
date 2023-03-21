package at.qe.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "The greenhouse with the given ID does not exists")
public class GreenhouseNotRegisteredException extends Exception{
}
