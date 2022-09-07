package br.com.ramondev.hotelservice.model.service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;
import br.com.ramondev.hotelservice.model.dto.ReservaDTO;
import br.com.ramondev.hotelservice.model.exception.ApartmentNotFoundException;
import br.com.ramondev.hotelservice.model.exception.ApartmentsNotAvailableException;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.exception.InvalidOccupantsNumberException;
import br.com.ramondev.hotelservice.model.repository.ApartamentoRepository;
import br.com.ramondev.hotelservice.model.repository.HospedeRepository;
import br.com.ramondev.hotelservice.model.repository.ReservaRepository;

@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  @Autowired
  private HospedeRepository hospedeRepository;

  @Autowired
  private ApartamentoRepository apartamentoRepository;

  public Reserva buscarReservaPorCpf(String cpfHospede) {
    return reservaRepository.findByHospede(hospedeRepository.findByCpfHospede(cpfHospede));
  }

  public List<Reserva> buscarTodasReservas() {
    return reservaRepository.findAll();
  }

  public Reserva buscarReserva(String cpfHospede) {
    return reservaRepository.findByHospede(hospedeRepository.findByCpfHospede(cpfHospede));
  }

  @Transactional
  public ResponseEntity<Object> cadastrarReserva(ReservaDTO reservaDTO) {
    Hospede hospede = hospedeRepository.findByCpfHospede(reservaDTO.getCpfHospede().replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema.");
    }

    if(reservaDTO.getNumeroOcupantes() <= 0){
      throw new InvalidOccupantsNumberException("Numero de ocupantes e invalido.");
    }

    int numeroApartamentoReservado = 0;

    List<Apartamento> apartamentos = apartamentoRepository.findAllByTipoApartamento(reservaDTO.getTipoApartamento());

    for(Apartamento a: apartamentos){
      if(a.isDisponibilidade()){
        numeroApartamentoReservado = a.getNumeroApartamento();
      }
    }

    if(numeroApartamentoReservado == 0){
      throw new ApartmentsNotAvailableException("Nao ha apartamentos do modelo solicitado disponiveis.");
    }

    Reserva reservaCriada = new Reserva(UUID.randomUUID().toString(), new Date(), reservaDTO.getDataChegadaPrevista(),
        reservaDTO.getDataSaidaPrevista(), reservaDTO.getNumeroOcupantes(), numeroApartamentoReservado, StatusReservaEnum.AGUARDANDO_CHECK_IN,
        reservaDTO.getTipoApartamento(), false,
        reservaDTO.isIncluirVagaGaragem(), hospede);

    reservaCriada = reservaRepository.save(reservaCriada);

    // atualizar disponibilidade do apartamento designado para indisponivel
    // dessa forma ele não pode ser atribuido novamente a outro hospede
    Apartamento apartamentoCriado = new Apartamento();

    Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(numeroApartamentoReservado);  
    
    if(apartamento == null){
      throw new ApartmentNotFoundException("Apartamento nao foi encontrado no sistema");
    }
    apartamentoCriado = apartamento;

    apartamentoCriado.setDisponibilidade(false);;
    apartamentoRepository.save(apartamentoCriado);

    //------

    URI locationReserva = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{cpfHospede}")
        .buildAndExpand(reservaCriada.getHospede().getCpfHospede())
        .toUri();

    return ResponseEntity.created(locationReserva).build();
  }
}
