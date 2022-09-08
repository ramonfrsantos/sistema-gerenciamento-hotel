package br.com.ramondev.hotelservice.model.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.repository.ReservaRepository;
import br.com.ramondev.hotelservice.model.service.ReservaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReservaControllerTest {

  private ReservaService reservaService;

  @Mock
  private ReservaRepository reservaRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.initMocks(this);
    reservaService = new ReservaService(reservaRepository);
  }

  @Test
  public void deveCadastrarUmaReservaComSucesso() {
    if (reservaService.buscarReservaPorCpf("05548786741") != null) {
      RestAssured
          .given()
          .body(
              "{  \"dataChegadaPrevista\": \"2023-01-01T12:30:00.000Z\",  \"dataSaidaPrevista\": \"2023-01-15T21:30:00.000Z\",  \"numeroOcupantes\": 2,  \"tipoApartamento\": \"PADRAO\",  \"incluirVagaGaragem\": true,  \"cpfHospede\": \"05548786741\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/reservas")
          .then()
          .statusCode(417);
    } else {
      RestAssured
          .given()
          .body(
              "{  \"dataChegadaPrevista\": \"2023-01-01T12:30:00.000Z\",  \"dataSaidaPrevista\": \"2023-01-15T21:30:00.000Z\",  \"numeroOcupantes\": 2,  \"tipoApartamento\": \"PADRAO\",  \"incluirVagaGaragem\": true,  \"cpfHospede\": \"05548786741\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/reservas")
          .then()
          .statusCode(201);
    }
  }
}