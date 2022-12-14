package br.com.ramondev.hotelservice.model.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

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
  private String id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_realizacao_reserva")
  private Date dataRealizacaoReserva;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_chegada_prevista")
  private Date dataChegadaPrevista;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_saida_prevista")
  private Date dataSaidaPrevista;

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

  public Reserva() {
  }

  public Reserva(String id, Date dataRealizacaoReserva, Date dataChegadaPrevista, Date dataSaidaPrevista,
      int numeroOcupantes, int numeroApartamento, StatusReservaEnum statusReserva, TipoApartamentoEnum tipoApartamento,
      boolean pagamentoEfetuado, boolean incluirVagaGaragem, Hospede hospede) {
    this.id = id;
    this.dataRealizacaoReserva = dataRealizacaoReserva;
    this.dataChegadaPrevista = dataChegadaPrevista;
    this.dataSaidaPrevista = dataSaidaPrevista;
    this.numeroOcupantes = numeroOcupantes;
    this.numeroApartamento = numeroApartamento;
    this.statusReserva = statusReserva;
    this.tipoApartamento = tipoApartamento;
    this.pagamentoEfetuado = pagamentoEfetuado;
    this.incluirVagaGaragem = incluirVagaGaragem;
    this.hospede = hospede;
  }
}