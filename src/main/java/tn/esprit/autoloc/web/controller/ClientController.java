package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
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
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // Activité DTO : on renvoie un ClientResponseDto, pas l'entité
    @GetMapping("/{id}")
    public ClientResponseDto getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }

    // Activité DTO : on reçoit un ClientRequestDto validé (@Valid -> 400 si nom/prénom absents)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto addClient(@Valid @RequestBody ClientRequestDto dto) {
        return clientService.addClient(dto);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable("id") Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        clientService.deleteById(id);
    }
}
