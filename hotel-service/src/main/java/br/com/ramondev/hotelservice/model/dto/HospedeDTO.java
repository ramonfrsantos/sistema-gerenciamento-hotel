package br.com.ramondev.hotelservice.model.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HospedeDTO {
  
  @Email(regexp = ".+[@].+[\\.].+")
  private String emailHospede;

  @Size(min = 2, message = "O nome deve conter no mínimo 2 caracteres.")
  private String nomeHospede;
  
  private String cpfHospede;
  
  private String rgHospede;

  @Past(message = "A data deve estar no passado.")
  private Date dataNascimentoHospede;
}
