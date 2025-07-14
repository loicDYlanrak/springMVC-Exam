package com.exemple.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    private EtatExemplaireRepository etatExemplaireRepository;

        @Autowired
    private StatusExemplaireRepository statusExemplaireRepository;
        
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

    public List<Reservation> getFilteredReservations(String search, String filter) {
        // Implement logic to filter reservations based on search and filter criteria
        return reservationRepository.findFilteredReservations(search, filter);
    }

    @Transactional
    public String validerReservation(int idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        if (reservation.getValide() != null && reservation.getValide()) {
            return "La réservation est déjà validée.";
        }

        reservation.setValide(true);
        reservationRepository.save(reservation);

        return "Réservation validée avec succès.";
    }

    public Reservation getReservationById(int idReservation) {
        return reservationRepository.findById(idReservation)
            .orElseThrow(() -> new RuntimeException("Réservation non trouvée avec l'ID: " + idReservation));
    }


    @Transactional
    public String annulerReservation(int idReservation) {
        try {
            Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
            
            // Supprimer la réservation
            reservationRepository.delete(reservation);
            
            // Mettre à jour le statut de l'exemplaire
            Exemplaire exemplaire = reservation.getExemplaire();
            StatusExemplaire newStatus = new StatusExemplaire();
            newStatus.setExemplaire(exemplaire);
            newStatus.setDate_changement(LocalDate.now());
            
            EtatExemplaire etatDisponible = etatExemplaireRepository.findByLibelle("disponible")
                .orElseThrow(() -> new RuntimeException("État 'disponible' non trouvé"));
            
            newStatus.setEtat(etatDisponible);
            statusExemplaireRepository.save(newStatus);
            
            return "Réservation annulée avec succès";
        } catch (Exception e) {
            return "Erreur lors de l'annulation de la réservation: " + e.getMessage();
        }
    }

    public Optional<Reservation> getActiveReservationForExemplaire(int idExemplaire) {
        List<Reservation> reservations = reservationRepository.findLatestReservationForExemplaire(idExemplaire);
        return reservations.isEmpty() ? Optional.empty() : Optional.of(reservations.get(0));
    }
}