package br.com.ramondev.hotelservice.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ramondev.hotelservice.model.domain.Hospede;
import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.domain.enums.StatusReservaEnum;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String> {

  Reserva findByHospede(Hospede hospede);

  List<Reserva> findAllByStatusReserva(StatusReservaEnum string);
}
