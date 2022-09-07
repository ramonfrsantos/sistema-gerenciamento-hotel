package br.com.ramondev.hotelservice.model.service;

import java.net.URI;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.FichaCadastro;
import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Produto;
import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckInDTO;
import br.com.ramondev.hotelservice.model.dto.FichaCadastroCheckOutDTO;
import br.com.ramondev.hotelservice.model.exception.ApartmentNotFoundException;
import br.com.ramondev.hotelservice.model.exception.HotelGuestNotFoundException;
import br.com.ramondev.hotelservice.model.exception.RegistrationFormNotFoundException;
import br.com.ramondev.hotelservice.model.exception.ReservationNotFoundException;
import br.com.ramondev.hotelservice.model.repository.ApartamentoRepository;
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

  @Autowired
  private ApartamentoRepository apartamentoRepository;

  public List<FichaCadastro> buscarTodasFichasCadastro() {
    return fichaCadastroRepository.findAll();
  }

  public FichaCadastro buscarFichaPorCpf(String cpfHospede) {
    return fichaCadastroRepository
        .findByHospede(hospedeRepository.findByCpfHospede(cpfHospede.replace(".", "").replace("-", "")));
  }

  @Transactional
  public ResponseEntity<Object> fazerCheckIn(FichaCadastroCheckInDTO fichaCheckInDTO) {
    Hospede hospede = hospedeRepository
        .findByCpfHospede(fichaCheckInDTO.getCpfHospede().replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema.");
    }

    // uma variação mais plausível seria utilizar a data atual como data de check in
    // para a simulação dos valores, vamos usar datas fictícias para ckeck in e
    // check out
    FichaCadastro fichaCadastroCriada = new FichaCadastro(UUID.randomUUID().toString(), hospede,
        fichaCheckInDTO.getDataEntrada(), false);

    fichaCadastroCriada = fichaCadastroRepository.save(fichaCadastroCriada);

    URI locationHospede = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{cpfHospede}")
        .buildAndExpand(fichaCadastroCriada.getHospede().getCpfHospede())
        .toUri();

    // atualizar status da reserva pós check in
    Reserva reservaCriada = new Reserva();

    Reserva reserva = reservaRepository.findByHospede(hospede);

    if (reserva == null) {
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

  @Transactional
  public ResponseEntity<Object> fazerCheckOut(FichaCadastroCheckOutDTO fichaCheckOutDTO) {
    Hospede hospede = hospedeRepository
        .findByCpfHospede(fichaCheckOutDTO.getCpfHospede().replace(".", "").replace("-", ""));

    if (hospede == null) {
      throw new HotelGuestNotFoundException("Hospede nao encontrado no sistema.");
    }

    FichaCadastro fichaCadastro = fichaCadastroRepository.findByHospede(hospede);

    if (fichaCadastro == null) {
      throw new RegistrationFormNotFoundException(
          "Hospede nao possui ficha de cadastro no hotel. Verifique se o Check In foi realizado.");
    }

    FichaCadastro fichaCadastroCriada = new FichaCadastro();
    fichaCadastroCriada = fichaCadastro;

    // aplica data de saida no checkout
    fichaCadastroCriada.setDataSaida(fichaCheckOutDTO.getDataSaida());

    // calcula quantidade de dias de segunda a sexta e de finais de semana
    int diasSegundaASexta = totalDiasSegundaSextaNoPeriodo(fichaCadastro.getDataEntrada(),
        fichaCheckOutDTO.getDataSaida());
    int finaisDeSemana = totalFinaisDeSemanaNoPeriodo(fichaCadastro.getDataEntrada(), fichaCheckOutDTO.getDataSaida());

    // buscar apartamento da reserva
    Reserva reserva = reservaRepository.findByHospede(hospede);

    if (reserva == null) {
      throw new ReservationNotFoundException("Reserva do hospede nao encontrada.");
    }

    Apartamento apartamento = apartamentoRepository.findByNumeroApartamento(reserva.getNumeroApartamento());

    if (apartamento == null) {
      throw new ApartmentNotFoundException("Apartamento nao encontrado.");
    }

    // aplica o valor final do consumo
    double valorFinalConsumo = 0;

    for (Produto p : fichaCadastro.getConsumo()) {
      valorFinalConsumo += (p.getPreco() * p.getQuantidade());
    }

    // verifica se houve reserva da vaga na garagem
    double valorVagaGaragem = 0;
    if (reserva.isIncluirVagaGaragem()) {
      valorVagaGaragem += (diasSegundaASexta * 15.0) + (finaisDeSemana * 20.0);
    }

    double valorDiariaExtra = 0;

    Calendar dataSaidaCal = Calendar.getInstance();
    dataSaidaCal.setTime(fichaCheckOutDTO.getDataSaida());
    dataSaidaCal.set(Calendar.HOUR_OF_DAY, 16);
    dataSaidaCal.set(Calendar.MINUTE, 30);

    Date dataHoraLimiteCheckOut = dataSaidaCal.getTime();

    // verifica se o check out ultrapassou o horario limte
    if (fichaCheckOutDTO.getDataSaida().after(dataHoraLimiteCheckOut)) {

      // verifica o dia do check out para cobrar a diaria adicional
      // no caso, está sendo verificado se cai no final de semana
      if (fichaCheckOutDTO.getDataSaida().toInstant().atZone(ZoneId.of("UTC")).getDayOfWeek().getValue() == 6
          || fichaCheckOutDTO.getDataSaida().toInstant().atZone(ZoneId.of("UTC")).getDayOfWeek().getValue() == 7) {
        valorDiariaExtra += apartamento.getPrecoDiariaFimDeSemana();
      } else {
        valorDiariaExtra += apartamento.getPrecoDiariaSegundaASexta();
      }
    }

    System.out.println("---------------------------------------------------------------");
    System.out.println("Dias de segunda a sexta: " + diasSegundaASexta);
    System.out.println("Finais de semana: " + finaisDeSemana);
    System.out.printf("Valor final do consumo: R$ %.2f\n", valorFinalConsumo);
    System.out.printf(reserva.isIncluirVagaGaragem() == true ? "Possui vaga na garagem: SIM | Valor total do acrescimo: R$ %.2f\n" : "Possui vaga na garagem: NAO\n", valorVagaGaragem);
    System.out.println("---------------------------------------------------------------");

    double valorTotalHospedagem = (diasSegundaASexta * apartamento.getPrecoDiariaSegundaASexta())
    + (finaisDeSemana * apartamento.getPrecoDiariaFimDeSemana()) + (valorFinalConsumo + valorVagaGaragem
    + valorDiariaExtra);

    System.out.printf("Valor total da hospedagem R$ %.2f", valorTotalHospedagem);

    // calcula o valor final da hospedagem
    fichaCadastroCriada.setValorHospedagem(valorTotalHospedagem);

    // pagamento efetuado
    fichaCadastroCriada.setPagamentoHospedagemEfetuado(true);
    fichaCadastroCriada = fichaCadastroRepository.save(fichaCadastroCriada);

    // apartamento disponivel novamente
    Apartamento apartamentoCriado = new Apartamento();
    apartamentoCriado = apartamento;
    apartamentoCriado.setDisponibilidade(true);
    apartamentoRepository.save(apartamentoCriado);

    // reserva encerrada
    Reserva reservaCriada = new Reserva();
    reservaCriada = reserva;
    reservaCriada.setStatusReserva(StatusReservaEnum.RESERVA_ENCERRADA);

    return ResponseEntity.ok().build();
  }

  public static int totalDiasSegundaSextaNoPeriodo(Date chegada, Date saida) {

    Calendar inicioCal = Calendar.getInstance();
    inicioCal.setTime(chegada);

    Calendar fimCal = Calendar.getInstance();
    fimCal.setTime(saida);

    int diasSegundaASexta = 0;

    // se saida e chegada forem os mesmos retorna 0
    if (inicioCal.getTimeInMillis() == fimCal.getTimeInMillis())
      return 0;

    if (inicioCal.getTimeInMillis() > fimCal.getTimeInMillis()) {
      inicioCal.setTime(chegada);
      fimCal.setTime(saida);
    }

    do {
      inicioCal.add(Calendar.DAY_OF_MONTH, 1);
      if (inicioCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
          && inicioCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
        ++diasSegundaASexta;
      }
    } while (inicioCal.getTimeInMillis() < fimCal.getTimeInMillis());

    return diasSegundaASexta;
  }

  public static int totalFinaisDeSemanaNoPeriodo(Date chegada, Date saida) {

    Calendar inicioCal = Calendar.getInstance();
    inicioCal.setTime(chegada);

    Calendar fimCal = Calendar.getInstance();
    fimCal.setTime(saida);

    int diasFinaisDeSemana = 0;

    // se saida e chegada forem os mesmos retorna 0
    if (inicioCal.getTimeInMillis() == fimCal.getTimeInMillis())
      return 0;

    if (inicioCal.getTimeInMillis() > fimCal.getTimeInMillis()) {
      inicioCal.setTime(chegada);
      fimCal.setTime(saida);
    }

    do {
      inicioCal.add(Calendar.DAY_OF_MONTH, 1);
      if (inicioCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
          || inicioCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
        ++diasFinaisDeSemana;
      }
    } while (inicioCal.getTimeInMillis() < fimCal.getTimeInMillis());

    return diasFinaisDeSemana;
  }
}
