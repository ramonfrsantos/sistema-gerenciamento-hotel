package br.com.ramondev.hotelservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.dto.ConsumoDTO;
import br.com.ramondev.hotelservice.model.dto.ProdutoDTO;
import br.com.ramondev.hotelservice.model.service.ConsumoService;

@RestController
public class ConsumoController {

  @Autowired
  private ConsumoService consumoService;
 
  @GetMapping("consumo/{cpfHospede}")
  public List<ProdutoDTO> consultarConsumo(@PathVariable String cpfHospede) {
    return consumoService.consultarConsumo(cpfHospede);
  }

  @PostMapping("/consumo")
  public ResponseEntity<Object> cadastrarConsumo(@RequestBody ConsumoDTO consumoDTO){
    return consumoService.cadastrarConsumo(consumoDTO);
  }
}
