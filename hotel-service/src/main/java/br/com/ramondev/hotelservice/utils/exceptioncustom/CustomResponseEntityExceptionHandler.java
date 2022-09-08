package br.com.ramondev.hotelservice.utils.exceptioncustom;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.ramondev.hotelservice.model.exception.ApartmentNotFoundException;
import br.com.ramondev.hotelservice.model.exception.ApartmentsNotAvailableException;
import br.com.ramondev.hotelservice.model.exception.HotelGuestExistsException;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.exception.InvalidOccupantsNumberException;
import br.com.ramondev.hotelservice.model.exception.RegisterBadRequestException;
import br.com.ramondev.hotelservice.model.exception.RegistrationFormExistsException;
import br.com.ramondev.hotelservice.model.exception.RegistrationFormNotFoundException;
import br.com.ramondev.hotelservice.model.exception.ReservationExistsException;
import br.com.ramondev.hotelservice.model.exception.ReservationNotFoundException;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  // personalizar responses para as exceptions

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RegisterBadRequestException.class)
  public final ResponseEntity<Object> handleRegisterBadRequestException(RegisterBadRequestException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Falha no cadastro.",
        detailsMessage);

    // o servidor entende o que foi enviado, mas não pode concluir a operação
    return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(422));
  }

  @ExceptionHandler(ApartmentsNotAvailableException.class)
  public final ResponseEntity<Object> handleApartmentsNotAvailableException(ApartmentsNotAvailableException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Falha no cadastro da reserva.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(422));
  }

  @ExceptionHandler(ApartmentNotFoundException.class)
  public final ResponseEntity<Object> handleApartmentNotFoundException(ApartmentNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ReservationNotFoundException.class)
  public final ResponseEntity<Object> handleReservationNotFoundException(ReservationNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RegistrationFormNotFoundException.class)
  public final ResponseEntity<Object> handleRegistrationFormNotFoundException(RegistrationFormNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RegistrationFormExistsException.class)
  public final ResponseEntity<Object> handleRegistrationFormExistsException(RegistrationFormExistsException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro ao fazer o Check In.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
  }

  @ExceptionHandler(HotelGuestExistsException.class)
  public final ResponseEntity<Object> handleHotelGuestExistsException(HotelGuestExistsException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro ao cadastrar.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
  }

  @ExceptionHandler(ReservationExistsException.class)
  public final ResponseEntity<Object> handleReservationExistsException(ReservationExistsException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro ao cadastrar reserva.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
  }

  @ExceptionHandler(InvalidOccupantsNumberException.class)
  public final ResponseEntity<Object> handleInvalidOccupantsNumberException(InvalidOccupantsNumberException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro ao cadastrar reserva.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
  }

  @ExceptionHandler(HotelGuestNotFoundException.class)
  public final ResponseEntity<Object> handleHospedeNotFoundException(HotelGuestNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    String detailsMessage = e
        .getBindingResult()
        .getAllErrors()
        .toString()
        .replace("]", "")
        .replace(" [","")
        .split("default message")[2];

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "A validação falhou.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

}