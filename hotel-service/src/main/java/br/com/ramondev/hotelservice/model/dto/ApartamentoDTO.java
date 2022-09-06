package br.com.ramondev.hotelservice.model.dto;

import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ApartamentoDTO {
  private TipoApartamentoEnum tipoApartamento;
}
