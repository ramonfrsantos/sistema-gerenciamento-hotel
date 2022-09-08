package br.com.ramondev.hotelservice.model.controller;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import br.com.ramondev.hotelservice.model.repository.ApartamentoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApartamentoControllerTest {

  @Mock
  private ApartamentoRepository apartamentoRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveRetornarApartamentos() {
    RestAssured
        .given()
        .when()
        .get("/apartamentos")
        .then()
        .statusCode(200);
  }

  @Test
  public void deveCadastrarApartamentosComSucesso() {
    List<Apartamento> apartamentosTipoPadrao = apartamentoRepository
        .findAllByTipoApartamento(TipoApartamentoEnum.PADRAO);

    if (apartamentosTipoPadrao.size() < 80) {
      RestAssured
          .given()
          .body("{\"tipoApartamento\": \"PADRAO\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/apartamentos")
          .then()
          .statusCode(201);
    } else {
      RestAssured
          .given()
          .body("{\"tipoApartamento\": \"PADRAO\"}")
          .contentType(ContentType.JSON)
          .when()
          .post("/apartamentos")
          .then()
          .statusCode(422);
    }
  }

  @Test
  public void naoDeveCadastrarApartamentosComTipoInvalido() {
    RestAssured
        .given()
        .body("{\"tipoApartamento\": \"DELUXE\"}")
        .contentType(ContentType.JSON)
        .when()
        .post("/apartamentos")
        .then()
        .statusCode(400);
  }
}