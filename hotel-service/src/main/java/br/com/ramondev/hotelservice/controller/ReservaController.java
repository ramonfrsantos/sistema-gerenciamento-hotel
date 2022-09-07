package br.com.ramondev.hotelservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ramondev.hotelservice.model.domain.Reserva;
import br.com.ramondev.hotelservice.model.dto.ReservaDTO;
import br.com.ramondev.hotelservice.model.exception.ReservationNotFoundException;
import br.com.ramondev.hotelservice.model.service.ReservaService;

@RestController
public class ReservaController {
  
  @Autowired
  private ReservaService reservaService;

  @GetMapping("/reservas")
  public List<Reserva> buscarTodasReservas(){
    return reservaService.buscarTodasReservas();
  }

  @GetMapping("/reservas/{cpfHospede}")
  public EntityModel<Reserva> buscarReserva(@PathVariable String cpfHospede){
    Reserva reserva = reservaService.buscarReserva(cpfHospede);
    
    if (reserva == null) {
      throw new ReservationNotFoundException("A reserva desse hospede nao foi encontrada.");
    }

    EntityModel<Reserva> model = EntityModel.of(reserva);

    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).buscarTodasReservas());
    model.add(linkToUsers.withRel("todas-reservas"));

    return model;
  }

  @PostMapping("/reservas")
  public ResponseEntity<Object> cadastrarReserva(@RequestBody ReservaDTO reservaDTO){
    return reservaService.cadastrarReserva(reservaDTO);
  }
}