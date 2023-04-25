package at.qe.backend.exceptions.Greenhouse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The greenhouse with the given ID does not exists")
public class GreenhouseNotRegisteredException extends Exception{
}
