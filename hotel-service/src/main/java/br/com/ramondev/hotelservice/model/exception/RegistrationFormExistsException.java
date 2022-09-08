package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "RegistrationFormExistsException")
public class RegistrationFormExistsException extends RuntimeException {
  public RegistrationFormExistsException(String message){
    super(message);
  }
}
