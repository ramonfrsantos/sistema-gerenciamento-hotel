package br.com.ramondev.hotelservice.model.controller;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import br.com.ramondev.hotelservice.model.repository.ApartamentoRepository;
import br.com.ramondev.hotelservice.model.service.ApartamentoService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApartamentoControllerTest {
  @Mock
  private ApartamentoRepository apartamentoRepository;

  @InjectMocks
  private ApartamentoService apartamentoService;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.initMocks(this);
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
  public void deveCadastrarApartamentosPadraoComSucesso() {
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
  public void deveCadastrarApartamentosPresidencialComSucesso() {
    List<Apartamento> apartamentosTipoPresidencial = apartamentoRepository
        .findAllByTipoApartamento(TipoApartamentoEnum.PRESIDENCIAL);

    if (apartamentosTipoPresidencial.size() < 2) {
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