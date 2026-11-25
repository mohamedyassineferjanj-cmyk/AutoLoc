package tn.esprit.autoloc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Données RENVOYÉES au client de l'API (GET /api/clients/{id}).
 * L'email, le numéro de permis et les réservations ne sont volontairement pas exposés.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {

    private Long idClient;
    private String nom;
    private String prenom;
    private String telephone;
}
