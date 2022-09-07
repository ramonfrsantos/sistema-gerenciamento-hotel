package br.com.ramondev.hotelservice.model.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@DynamicUpdate
@Table(name = "t_hospede")
public class Hospede {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "email")
  private String emailHospede;

  @Column(name = "nome")
  private String nomeHospede;

  @Column(name = "cpf")
  private String cpfHospede;

  @Column(name = "rg")
  private String rgHospede;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_nascimento")
  private Date dataNascimentoHospede;

  public Hospede() {
  }

  public Hospede(String id, String emailHospede,
      String nomeHospede, String cpfHospede,
      String rgHospede, Date dataNascimentoHospede) {
    this.id = id;
    this.emailHospede = emailHospede;
    this.nomeHospede = nomeHospede;
    this.cpfHospede = cpfHospede;
    this.rgHospede = rgHospede;
    this.dataNascimentoHospede = dataNascimentoHospede;
  }
}