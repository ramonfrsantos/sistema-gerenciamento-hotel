package br.com.ramondev.hotelservice.model.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;
import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@DynamicUpdate
@Table(name = "t_reserva")
public class Reserva {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "data_realizacao_reserva")
  private DateTime dataRealizacaoReserva;
  
  @Column(name = "data_chegada_prevista")
  private DateTime dataChegadaPrevista;
  
  @Column(name = "data_saida_prevista")
  private DateTime dataSaidaPrevista;

  @Column(name = "numero_ocupantes")
  private int numeroOcupantes;

  @Column(name = "numero_apartamento")
  private int numeroApartamento;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_reserva")
  private StatusReservaEnum statusReserva;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_apartamento")
  private TipoApartamentoEnum tipoApartamento;

  @Column(name = "pagamento_reserva_efetuado")
  private boolean pagamentoEfetuado;

  @Column(name = "vaga_garagem")
  private boolean incluirVagaGaragem;

  @OneToOne
  @JoinColumn(name = "fk_hospede")
  private Hospede hospede;
}