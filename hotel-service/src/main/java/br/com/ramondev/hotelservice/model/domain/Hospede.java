package br.com.ramondev.hotelservice.model.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

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

  @Size(min=2, message="O nome deve conter no mínimo 2 caracteres.")
  @Column(name = "nome")
  private String nomeHospede;

  @Column(name = "cpf")
  private String cpfHospede;
  
  @Column(name = "rg")
  private String rgHospede;
  
  @Past(message="A data deve estar no passado.")
  @Column(name = "data_nascimento")
  private DateTime dataNascimentoHospede;
}