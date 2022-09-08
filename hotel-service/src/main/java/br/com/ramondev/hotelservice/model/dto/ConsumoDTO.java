package br.com.ramondev.hotelservice.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConsumoDTO {
  private List<ProdutoDTO> consumo;
  private String cpfHospede;

  public ConsumoDTO() {
  }

  public ConsumoDTO(List<ProdutoDTO> consumo, String cpfHospede) {
    this.consumo = consumo;
    this.cpfHospede = cpfHospede;
  }
}
