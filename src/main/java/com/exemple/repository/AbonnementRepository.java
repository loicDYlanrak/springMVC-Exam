package com.exemple.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.model.Abonnement;
import com.exemple.model.Adherent;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    
    List<Abonnement> findByAdherent(Adherent adherent);
        @Query("SELECT a FROM Abonnement a WHERE a.adherent.id_adherent = :idAdherent " +
           "AND a.dateDebut <= :currentDate AND a.dateFin >= :currentDate")
    Optional<Abonnement> findActiveByAdherent(@Param("idAdherent") int idAdherent, 
                                            @Param("currentDate") LocalDate currentDate);
}