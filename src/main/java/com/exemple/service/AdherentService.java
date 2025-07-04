package com.exemple.service;

import com.exemple.model.Abonnement;
import com.exemple.model.Adherent;
import com.exemple.repository.AbonnementRepository;
import com.exemple.repository.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
    }

    public Adherent getAdherentById(Integer id) {
        return adherentRepository.findById(id).orElse(null);
    }

    public Adherent saveAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public void deleteAdherent(Integer id) {
        adherentRepository.deleteById(id);
    }

    public Adherent findByEmail(String email) {
        return adherentRepository.findByEmail(email);
    }

    public Abonnement getAbonnement(int idAdherent) {
        Optional<Adherent> adherentOpt = adherentRepository.findById(idAdherent);
        if (!adherentOpt.isPresent()) {
            return null;
        }

        Adherent adherent = adherentOpt.get();
        List<Abonnement> abonnements = abonnementRepository.findByAdherent(adherent);        
        return abonnements.stream()
                .max(Comparator.comparing(Abonnement::getDateFin))
                .orElse(null);
    }
    


} 