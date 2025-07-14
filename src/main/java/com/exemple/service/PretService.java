package com.exemple.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemple.model.Abonnement;
import com.exemple.model.Adherent;
import com.exemple.model.Bibliothecaire;
import com.exemple.model.EtatExemplaire;
import com.exemple.model.Exemplaire;
import com.exemple.model.Livre;
import com.exemple.model.Penalite;
import com.exemple.model.Pret;
import com.exemple.model.Prolongement;
import com.exemple.model.RegleRetour;
import com.exemple.model.StatusExemplaire; 
import com.exemple.model.TypeAdherent;
import com.exemple.model.TypePret;
import com.exemple.repository.AbonnementRepository;
import com.exemple.repository.AdherentRepository;
import com.exemple.repository.EtatExemplaireRepository;
import com.exemple.repository.ExemplaireRepository;
import com.exemple.repository.PenaliteRepository;
import com.exemple.repository.PretRepository;
import com.exemple.repository.ProlongementRepository;
import com.exemple.repository.StatusExemplaireRepository;
import com.exemple.repository.TypePretRepository;
import com.exemple.repository.JourOuvrableRepository;
import com.exemple.repository.JourFerieRepository;
import com.exemple.repository.RegleRetourRepository;

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
    
    @Autowired
    private JourOuvrableRepository jourOuvrableRepository;

    @Autowired
    private JourFerieRepository jourFerieRepository;

    @Autowired
    private RegleRetourRepository regleRetourRepository;

    public List<Pret> getHistoriquePretsByExemplaire(int idExemplaire) {
        return pretRepository.findByExemplaireIdOrderByDatePretDesc(idExemplaire);
    }

    private LocalDate calculateValidReturnDate(LocalDate date) {
        while (true) {
            boolean isOuvrable = jourOuvrableRepository.existsByJourSemaine(date.getDayOfWeek().getValue());
            boolean isFerie = jourFerieRepository.existsByDateFerie(date);

            if (isOuvrable && !isFerie) {
                return date;
            }

            RegleRetour regle = regleRetourRepository.findFirst()
                    .orElseThrow(() -> new RuntimeException("Règle de retour non configurée"));

            if ("AVANT".equals(regle.getAction())) {
                date = date.minusDays(1);
            } else if ("APRES".equals(regle.getAction())) {
                date = date.plusDays(1);
            } else {
                throw new RuntimeException("Action de règle de retour inconnue");
            }
        }
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
        for (Penalite penalite : penalites) {
            Pret penalitePret = penalite.getPret();
            LocalDate penaliteEndDate = penalitePret.getDate_retour()
                    .plusDays(penalitePret.getAdherent().getTypeAdherent().getDuree_penalite());
            if (!datePret.isAfter(penaliteEndDate)) {
                return "Pénalités en cours, prêt impossible jusqu'au " + penaliteEndDate;
            }
        }

        List<Pret> pretsEnCours = pretRepository.findPretsEnCoursByExemplaire(idExemplaire);
        for (Pret pret : pretsEnCours) {
            LocalDate dateLimite = pret.getDate_pret().plusDays(
                prolongementRepository.existsByPret(pret.getId_pret()) 
                    ? pret.getAdherent().getTypeAdherent().getNb_jour_max_prologement()
                    : pret.getAdherent().getTypeAdherent().getDuree_pret()
            );
            if (datePret.isBefore(dateLimite)) {
                return "Date de prêt : " + pret.getDate_pret() + ", exemplaire déjà prêté jusqu'au " + dateLimite;
            }
        }

        Livre livre = exemplaire.getLivre();
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
        
        
        EtatExemplaire etat = etatExemplaireRepository.findByLibelle("prete")
                .orElseThrow(() -> new RuntimeException("État exemplaire non trouvé"));
        status.setEtat(etat);
        

        Bibliothecaire biblio = new Bibliothecaire();
        biblio.setId_biblio(1);
        status.setBibliothecaire(biblio);
        
        statusExemplaireRepository.save(status);

        return "Prêt effectué avec succès.";
    }

    @Transactional
    public String prolongerPret(int idPret, LocalDate dateRetour) {
        Pret pret = pretRepository.findById(idPret)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));

        List<Penalite> penalites = penaliteRepository.findActivePenalitesByAdherent(pret.getAdherent().getId_adherent());
        for (Penalite penalite : penalites) {
            Pret penalitePret = penalite.getPret();
            LocalDate penaliteEndDate = penalitePret.getDate_retour()
                    .plusDays(penalitePret.getAdherent().getTypeAdherent().getDuree_penalite());
            if (!dateRetour.isAfter(penaliteEndDate)) {
                return "Pénalités en cours, prolongement impossible.";
            }
        }

        LocalDate dateLimite = calculateValidReturnDate(
            pret.getDate_pret().plusDays(
                prolongementRepository.existsByPret(idPret)
                    ? pret.getAdherent().getTypeAdherent().getNb_jour_max_prologement()
                    : pret.getAdherent().getTypeAdherent().getDuree_pret()
            )
        );

        if (dateRetour.isAfter(dateLimite)) {
            return "Prolongement impossible : la date de retour est dépassée.";
        }

        pret.setDate_retour(dateRetour);
        pretRepository.save(pret);

        Pret newPret = new Pret();
        newPret.setExemplaire(pret.getExemplaire());
        newPret.setAdherent(pret.getAdherent());
        newPret.setDate_pret(dateRetour);
        newPret.setTypePret(pret.getTypePret());
        pretRepository.save(newPret);

        Prolongement prolongement = new Prolongement();
        prolongement.setPret(newPret);
        prolongementRepository.save(prolongement);

        return "Prolongement effectué avec succès.";
    }

    public List<Pret> getAllPrets() {
        return pretRepository.findAll();
    }

    public Pret getPretById(int idPret) {
        return pretRepository.findById(idPret)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));
    }
    
    
    public List<Pret> getPretsEnCoursByExemplaire(int idExemplaire) {
        return pretRepository.findPretsEnCoursByExemplaire(idExemplaire);
    }

    @Transactional
    public String retournerExemplaireParPret(int idPret, LocalDate dateRetour) {
        Pret pret = pretRepository.findById(idPret)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));

        pret.setDate_retour(dateRetour);

        LocalDate dateLimite = calculateValidReturnDate(
            pret.getDate_pret().plusDays(
                prolongementRepository.existsByPret(idPret)
                    ? pret.getAdherent().getTypeAdherent().getNb_jour_max_prologement()
                    : pret.getAdherent().getTypeAdherent().getDuree_pret()
            )
        );

        if (dateRetour.isAfter(dateLimite)) {
            Penalite penalite = new Penalite();
            penalite.setPret(pret);
            penaliteRepository.save(penalite);
            return "Retour enregistré avec pénalité pour retard.";
        }

        return "Retour enregistré.";
    }

}