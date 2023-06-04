package at.qe.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The measurement with the given ID does not exists")
public class MeasurentNotFoundException  extends Exception{}
