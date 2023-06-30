package br.com.ramondev.hotelservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProdutoDTO {
  @JsonIgnore
  private String id;
  private String nomeProduto;
  private double preco;
  private int quantidade;
}
