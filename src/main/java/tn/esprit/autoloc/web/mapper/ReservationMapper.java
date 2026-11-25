package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // "client.nom" : MapStruct suit la relation Reservation -> Client.
    // Si la réservation n'a pas de client (ou de véhicule), les champs restent à null.
    @Mapping(source = "client.nom", target = "nomClient")
    @Mapping(source = "client.prenom", target = "prenomClient")
    @Mapping(source = "vehicule.marque", target = "marqueVehicule")
    @Mapping(source = "vehicule.modele", target = "modeleVehicule")
    @Mapping(source = "vehicule.immatriculation", target = "immatriculationVehicule")
    ReservationResponseDto toResponseDto(Reservation reservation);

    // MapStruct réutilise la méthode ci-dessus pour chaque élément
    List<ReservationResponseDto> toResponseDtoList(List<Reservation> reservations);
}
