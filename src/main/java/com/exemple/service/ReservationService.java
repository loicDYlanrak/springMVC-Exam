package com.exemple.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemple.model.Adherent;
import com.exemple.model.Exemplaire;
import com.exemple.model.Reservation;
import com.exemple.repository.AbonnementRepository;
import com.exemple.repository.AdherentRepository;
import com.exemple.repository.ExemplaireRepository;
import com.exemple.repository.PenaliteRepository;
import com.exemple.repository.ReservationRepository;

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
    
    public List<Reservation> getReservationsByExemplaire(int idExemplaire) {
        return reservationRepository.findByExemplaireIdOrderByDateReservationDesc(idExemplaire);
    }
    
    @Transactional
    public String reserverExemplaire(int idExemplaire, int idAdherent, LocalDateTime dateReservation) {
        // Récupération des entités complètes
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
            
        // Vérification réservation existante (utilise maintenant les objets)
        if (reservationRepository.existsByAdherentAndExemplaire(adherent, exemplaire)) {
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