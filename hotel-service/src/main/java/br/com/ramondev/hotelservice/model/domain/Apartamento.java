package br.com.ramondev.hotelservice.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@DynamicUpdate
@Table(name = "t_apartamento")
public class Apartamento {
  @Id
  @Column(name = "numero_apartamento")
  private int numeroApartamento;
  
  @Column(name = "preco_diaria_seg_sex")
  private double precoDiariaSegundaASexta;
  
  @Column(name = "preco_diaria_fim_sem")
  private double precoDiariaFimDeSemana;
  
  @Column(name = "disponibilidade")
  private boolean disponibilidade;
  
  @Column(name = "tipo_apartamento")
  @Enumerated(EnumType.STRING)
  private TipoApartamentoEnum tipoApartamento;

  public Apartamento(TipoApartamentoEnum tipoApartamento) {
    this.tipoApartamento = tipoApartamento;
  }
  
  public Apartamento() {
  }
}
