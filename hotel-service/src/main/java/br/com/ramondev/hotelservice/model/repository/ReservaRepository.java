package br.com.ramondev.hotelservice.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {

  Reserva findByHospede(Hospede hospede);
}
