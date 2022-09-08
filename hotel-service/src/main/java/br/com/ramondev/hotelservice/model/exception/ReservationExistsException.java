package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "ReservationExistsException")
public class ReservationExistsException extends RuntimeException {
  public ReservationExistsException(String message){
    super(message);
  }
}
