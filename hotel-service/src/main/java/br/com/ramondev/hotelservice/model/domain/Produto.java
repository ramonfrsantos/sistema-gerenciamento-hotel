package br.com.ramondev.hotelservice.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "t_produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private String id;

  @Column(name = "nome")
  private String nomeProduto;
  
  @Column(name = "preco")
  private double preco;

  @Column(name = "quantidade")
  private int quantidade;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_hospede")
  @JsonIgnore
  private Hospede hospede;

  public Produto() {
  }

  public Produto(String nomeProduto, double preco, int quantidade, Hospede hospede) {
    this.nomeProduto = nomeProduto;
    this.preco = preco;
    this.quantidade = quantidade;
    this.hospede = hospede;
  }
}
