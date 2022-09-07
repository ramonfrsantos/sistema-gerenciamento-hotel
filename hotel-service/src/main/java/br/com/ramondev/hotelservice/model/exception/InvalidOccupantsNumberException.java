package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "InvalidOccupantsNumberException")
public class InvalidOccupantsNumberException extends RuntimeException {
  public InvalidOccupantsNumberException(String message){
    super(message);
  }
}
