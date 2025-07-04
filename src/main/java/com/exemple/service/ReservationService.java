package com.exemple.service;

import com.exemple.model.*;
import com.exemple.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    
    @Autowired
    private AdherentRepository adherentRepository;
    
    @Autowired
    private AdherentService adherentService;
        
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Transactional
    public void createReservation(int idExemplaire, int idAdherent) throws Exception {
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
            .orElseThrow(() -> new Exception("Exemplaire introuvable"));
        
        Adherent adherent = adherentRepository.findById(idAdherent)
            .orElseThrow(() -> new Exception("Adhérent introuvable"));
        
        // Vérifier que l'adhérent est actif
        if (!adherentService.isAdherentActif(idAdherent)) {
            throw new Exception("Adhérent suspendu ou non renouvelé");
        }
        
        // Vérifier que l'exemplaire est disponible
        if (!exemplaireService.isDisponible(idExemplaire)) {
            throw new Exception("Exemplaire déjà prêté ou réservé");
        }

        
        // Vérifier que l'adhérent n'a pas déjà réservé cet exemplaire
        if (reservationRepository.existsByExemplaireAndAdherent(exemplaire, adherent)) {
            throw new Exception("Vous avez déjà réservé cet exemplaire");
        }
        
        // Vérifier que l'adhérent n'a pas dépassé son quota de réservations
        TypeAdherent type = adherent.getTypeAdherent();
        long nbReservations = reservationRepository.countByAdherent(adherent);
        if (nbReservations >= type.getNb_reservation_max()) {
            throw new Exception("Quota de réservations atteint");
        }
        
        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setExemplaire(exemplaire);
        reservation.setAdherent(adherent);
        reservation.setDate_reservation(LocalDateTime.now());
        
        reservationRepository.save(reservation);
    }
}