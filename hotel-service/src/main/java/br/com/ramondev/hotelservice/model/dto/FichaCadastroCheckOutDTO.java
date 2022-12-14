package br.com.ramondev.hotelservice.model.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FichaCadastroCheckOutDTO {
  private String cpfHospede;
  private Date dataSaida;
  
  public FichaCadastroCheckOutDTO(String cpfHospede, Date dataSaida) {
    this.cpfHospede = cpfHospede;
    this.dataSaida = dataSaida;
  }

  public FichaCadastroCheckOutDTO() {
  }

}
