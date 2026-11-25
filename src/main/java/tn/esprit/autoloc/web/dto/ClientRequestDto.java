package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Données REÇUES lors de la création d'un client (POST /api/clients).
 * Le client ne peut envoyer que ces 4 champs : impossible d'imposer un id,
 * une date d'inscription ou des réservations.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50)
    private String prenom;

    // Facultatif ; s'il est fourni, il doit avoir un format d'email valide
    @Email(message = "Format d'email invalide")
    @Size(max = 100)
    private String email;

    // Facultatif
    @Size(max = 20)
    private String telephone;
}
