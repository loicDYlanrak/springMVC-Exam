package com.exemple.service;

import com.exemple.model.Abonnement;
import com.exemple.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonnementService {
    @Autowired
    private AbonnementRepository abonnementRepository;

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