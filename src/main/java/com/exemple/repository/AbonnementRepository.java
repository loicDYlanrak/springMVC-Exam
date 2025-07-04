package com.exemple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemple.model.Abonnement;
import com.exemple.model.Adherent;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    
    List<Abonnement> findByAdherent(Adherent adherent);
}