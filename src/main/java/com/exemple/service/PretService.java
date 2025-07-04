package com.exemple.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemple.model.Adherent;
import com.exemple.model.Exemplaire;
import com.exemple.model.Penalite;
import com.exemple.model.Pret;
import com.exemple.model.TypeAdherent;
import com.exemple.model.TypePret;
import com.exemple.repository.AdherentRepository;
import com.exemple.repository.ExemplaireRepository;
import com.exemple.repository.PenaliteRepository;
import com.exemple.repository.PretRepository;
import com.exemple.repository.ReservationRepository;
import com.exemple.repository.TypePretRepository;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;
    
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    
    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private TypePretRepository typePretRepository;

    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PenaliteRepository penaliteRepository;
    
    @Transactional
    public void processPret(int idExemplaire, int idAdherent, int idBibliothecaire) throws Exception {
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
            .orElseThrow(() -> new Exception("Exemplaire introuvable"));
        
        Adherent adherent = adherentRepository.findById(idAdherent)
            .orElseThrow(() -> new Exception("Adhérent introuvable"));
        
        if (!adherentService.isAdherentActif(idAdherent)) {
            throw new Exception("Adhérent suspendu ou non renouvelé");
        }
        
        // Vérifier que l'adhérent n'a pas atteint sa limite d'emprunt
        TypeAdherent type = adherent.getTypeAdherent();
        long nbPretsActifs = pretRepository.countByAdherentAndDateRetourIsNull(adherent);
        if (nbPretsActifs >= type.getNb_reservation_max()) {
            throw new Exception("Limite de prêts atteinte");
        }
        
        // Vérifier que l'adhérent n'a pas de pénalités bloquantes
        if (penaliteRepository.existsByAdherentAndPayeeFalse(adherent)) {
            throw new Exception("Pénalités en cours, prêt impossible");
        }
        
        // Vérifier que l'exemplaire est disponible
        if (!exemplaireService.isDisponible(idExemplaire)) {
            throw new Exception("Exemplaire déjà prêté ou réservé");
        }
        
        // Vérifier que l'exemplaire n'est pas réservé par un autre utilisateur
        if (reservationRepository.existsByExemplaireAndAdherentNot(exemplaire, adherent)) {
            throw new Exception("Exemplaire réservé par un autre utilisateur");
        }
        
        // Créer le prêt
        TypePret typePret1 = typePretRepository.findByLibelle("EMPRUNT")
        .orElseThrow(() -> new Exception("Type de prêt EMPRUNT introuvable"));
    
        Pret pret = new Pret();
        pret.setExemplaire(exemplaire);
        pret.setAdherent(adherent);
        pret.setTypePret(typePret1);
        pret.setDate_pret(LocalDate.now());
        pret.setDate_retour_prevue(LocalDate.now().plusDays(type.getDuree_pret()));
        
        pretRepository.save(pret);
            
        // Marquer l'exemplaire comme non disponible
        exemplaireRepository.save(exemplaire);
        
        // Supprimer la réservation si elle existe pour cet adhérent
        reservationRepository.deleteByExemplaireAndAdherent(exemplaire, adherent);
    }
    
    @Transactional
    public void processRetour(int idPret, int idBibliothecaire) throws Exception {
        Pret pret = pretRepository.findById(idPret)
            .orElseThrow(() -> new Exception("Prêt introuvable"));
        
        if (pret.getDate_retour() != null) {
            throw new Exception("Ce prêt a déjà été retourné");
        }
        
        // Enregistrer la date de retour
        pret.setDate_retour(LocalDate.now());
        pretRepository.save(pret);
        
        // Marquer l'exemplaire comme disponible
        Exemplaire exemplaire = pret.getExemplaire();
        exemplaireRepository.save(exemplaire);
        
        // Vérifier si le retour est en retard et créer une pénalité si nécessaire
        if (LocalDate.now().isAfter(pret.getDate_retour_prevue())) {
            Penalite penalite = new Penalite();
            penalite.setPret(pret);
            penalite.setMontant(calculatePenalite(pret));
            penalite.setPayee(false);
            penaliteRepository.save(penalite);
        }
    }
    
    @Transactional
    public void prolongerPret(int idPret) throws Exception {
        Pret pret = pretRepository.findById(idPret)
            .orElseThrow(() -> new Exception("Prêt introuvable"));
        
        if (pret.getDate_retour() != null) {
            throw new Exception("Ce prêt a déjà été retourné");
        }
        
        // Vérifier si le prêt a déjà été prolongé
        if (pretRepository.existsProlongementForPret(pret)) {
            throw new Exception("Ce prêt a déjà été prolongé");
        }
        
        TypeAdherent type = pret.getAdherent().getTypeAdherent();
        LocalDate nouvelleDate = pret.getDate_retour_prevue().plusDays(type.getDuree_pret());
        
        // Vérifier que la nouvelle date ne dépasse pas la durée max
        if (ChronoUnit.DAYS.between(pret.getDate_pret(), nouvelleDate) > type.getNb_jour_max_prologement()) {
            throw new Exception("Durée maximale de prêt atteinte");
        }
        
        // Enregistrer la prolongation
        pret.setDate_retour_prevue(nouvelleDate);
        pretRepository.save(pret);
    }
    
    public Pret findPretActifByExemplaire(int idExemplaire) {
        return pretRepository.findByExemplaireIdAndDate_RetourIsNull(idExemplaire);
    }
    
    public LocalDate calculateNewDateRetour(Pret pret) {
        return pret.getDate_retour_prevue().plusDays(pret.getAdherent().getTypeAdherent().getDuree_pret());
    }
    
    private double calculatePenalite(Pret pret) {
        long joursRetard = ChronoUnit.DAYS.between(pret.getDate_retour_prevue(), LocalDate.now());
        return joursRetard * 0.50; // 0.50€ par jour de retard
    }

    public Pret getPretById(Integer id) {
        return pretRepository.findById(id).orElse(null);
    }
}