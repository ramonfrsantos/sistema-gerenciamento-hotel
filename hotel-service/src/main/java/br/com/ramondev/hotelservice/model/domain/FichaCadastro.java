package br.com.ramondev.hotelservice.model.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@DynamicUpdate
@Table(name = "t_ficha_cadastro")
public class FichaCadastro {
  @Id
  @Column(name = "id")
  private UUID id;
  
  @OneToOne
  @JoinColumn(name = "fk_hospede")
  private Hospede hospede;
  
  @Column(name = "data_entrada")
  private DateTime dataEntrada;

  @Column(name = "data_saida")
  private DateTime dataSaida;
  
  @Column(name = "valor_hospedagem")
  private double valorHospedagem;

  @Column(name = "pagamento_hospedagem_efetuado")
  private boolean pagamentoHospedagemEfetuado;
  
  @ElementCollection
  @CollectionTable(name = "t_consumo", joinColumns = @JoinColumn(name = "fk_consumo"))
  @Column(name = "consumo")
  private List<Produto> consumo;
}