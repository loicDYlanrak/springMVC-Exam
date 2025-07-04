package com.exemple.service;

import com.exemple.model.*;
import com.exemple.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;
    
    @Autowired
    private AdherentRepository adherentRepository;
    
    @Autowired
    private ExemplaireRepository exemplaireRepository; 

    @Autowired
    private EtatExemplaireRepository etatExemplaireRepository;

    @Autowired
    private StatusExemplaireRepository statusExemplaireRepository;
    
    @Autowired
    private AbonnementRepository abonnementRepository;
    
    @Autowired
    private PenaliteRepository penaliteRepository;
    
    @Autowired
    private TypePretRepository typePretRepository;

    @Autowired
    private ProlongementRepository prolongementRepository; 

    public List<Pret> getHistoriquePretsByExemplaire(int idExemplaire) {
        return pretRepository.findByExemplaireIdOrderByDatePretDesc(idExemplaire);
    }

    @Transactional
    public String emprunterExemplaire(int idExemplaire, int idAdherent, LocalDate datePret) {
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("Adhérent non trouvé"));
        
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
                .orElseThrow(() -> new RuntimeException("Exemplaire non trouvé"));
        
        Optional<Abonnement> abonnementOpt = abonnementRepository.findActiveByAdherent(idAdherent, LocalDate.now());
        if (abonnementOpt.isEmpty()) {
            return "Adhérent suspendu ou non renouvelé.";
        }
        
        List<Penalite> penalites = penaliteRepository.findActivePenalitesByAdherent(idAdherent);
        if (!penalites.isEmpty()) { 
            return "Pénalités en cours, prêt impossible.";
        }
        
        if (pretRepository.existsByExemplaireAndDateRetourIsNull(idExemplaire)) {
            return "Exemplaire déjà prêté.";
        }
        
        Livre livre = exemplaire.getLivre();
        @SuppressWarnings("deprecation")
        int ageAdherent = LocalDate.now().getYear() - adherent.getDateNaissance().getYear();
        if (ageAdherent < livre.getAge_minimum()) {
            return "L'âge de l'adhérent ne correspond pas à l'âge minimum requis pour ce livre.";
        }
        
        TypeAdherent typeAdherent = adherent.getTypeAdherent();
        long nbPretsActifs = pretRepository.countActivePretsByAdherent(idAdherent);
        if (nbPretsActifs >= typeAdherent.getQuota()) {
            return "Limite de prêts atteinte.";
        }
        
        Pret pret = new Pret();
        pret.setExemplaire(exemplaire);
        pret.setAdherent(adherent);
        pret.setDate_pret(datePret);
        
        TypePret typePret = typePretRepository.findByLibelle("Lecture a domicile")
                .orElseThrow(() -> new RuntimeException("Type de prêt non trouvé"));
        pret.setTypePret(typePret);
        
        pretRepository.save(pret);
        
        
        StatusExemplaire status = new StatusExemplaire();
        status.setExemplaire(exemplaire);
        status.setDate_changement(datePret);
        
        // On suppose que l'état "prete" a l'id 2 (selon votre INSERT)
        EtatExemplaire etat = etatExemplaireRepository.findByLibelle("prete")
                .orElseThrow(() -> new RuntimeException("État exemplaire non trouvé"));
        status.setEtat(etat);
        
        // On utilise l'ID 1 pour le bibliothécaire comme spécifié
        Bibliothecaire biblio = new Bibliothecaire();
        biblio.setId_biblio(1);
        status.setBibliothecaire(biblio);
        
        statusExemplaireRepository.save(status);

        return "Prêt effectué avec succès.";
    }

    @Transactional
    public String retournerExemplaire(int idExemplaire, LocalDate dateRetour) {
        Pret pret = pretRepository.findByExemplaireAndDateRetourIsNull(idExemplaire)
                .orElseThrow(() -> new RuntimeException("Aucun prêt en cours pour cet exemplaire"));
        
        pret.setDate_retour(dateRetour);

        StatusExemplaire status = new StatusExemplaire();
        status.setExemplaire(pret.getExemplaire());
        status.setDate_changement(dateRetour);
        
        EtatExemplaire etat = etatExemplaireRepository.findByLibelle("disponible")
                .orElseThrow(() -> new RuntimeException("État exemplaire non trouvé"));
        status.setEtat(etat);
        
        Bibliothecaire biblio = new Bibliothecaire();
        biblio.setId_biblio(1);
        status.setBibliothecaire(biblio);
        
        statusExemplaireRepository.save(status);
        
        // Vérification retard
        LocalDate dateLimite = pret.getDate_pret().plusDays(pret.getAdherent().getTypeAdherent().getDuree_pret());
        if (dateRetour.isAfter(dateLimite)) {
            Penalite penalite = new Penalite();
            penalite.setPret(pret);
            penaliteRepository.save(penalite);
            return "Retour enregistré avec pénalité pour retard";
        }
        
        return "Retour enregistré";
    }

    @Transactional
    public String prolongerPret(int idExemplaire) {
        Pret pret = pretRepository.findByExemplaireAndDateRetourIsNull(idExemplaire)
                .orElseThrow(() -> new RuntimeException("Aucun prêt en cours pour cet exemplaire"));
        
        // Vérification si déjà prolongé
        if (prolongementRepository.existsByPret(pret.getId_pret())) {
            return "Ce prêt a déjà été prolongé";
        }
        
        // Vérification limite prolongement
        TypeAdherent type = pret.getAdherent().getTypeAdherent();
        long nbProlongements = prolongementRepository.countByAdherent(pret.getAdherent().getId_adherent());
        if (nbProlongements >= type.getNb_jour_max_prologement()) {
            return "Limite de prolongement atteinte pour cet adhérent";
        }
        
        Prolongement prolongement = new Prolongement();
        prolongement.setPret(pret);
        prolongementRepository.save(prolongement);
        
        return "Prolongement effectué";
    }
}