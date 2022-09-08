package br.com.ramondev.hotelservice.model.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ramondev.hotelservice.model.repository.FichaCadastroRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ConsumoControllerTest {
  @Mock
  private FichaCadastroRepository fichaCadastroRepository;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001";
  }

  @Before
  public void setupMockito() {
    MockitoAnnotations.initMocks(this);
    // fichaCadastroService = new FichaCadastroService(fichaCadastroRepository);
  }

  @Test
  public void deveCadastrarConsumocomSucesso(){
    RestAssured
        .given()
        .body("{\"consumo\": [{\"nomeProduto\": \"Agua\",\"preco\": 2.00,\"quantidade\": 3 },{ \"nomeProduto\": \"Refrigerante\",\"preco\": 8.00,\"quantidade\": 5 },{\"nomeProduto\": \"Fritas\",\"preco\": 24.00,\"quantidade\": 1 }],\"cpfHospede\": \"05548786741\"}")
        .contentType(ContentType.JSON)
        .when()
        .post("/consumo")
        .then()
        .statusCode(210);
  }
}