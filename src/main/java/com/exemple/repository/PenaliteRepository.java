package com.exemple.repository;

import com.exemple.model.Adherent;
import com.exemple.model.Penalite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    
    boolean existsByPretAdherentAndPayeeFalse(Adherent adherent);

    @Query("SELECT COUNT(p) > 0 FROM Penalite p WHERE p.pret.adherent = :adherent")
    boolean existsByAdherentAndPayeeFalse(Adherent adherent);

    @Query("SELECT p FROM Penalite p WHERE p.pret.adherent.id_adherent = :idAdherent " +
       "AND (p.pret.date_retour IS NULL OR p.pret.date_retour > CURRENT_DATE)")
    List<Penalite> findActivePenalitesByAdherent(@Param("idAdherent") int idAdherent);
}
