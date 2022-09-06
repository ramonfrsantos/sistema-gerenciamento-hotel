package br.com.ramondev.hotelservice.model.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

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
  private UUID id;
  
  @Email(regexp = ".+[@].+[\\.].+")
  @Column(name = "email")
  private String emailHospede;

  @Column(name = "nome")
  private String nomeHospede;

  @Column(name = "cpf")
  private String cpfHospede;
  
  @Column(name = "rg")
  private String rgHospede;
  
  @Column(name = "data_nascimento")
  private DateTime dataNascimentoHospede;
}