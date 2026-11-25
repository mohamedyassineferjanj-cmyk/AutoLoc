package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    // @EntityGraph : charge client et véhicule dans la MÊME requête (jointure),
    // au lieu d'une requête supplémentaire par réservation (problème N+1)
    @Override
    @EntityGraph(attributePaths = {"client", "vehicule"})
    List<Reservation> findAll();

    // Requête dérivée : WHERE reservation.client_id = ?
    @EntityGraph(attributePaths = {"client", "vehicule"})
    List<Reservation> findByClient_IdClient(Long idClient);
}
