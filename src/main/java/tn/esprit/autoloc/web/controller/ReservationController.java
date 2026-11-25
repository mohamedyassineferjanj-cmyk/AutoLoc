package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    // Activité DTO : liste "à plat" avec les infos essentielles du client et du véhicule
    @GetMapping
    public List<ReservationResponseDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Activité DTO : réservations d'un client (404 si le client n'existe pas)
    @GetMapping("/client/{idClient}")
    public List<ReservationResponseDto> getReservationsByClient(@PathVariable("idClient") Long idClient) {
        return reservationService.getReservationsByClient(idClient);
    }

    @GetMapping("/{id}")
    public Reservation findById(@PathVariable("id") Long id) {
        return reservationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        reservationService.deleteById(id);
    }
}
