package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterBadRequestException extends RuntimeException {
  public RegisterBadRequestException(String message){
    super(message);
  }
}
