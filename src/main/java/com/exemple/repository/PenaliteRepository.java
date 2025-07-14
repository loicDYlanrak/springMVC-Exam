package com.exemple.repository;

import com.exemple.model.Penalite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {

    @Query("SELECT p FROM Penalite p WHERE p.pret.adherent.id_adherent = :idAdherent " +
       "AND (p.pret.date_retour IS NULL OR p.pret.date_retour > CURRENT_DATE)")
    List<Penalite> findActivePenalitesByAdherent(@Param("idAdherent") int idAdherent);

    @Query("SELECT p.pret.adherent.nom, COUNT(p) AS nombrePenalites " +
           "FROM Penalite p " +
           "GROUP BY p.pret.adherent.nom " +
           "ORDER BY nombrePenalites DESC")
    List<Object[]> findPenalitesStatistiques();
}
