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
import br.com.ramondev.hotelservice.model.exception.HospedeNotFoundException;
import br.com.ramondev.hotelservice.model.exception.RegisterBadRequestException;

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
  public final ResponseEntity<Object> handleBadRequestException(RegisterBadRequestException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Falha no cadastro.",
        detailsMessage);

    // o servidor entende o que foi enviado, mas não pode concluir a operação
    return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(422));
  }

  @ExceptionHandler(ApartmentNotFoundException.class)
  public final ResponseEntity<Object> handleApartmentNotFoundException(ApartmentNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    System.out.println(detailsMessage);

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HospedeNotFoundException.class)
  public final ResponseEntity<Object> handleHospedeNotFoundException(HospedeNotFoundException e,
      WebRequest request) {

    String detailsMessage = e
        .getMessage();

    System.out.println(detailsMessage);

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Erro na requisicao.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    // expressão para personalizar o que vai ser mostrado no corpo da response
    // o desejado é apenas a mensagem definida no 'details'

    String detailsMessage = e
        .getBindingResult()
        .getAllErrors()
        .toString()
        .replace("]", "").replace(" [","")
        .split("default message")[2];

    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "A validação falhou.",
        detailsMessage);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

}