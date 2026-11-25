package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

/**
 * MapStruct génère l'implémentation (ClientMapperImpl) à la compilation,
 * dans target/generated-sources/annotations. componentModel = "spring" en fait un bean injectable.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    // DTO reçu -> entité : les champs absents du DTO sont ignorés explicitement
    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "numPermis", ignore = true)
    @Mapping(target = "dateInscription", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientRequestDto dto);

    // entité -> DTO renvoyé : mêmes noms de champs, aucune annotation nécessaire
    ClientResponseDto toResponseDto(Client client);
}
