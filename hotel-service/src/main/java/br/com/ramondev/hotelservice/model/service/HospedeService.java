package br.com.ramondev.hotelservice.model.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;
import br.com.ramondev.hotelservice.model.dto.HospedeDTO;
import br.com.ramondev.hotelservice.model.exception.HotelGuestExistsException;
import br.com.ramondev.hotelservice.model.repository.HospedeRepository;
import br.com.ramondev.hotelservice.model.repository.ReservaRepository;

@Service
public class HospedeService {

  @Autowired
  private HospedeRepository hospedeRepository;

  @Autowired
  private ReservaRepository reservaRepository;

  public List<Hospede> buscarTodosHospedes() {
    return hospedeRepository.findAll();
  }

  public Optional<Hospede> buscarHospede(String id) {
    return hospedeRepository.findById(id);
  }

  @Transactional
  public ResponseEntity<Object> cadastrarHospede(HospedeDTO hospedeDTO) {

    if(hospedeRepository.findByCpfHospede(hospedeDTO.getCpfHospede().replace(".", "").replace("-", "")) != null){
      throw new HotelGuestExistsException("Hospede com o CPF informado ja existe no sistema.");
    }

    Hospede hospedeCriado = new Hospede(
        UUID.randomUUID().toString(),
        hospedeDTO.getEmailHospede(),
        hospedeDTO.getNomeHospede(),
        hospedeDTO.getCpfHospede().replace(".", "").replace("-", ""),
        hospedeDTO.getRgHospede().replace(".", "").replace("-", ""),
        hospedeDTO.getDataNascimentoHospede());

    hospedeCriado = hospedeRepository.save(hospedeCriado);

    URI locationHospede = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(hospedeCriado.getId())
        .toUri();

    return ResponseEntity.created(locationHospede).build();
  }

  public List<Hospede> buscarExHospedes() {
    List<Hospede> exHospedes = new ArrayList<Hospede>();
    
    List<Reserva> reservasEncerradas = reservaRepository.findAllByStatusReserva(StatusReservaEnum.RESERVA_ENCERRADA);
    for(Reserva reserva: reservasEncerradas){
      exHospedes.add(reserva.getHospede());
    }

    return exHospedes;
  }

}
