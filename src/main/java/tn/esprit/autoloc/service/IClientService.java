package tn.esprit.autoloc.service;

import java.util.List;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

public interface IClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client create(Client client);

    Client update(Long id, Client client);

    void deleteById(Long id);

    // ----- Activité DTO -----

    ClientResponseDto addClient(ClientRequestDto dto);

    ClientResponseDto getClientById(Long id);
}
