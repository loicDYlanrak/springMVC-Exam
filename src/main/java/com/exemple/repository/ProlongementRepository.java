package com.exemple.repository;

import com.exemple.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    boolean existsByPret(int idPret);
    @Query(value = "SELECT COUNT(*) FROM Prologement p JOIN Pret pr ON p.id_pret = pr.id_pret WHERE pr.id_adherent = :adherentId", nativeQuery = true)
    long countByAdherent(@Param("adherentId") int adherentId);
}