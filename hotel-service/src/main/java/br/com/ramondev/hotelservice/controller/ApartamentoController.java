package br.com.ramondev.hotelservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.dto.ApartamentoDTO;
import br.com.ramondev.hotelservice.model.service.ApartamentoService;

@RestController
public class ApartamentoController {

  @Autowired
  private ApartamentoService apartamentoService;

  @GetMapping("/apartamentos")
  public List<Apartamento> buscarTodosApartamentos(){
    return apartamentoService.buscarTodosApartamentos();
  }

  @GetMapping("/apartamentos/{numeroApartamento}")
  public EntityModel<Apartamento> buscarApartamento(@PathVariable int numeroApartamento){
    return apartamentoService.buscarApartamento(numeroApartamento);
  }

  @PostMapping("/apartamentos")
  public ResponseEntity<Object> cadastrarApartamento(@RequestBody ApartamentoDTO apartamento) {
    return apartamentoService.cadastrarApartamento(apartamento);
  }
}
