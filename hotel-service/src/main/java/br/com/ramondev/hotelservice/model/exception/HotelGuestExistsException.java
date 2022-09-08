package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "HotelGuestExistsException")
public class HotelGuestExistsException extends RuntimeException {
  public HotelGuestExistsException(String message){
    super(message);
  }
}
