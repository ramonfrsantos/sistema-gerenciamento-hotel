package br.com.ramondev.hotelservice.model.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.repository.FichaCadastroRepository;
import br.com.ramondev.hotelservice.model.service.FichaCadastroService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class FichaCadastroControllerTest {

  private FichaCadastroService fichaCadastroService;

  @Mock
  private FichaCadastroRepository fichaCadastroRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.initMocks(this);
    fichaCadastroService = new FichaCadastroService(fichaCadastroRepository);
  }

  @Test
  public void deveFazerCheckInComSucesso() {
    if (fichaCadastroService.buscarFichaPorCpf("05548786741") != null) {
      RestAssured
          .given()
          .body("{\"cpfHospede\": \"05548786741\",\"dataEntrada\": \"2023-01-01T14:10:00.000Z\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/fichas-cadastro/check-in")
          .then()
          .statusCode(417);
    } else {
      RestAssured
          .given()
          .body("{\"cpfHospede\": \"05548786741\",\"dataEntrada\": \"2023-01-01T14:10:00.000Z\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/fichas-cadastro/check-in")
          .then()
          .statusCode(210);
    }
  }
}