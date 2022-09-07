package br.com.ramondev.hotelservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramondev.hotelservice.model.domain.FichaCadastro;
import br.com.ramondev.hotelservice.model.domain.Hospede;

@Repository
public interface FichaCadastroRepository extends JpaRepository<FichaCadastro, String>{

  FichaCadastro findByHospede(Hospede hospede);
}
