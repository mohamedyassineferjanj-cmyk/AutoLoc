package tn.esprit.autoloc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

/**
 * Vue "à plat" d'une réservation : on n'expose pas les objets Client et Vehicule,
 * seulement les informations utiles, récupérées via les relations de l'entité.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;

    // depuis reservation.client
    private String nomClient;
    private String prenomClient;

    // depuis reservation.vehicule
    private String marqueVehicule;
    private String modeleVehicule;
    private String immatriculationVehicule;
}
