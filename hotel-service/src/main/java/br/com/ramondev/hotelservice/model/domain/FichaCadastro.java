package br.com.ramondev.hotelservice.model.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

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
  private String id;
  
  @OneToOne
  @JoinColumn(name = "fk_hospede")
  private Hospede hospede;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_entrada")
  private Date dataEntrada;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_saida")
  private Date dataSaida;
  
  @Column(name = "valor_hospedagem")
  private double valorHospedagem;

  @Column(name = "pagamento_hospedagem_efetuado")
  private boolean pagamentoHospedagemEfetuado;
  
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "t_consumo", joinColumns = @JoinColumn(name = "fk_consumo"))
  @Column(name = "consumo")
  private List<Produto> consumo;

  public FichaCadastro() {
  }

  public FichaCadastro(String id, Hospede hospede, Date dataEntrada, Date dataSaida, double valorHospedagem,
      boolean pagamentoHospedagemEfetuado, List<Produto> consumo) {
    this.id = id;
    this.hospede = hospede;
    this.dataEntrada = dataEntrada;
    this.dataSaida = dataSaida;
    this.valorHospedagem = valorHospedagem;
    this.pagamentoHospedagemEfetuado = pagamentoHospedagemEfetuado;
    this.consumo = consumo;
  }

  public FichaCadastro(String id, Hospede hospede, Date dataEntrada, boolean pagamentoHospedagemEfetuado) {
    this.id = id;
    this.hospede = hospede;
    this.dataEntrada = dataEntrada;
    this.pagamentoHospedagemEfetuado = pagamentoHospedagemEfetuado;
  }

  
}