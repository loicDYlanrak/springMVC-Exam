package com.exemple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.model.Adherent;
import com.exemple.model.Exemplaire;
import com.exemple.model.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    
    @Query("SELECT p FROM Pret p WHERE p.adherent = :adherent AND p.date_retour IS NULL")
    List<Pret> findByAdherentAndDateRetourIsNull(@Param("adherent") Adherent adherent);

    @Query("SELECT COUNT(p) FROM Pret p WHERE p.adherent = :adherent AND p.date_retour IS NULL")
    int countByAdherentAndDateRetourIsNull(@Param("adherent") Adherent adherent);

    @Query("SELECT p FROM Pret p WHERE p.exemplaire.id_exemplaire = :idExemplaire AND p.date_retour IS NULL")
    Pret findByExemplaireIdAndDate_RetourIsNull(@Param("idExemplaire") int idExemplaire);
        
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
        "FROM Prolongement p WHERE p.pret = :pret")
    boolean existsProlongementForPret(@Param("pret") Pret pret);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pret p WHERE p.exemplaire = :exemplaire AND p.date_retour IS NULL")
    boolean existsByExemplaireAndDateRetourIsNull(@Param("exemplaire") Exemplaire exemplaire);
}