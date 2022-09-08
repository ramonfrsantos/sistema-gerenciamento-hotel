package br.com.ramondev.hotelservice.model.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FichaCadastroCheckInDTO {
  private String cpfHospede;
  private Date dataEntrada;
  
  public FichaCadastroCheckInDTO() {
  }

  public FichaCadastroCheckInDTO(String cpfHospede, Date dataEntrada) {
    this.cpfHospede = cpfHospede;
    this.dataEntrada = dataEntrada;
  }
}
