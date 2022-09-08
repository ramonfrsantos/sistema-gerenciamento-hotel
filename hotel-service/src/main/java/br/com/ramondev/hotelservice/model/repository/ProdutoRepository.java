package br.com.ramondev.hotelservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramondev.hotelservice.model.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{
  Produto findByNomeProduto(String nomeProduto);
}
