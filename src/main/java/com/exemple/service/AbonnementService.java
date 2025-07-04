package com.exemple.service;

import com.exemple.model.*;
import com.exemple.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbonnementService {
    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    public Abonnement creerAbonnement(Integer idAdherent, String dateDebut, String dateFin) {
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("Aucun adhérent trouvé avec cet identifiant."));

        Abonnement abonnement = new Abonnement();
        abonnement.setAdherent(adherent);
        abonnement.setDateDebut(LocalDate.parse(dateDebut)); 
        abonnement.setDateFin(LocalDate.parse(dateFin));  

        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }

    public Abonnement getAbonnementById(Integer id) {
        return abonnementRepository.findById(id).orElse(null);
    }

    public Abonnement saveAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    public void deleteAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }
}