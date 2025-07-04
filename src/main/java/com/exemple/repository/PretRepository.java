package com.exemple.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.model.Adherent;
import com.exemple.model.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    
    @Query("SELECT p FROM Pret p WHERE p.adherent = :adherent AND p.date_retour IS NULL")
    List<Pret> findByAdherentAndDateRetourIsNull(@Param("adherent") Adherent adherent);

    @Query("SELECT COUNT(p) FROM Pret p WHERE p.adherent = :adherent AND p.date_retour IS NULL")
    int countByAdherentAndDateRetourIsNull(@Param("adherent") Adherent adherent);

    @Query("SELECT p FROM Pret p WHERE p.exemplaire.id_exemplaire = :idExemplaire AND p.date_retour IS NULL")
    Optional<Pret> findByExemplaireAndDateRetourIsNull(@Param("idExemplaire") int idExemplaire);
        
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
        "FROM Prolongement p WHERE p.pret = :pret")
    boolean existsProlongementForPret(@Param("pret") Pret pret);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
       "FROM Pret p WHERE p.exemplaire.id_exemplaire = :idExemplaire " +
       "AND p.date_retour IS NULL")
    boolean existsByExemplaireAndDateRetourIsNull(@Param("idExemplaire") int idExemplaire);

    @Query("SELECT COUNT(p) FROM Pret p WHERE p.adherent.id_adherent = :idAdherent " +
       "AND p.date_retour IS NULL")
    long countActivePretsByAdherent(@Param("idAdherent") int idAdherent);

    @Query("SELECT p FROM Pret p WHERE p.exemplaire.id_exemplaire = :idExemplaire ORDER BY p.date_pret DESC")
    List<Pret> findByExemplaireIdOrderByDatePretDesc(@Param("idExemplaire") int idExemplaire);
}