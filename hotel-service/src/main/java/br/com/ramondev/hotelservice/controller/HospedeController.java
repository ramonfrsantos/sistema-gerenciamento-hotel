package br.com.ramondev.hotelservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.dto.HospedeDTO;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.service.HospedeService;

@RestController
public class HospedeController {
  
  @Autowired
  private HospedeService hospedeService;

  @GetMapping("/hospedes")
  public List<Hospede> buscarTodosHospedes(){
    return hospedeService.buscarTodosHospedes();
  }

  @GetMapping("/hospedes/antigos")
  public List<Hospede> buscarExHospedes(){
    return hospedeService.buscarExHospedes();
  }

  @GetMapping("/hospedes/{id}")
  public EntityModel<Optional<Hospede>> buscarHospede(@PathVariable String id){
    Optional<Hospede> hospede = hospedeService.buscarHospede(id);

    if (!hospede.isPresent()) {
      throw new HotelGuestNotFoundException("O hospede nao foi encontrado.");
    }

    EntityModel<Optional<Hospede>> model = EntityModel.of(hospede);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodosHospedes());
    model.add(linkToUsers.withRel("todos-hospedes"));

    return model;
  }

  @PostMapping("/hospedes")
  public ResponseEntity<Object> cadastrarHospede(@Valid @RequestBody HospedeDTO hospedeDTO){
    return hospedeService.cadastrarHospede(hospedeDTO);
  }
}
