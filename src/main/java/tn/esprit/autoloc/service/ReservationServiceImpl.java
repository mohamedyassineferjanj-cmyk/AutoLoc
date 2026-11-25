package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation introuvable : id = " + id));
    }

    @Override
    public Reservation create(Reservation reservation) {
        reservation.setIdReservation(null); // id null -> save() fait un persist (INSERT), jamais un merge
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existing = findById(id);
        // Seuls les champs simples sont copiés : les associations ne sont pas écrasées
        // (un merge de l'objet reçu viderait par exemple les listes non renseignées).
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        existing.setStatut(reservation.getStatut());
        return existing; // entité gérée : l'UPDATE part au commit (dirty checking)
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation introuvable : id = " + id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getAllReservations() {
        // La conversion se fait DANS la transaction : client et véhicule sont accessibles
        return reservationMapper.toResponseDtoList(reservationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getReservationsByClient(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            // client inexistant -> 404, plutôt qu'une liste vide trompeuse
            throw new EntityNotFoundException("Client introuvable : id = " + idClient);
        }
        return reservationMapper.toResponseDtoList(reservationRepository.findByClient_IdClient(idClient));
    }
}
