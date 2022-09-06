package br.com.ramondev.hotelservice.model.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.dto.HospedeDTO;
import br.com.ramondev.hotelservice.model.exception.HospedeNotFoundException;
import br.com.ramondev.hotelservice.model.repository.HospedeRepository;

@Service
public class HospedeService {

  @Autowired
  private HospedeRepository hospedeRepository;

  public List<Hospede> buscarTodosHospedes() {
    return hospedeRepository.findAll();
  }

  public EntityModel<Optional<Hospede>> buscarHospede(String id) {
    Optional<Hospede> hospede = hospedeRepository.findById(id);

    if (!hospede.isPresent()) {
      throw new HospedeNotFoundException("O hospede nao foi encontrado.");
    }

    EntityModel<Optional<Hospede>> model = EntityModel.of(hospede);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodosHospedes());
    model.add(linkToUsers.withRel("todos-hospedes"));

    return model;
  }

  @Transactional
  public ResponseEntity<Object> cadastrarHospede(HospedeDTO hospedeDTO) {
    Hospede hospedeCriado = new Hospede(
        UUID.randomUUID().toString(),
        hospedeDTO.getEmailHospede(),
        hospedeDTO.getNomeHospede(),
        hospedeDTO.getCpfHospede().replace(".", "").replace("-", ""),
        hospedeDTO.getRgHospede().replace(".", "").replace("-", ""),
        new DateTime(hospedeDTO.getDataNascimentoHospede()));

    hospedeCriado = hospedeRepository.save(hospedeCriado);

    System.out.println(hospedeCriado);

    URI locationHospede = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(hospedeCriado.getId())
        .toUri();

    return ResponseEntity.created(locationHospede).build();
  }

}
