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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.FichaCadastro;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckInDTO;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckOutDTO;
import br.com.ramondev.hotelservice.model.exception.RegistrationFormNotFoundException;
import br.com.ramondev.hotelservice.model.service.FichaCadastroService;

@RestController
public class FichaCadastroController {
  @Autowired
  private FichaCadastroService fichaCadastroService;

  @GetMapping("/fichas-cadastro")
  public List<FichaCadastro> buscarTodasFichasCadastro() {
    return fichaCadastroService.buscarTodasFichasCadastro();
  }

  @GetMapping("/fichas-cadastro/{cpfHospede}")
  public EntityModel<FichaCadastro> buscarFicha(@PathVariable String cpfHospede) {
    FichaCadastro fichaCadastro = fichaCadastroService.buscarFichaPorCpf(cpfHospede);
    
    if (fichaCadastro == null) {
      throw new RegistrationFormNotFoundException("Hospede nao possui ficha de cadastro no hotel. Verifique se o Check In foi realizado.");
    }

    EntityModel<FichaCadastro> model = EntityModel.of(fichaCadastro);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodasFichasCadastro());
    model.add(linkToUsers.withRel("todas-fichas-cadastro"));

    return model;
  }

  @PostMapping("/fichas-cadastro/check-in")
  public ResponseEntity<Object> fazerCheckIn(@RequestBody FichaCadastroCheckInDTO fichaCheckInDTO){
    return fichaCadastroService.fazerCheckIn(fichaCheckInDTO);
  }

  @PutMapping("/fichas-cadastro/check-out")
  public ResponseEntity<Object> fazerCheckOut(@RequestBody FichaCadastroCheckOutDTO fichaCheckOutDTO){
    return fichaCadastroService.fazerCheckOut(fichaCheckOutDTO);
  }
}