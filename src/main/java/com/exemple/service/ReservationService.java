package com.exemple.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemple.model.*;
import com.exemple.repository.*;

@Service
public class ReservationService {
    
    @Autowired
    private AdherentRepository adherentRepository;
    
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    
    @Autowired
    private AbonnementRepository abonnementRepository;
    
    @Autowired
    private PenaliteRepository penaliteRepository;

        
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Transactional
    public String reserverExemplaire(int idExemplaire, int idAdherent, LocalDateTime dateReservation) {
        // Vérifications
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("Adhérent non trouvé"));
        
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
                .orElseThrow(() -> new RuntimeException("Exemplaire non trouvé"));
        
        // Vérification adhérent actif
        if (abonnementRepository.findActiveByAdherent(idAdherent, LocalDate.now()).isEmpty()) {
            return "Adhésion expirée";
        }
        
        // Vérification pénalités
        if (!penaliteRepository.findActivePenalitesByAdherent(idAdherent).isEmpty()) {
            return "Vous ne pouvez pas réserver à cause d'une pénalité active";
        }
        
        // Vérification réservation existante
        if (reservationRepository.existsByAdherentAndExemplaire(idAdherent, idExemplaire)) {
            return "Vous avez déjà une réservation active pour ce livre";
        }
        
        if (LocalDate.now().getYear() - adherent.getDateNaissance().getYear() < exemplaire.getLivre().getAge_minimum()) {
            return "L'âge de l'adhérent ne correspond pas à l'âge minimum requis";
        }
        
        // Création réservation
        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setExemplaire(exemplaire);
        reservation.setDate_reservation(dateReservation);
        reservationRepository.save(reservation);
        
        return "Réservation effectuée pour le " + dateReservation.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }   
}