package br.com.ramondev.hotelservice.model.dto;

import java.util.Date;

import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ReservaDTO {

  private Date dataChegadaPrevista;

  private Date dataSaidaPrevista;

  private int numeroOcupantes;

  private TipoApartamentoEnum tipoApartamento;

  private boolean incluirVagaGaragem;
  
  private String cpfHospede;
}
