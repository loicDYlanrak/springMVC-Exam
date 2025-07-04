package com.exemple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemple.model.Adherent;
import com.exemple.model.Exemplaire;
import com.exemple.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    boolean existsByExemplaireAndAdherent(Exemplaire exemplaire, Adherent adherent);
    
    boolean existsByExemplaireAndAdherentNot(Exemplaire exemplaire, Adherent adherent);
    
    int countByAdherent(Adherent adherent);
    
    void deleteByExemplaireAndAdherent(Exemplaire exemplaire, Adherent adherent);
    boolean existsByExemplaire(Exemplaire exemplaire);
}