package br.com.ramondev.hotelservice.model.controller;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckInDTO;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckOutDTO;
import br.com.ramondev.hotelservice.model.repository.FichaCadastroRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class FichaCadastroControllerTest {

  // private FichaCadastroService fichaCadastroService;

  @Mock
  private FichaCadastroRepository fichaCadastroRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.openMocks(this);
    // fichaCadastroService = new FichaCadastroService(fichaCadastroRepository);
  }

  @Test
  public void deveFazerCheckInComSucesso() {
    FichaCadastroCheckInDTO dto = new FichaCadastroCheckInDTO("05548786741",
        Date.from(Instant.parse("2023-01-01T14:10:00.000Z")));

    RestAssured
        .given()
        // .body("{\"cpfHospede\": \"05548786741\",\"dataEntrada\":
        // \"2023-01-01T14:10:00.000Z\"}")
        .body(dto)
        .contentType(ContentType.JSON)
        .when()
        .post("/fichas-cadastro/check-in")
        .then()
        .log().all();
  }

  @Test
  public void deveFazerCheckOutComSucesso() {
    FichaCadastroCheckOutDTO dto = new FichaCadastroCheckOutDTO("05548786741",
        Date.from(Instant.parse("2023-01-12T20:00:00.000Z")));

    RestAssured
        .given()
        // .body("{\"cpfHospede\": \"05548786741\",\"dataEntrada\":
        // \"2023-01-01T14:10:00.000Z\"}")
        .body(dto)
        .contentType(ContentType.JSON)
        .when()
        .put("/fichas-cadastro/check-out")
        .then()
        .statusCode(200);
  }
}