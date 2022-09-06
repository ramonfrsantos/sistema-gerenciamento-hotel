package br.com.ramondev.hotelservice.model.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import br.com.ramondev.hotelservice.model.dto.ApartamentoDTO;
import br.com.ramondev.hotelservice.model.exception.ApartmentNotFoundException;
import br.com.ramondev.hotelservice.model.exception.RegisterBadRequestException;
import br.com.ramondev.hotelservice.model.repository.ApartamentoRepository;
import br.com.ramondev.hotelservice.utils.CustomComparator;

@Service
public class ApartamentoService {

  @Autowired
  private ApartamentoRepository apartamentoRepository;

  public List<Apartamento> buscarTodosApartamentos() {
    return apartamentoRepository.findAll();
  }

  public EntityModel<Apartamento> buscarApartamento(int numeroApartamento) {
    Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(numeroApartamento);

    if (apartamento == null) {
      throw new ApartmentNotFoundException("O apartamento nao foi encontrado.");
    }

    EntityModel<Apartamento> model = EntityModel.of(apartamento);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodosApartamentos());
    model.add(linkToUsers.withRel("todos-apartamentos"));

    return model;
  }

  @Transactional
  public ResponseEntity<Object> cadastrarApartamento(ApartamentoDTO apartamentoDTO) {

    // verifica se é possível cadastrar apartamentos tipo PADRÃO ou PRESIDENCIAL
    // são permitidos no máximo 80 do tipo PADRÃO e 2 do tipo PRESIDENCIAL
    // atribui preço às diárias de acordo com o tipo do apartamento

    Apartamento apartamentoCriado = new Apartamento(apartamentoDTO.getTipoApartamento());

    switch (apartamentoDTO.getTipoApartamento()) {

      case PADRAO:
        List<Apartamento> apartamentosTipoPadrao = apartamentoRepository
            .findAllByTipoApartamento(TipoApartamentoEnum.PADRAO);

        if (apartamentosTipoPadrao.size() >= 80) {
          throw new RegisterBadRequestException("Nao e possivel cadastrar mais apartamentos do tipo 'padrao'");
        } else if (apartamentosTipoPadrao.size() == 0) {
          apartamentoCriado.setNumeroApartamento(100);
        } else {
          apartamentosTipoPadrao.sort(new CustomComparator());

          apartamentoCriado.setNumeroApartamento(apartamentosTipoPadrao.get(apartamentosTipoPadrao.size()-1).getNumeroApartamento() + 10);
        }

        apartamentoCriado.setPrecoDiariaSegundaASexta(120.0);
        apartamentoCriado.setPrecoDiariaFimDeSemana(150.0);
        break;

      // ------------

      case PRESIDENCIAL:
        List<Apartamento> apartamentosTipoPresidencial = apartamentoRepository
            .findAllByTipoApartamento(TipoApartamentoEnum.PRESIDENCIAL);

        if (apartamentosTipoPresidencial.size() >= 2) {
          throw new RegisterBadRequestException("Nao e possivel cadastrar mais apartamentos do tipo 'presidencial'");
        } else if (apartamentosTipoPresidencial.size() == 0) {
          apartamentoCriado.setNumeroApartamento(1000);
        } else {
          apartamentosTipoPresidencial.sort(new CustomComparator());

          apartamentoCriado.setNumeroApartamento(apartamentosTipoPresidencial.get(apartamentosTipoPresidencial.size()-1).getNumeroApartamento() + 1);
        }

        apartamentoCriado.setPrecoDiariaSegundaASexta(360.0);
        apartamentoCriado.setPrecoDiariaFimDeSemana(450.0);
        break;

      // -------------

      default:
        throw new ApartmentNotFoundException("O tipo do apartamento nao foi especificado.");
    }

    apartamentoCriado.setDisponibilidade(true);
    apartamentoCriado = apartamentoRepository.save(apartamentoCriado);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{numeroApartamento}")
        .buildAndExpand(apartamentoCriado.getNumeroApartamento())
        .toUri();

    return ResponseEntity.created(location).build();
  }

}
