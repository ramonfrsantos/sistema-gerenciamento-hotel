package br.com.ramondev.hotelservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.dto.HospedeDTO;
import br.com.ramondev.hotelservice.model.service.HospedeService;

@RestController
public class HospedeController {
  
  @Autowired
  private HospedeService hospedeService;

  @GetMapping("/hospedes")
  public List<Hospede> buscarTodosHospedes(){
    return hospedeService.buscarTodosHospedes();
  }

  @GetMapping("/hospedes/{id}")
  public EntityModel<Optional<Hospede>> buscarHospede(@PathVariable String id){
    return hospedeService.buscarHospede(id);
  }

  @PostMapping("/hospedes")
  public ResponseEntity<Object> cadastrarHospede(@Valid @RequestBody HospedeDTO hospedeDTO){
    return hospedeService.cadastrarHospede(hospedeDTO);
  }
}
