package br.com.ramondev.hotelservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.dto.ApartamentoDTO;
import br.com.ramondev.hotelservice.model.exception.ApartmentNotFoundException;
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
    Apartamento apartamento = apartamentoService.buscarApartamento(numeroApartamento);
    
    if (apartamento == null) {
      throw new ApartmentNotFoundException("O apartamento nao foi encontrado.");
    }

    EntityModel<Apartamento> model = EntityModel.of(apartamento);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodosApartamentos());
    model.add(linkToUsers.withRel("todos-apartamentos"));

    return model;
  }

  @PostMapping("/apartamentos")
  public ResponseEntity<Object> cadastrarApartamento(@RequestBody ApartamentoDTO apartamento) {
    return apartamentoService.cadastrarApartamento(apartamento);
  }
}
