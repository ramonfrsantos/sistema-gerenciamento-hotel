package br.com.ramondev.hotelservice.model.service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.FichaCadastro;
import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckInDTO;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.exception.ReservationNotFoundException;
import br.com.ramondev.hotelservice.model.repository.FichaCadastroRepository;
import br.com.ramondev.hotelservice.model.repository.HospedeRepository;
import br.com.ramondev.hotelservice.model.repository.ReservaRepository;

@Service
public class FichaCadastroService {

  @Autowired
  private FichaCadastroRepository fichaCadastroRepository;

  @Autowired
  private HospedeRepository hospedeRepository;

  @Autowired
  private ReservaRepository reservaRepository;

  public List<FichaCadastro> buscarTodasFichasCadastro(){
    return fichaCadastroRepository.findAll();
  }

  public FichaCadastro buscarFichaPorCpf(String cpfHospede) {
    return fichaCadastroRepository.findByHospede(hospedeRepository.findByCpfHospede(cpfHospede.replace(".", "").replace("-", "")));
  }

  @Transactional
  public ResponseEntity<Object> fazerCheckIn(FichaCadastroCheckInDTO fichaCheckInDTO){
    Hospede hospede = hospedeRepository.findByCpfHospede(fichaCheckInDTO.getCpfHospede().replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema.");
    }
    
    // uma variação mais plausível seria utilizar a data atual como data de check in
    // para a simulação dos valores, vamos usar datas fictícias para ckeck in e check out
    FichaCadastro fichaCadastroCriada = new FichaCadastro(UUID.randomUUID().toString(), hospede, fichaCheckInDTO.getDataEntrada(), false);

    fichaCadastroCriada = fichaCadastroRepository.save(fichaCadastroCriada);
    
    URI locationHospede = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{cpfHospede}")
        .buildAndExpand(fichaCadastroCriada.getHospede().getCpfHospede())
        .toUri();

    // atualizar status da reserva pós check in
    Reserva reservaCriada = new Reserva();

    Reserva reserva = reservaRepository.findByHospede(hospede);  
    
    if(reserva == null){
      throw new ReservationNotFoundException("Reserva nesse CPF nao foi encontrada");
    }
    reservaCriada = reserva;

    reservaCriada.setStatusReserva(StatusReservaEnum.RESERVA_CONFIRMADA);

    // geralmente, é cobrada uma taxa de reserva do apartamento como garantia
    // estamos considerando que ao fazer o check in, essa taxa tenha sido paga
    reservaCriada.setPagamentoEfetuado(true);
    reservaRepository.save(reservaCriada);

    return ResponseEntity.created(locationHospede).build();
  }
}
