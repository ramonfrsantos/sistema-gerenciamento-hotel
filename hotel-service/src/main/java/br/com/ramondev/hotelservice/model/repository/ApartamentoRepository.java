package br.com.ramondev.hotelservice.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramondev.hotelservice.model.domain.Apartamento;
import br.com.ramondev.hotelservice.model.domain.enums.TipoApartamentoEnum;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {

  Apartamento findByNumeroApartamento(int numeroApartamento);

  List<Apartamento> findAllByTipoApartamento(TipoApartamentoEnum tipoApartamento); 
}