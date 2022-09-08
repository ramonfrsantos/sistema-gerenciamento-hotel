package br.com.ramondev.hotelservice.model.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.repository.HospedeRepository;
import br.com.ramondev.hotelservice.model.service.HospedeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HospedeControllerTest {

  private HospedeService hospedeService;

  @Mock
  private HospedeRepository hospedeRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.initMocks(this);
    hospedeService = new HospedeService(hospedeRepository);
  }

  @Test
  public void deveCadastrarUmHospedeComSucesso() {
    if (hospedeService.buscarHospedePorCpf("05548786741") != null) {
      RestAssured
          .given()
          .body(
              "{\"emailHospede\": \"teste@email.com\", \"nomeHospede\": \"Tester\", \"cpfHospede\": \"055.487.867-41\", \"rgHospede\": \"46.282.232-1\", \"dataNascimentoHospede\": \"1999-03-05T10:00:00.000Z\"}")
          .contentType(ContentType.JSON)
          .log().body()
          .when()
          .post("/hospedes")
          .then()
          .statusCode(417);
    } else {
      RestAssured
          .given()
          .body(
              "{\"emailHospede\": \"teste@email.com\", \"nomeHospede\": \"Tester\", \"cpfHospede\": \"055.487.867-41\", \"rgHospede\": \"46.282.232-1\", \"dataNascimentoHospede\": \"1999-03-05T10:00:00.000Z\"}")
          .contentType(ContentType.JSON)
          .log().body()
          .when()
          .post("/hospedes")
          .then()
          .statusCode(201);
    }
  }
}