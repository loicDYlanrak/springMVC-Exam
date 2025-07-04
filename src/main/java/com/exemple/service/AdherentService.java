package com.exemple.service;

import com.exemple.model.*;
import com.exemple.repository.*;

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

    @Autowired
    private TypeAdherentRepository typeAdherentRepository;

    public Adherent inscrireAdherent(String nom, String email, String pwd, String dateNaissance, Integer idTypeAdherent) {
        if (adherentRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre adhérent.");
        }

        TypeAdherent typeAdherent = typeAdherentRepository.findById(idTypeAdherent)
                .orElseThrow(() -> new RuntimeException("Type d'adhérent non trouvé"));

        Adherent adherent = new Adherent();
        adherent.setNom(nom);
        adherent.setEmail(email);
        adherent.setPwd(pwd);
        adherent.setDateNaissance(java.sql.Date.valueOf(dateNaissance));
        adherent.setTypeAdherent(typeAdherent);

        return adherentRepository.save(adherent);
    }

    public Adherent getAdherentById(Integer id) {
        return adherentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun adhérent trouvé avec cet identifiant."));
    }

    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
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