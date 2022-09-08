package br.com.ramondev.hotelservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HotelGuestNotFoundException extends RuntimeException {
  public HotelGuestNotFoundException(String message){
    super(message);
  }
}
