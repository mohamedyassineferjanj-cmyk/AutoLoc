package tn.esprit.autoloc.service;

import java.util.List;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

public interface IReservationService {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);

    // ----- Activité DTO -----

    List<ReservationResponseDto> getAllReservations();

    List<ReservationResponseDto> getReservationsByClient(Long idClient);
}
