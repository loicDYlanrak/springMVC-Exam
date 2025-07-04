package com.exemple.repository;

import com.exemple.model.Exemplaire;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    @EntityGraph(attributePaths = {"statusExemplaires", "statusExemplaires.etat"})
    @Query("SELECT e FROM Exemplaire e WHERE e.livre.id_livre = :idLivre")
    List<Exemplaire> findByLivreId(@Param("idLivre") Integer idLivre);

    @Query("SELECT e FROM Exemplaire e LEFT JOIN FETCH e.statusExemplaires WHERE e.id_exemplaire = :id")
    Optional<Exemplaire> findByIdWithStatus(@Param("id") int id);  

    @EntityGraph(attributePaths = {"statusExemplaires.etat", "livre"})
    @Query("SELECT e FROM Exemplaire e WHERE e.id_exemplaire = :id")
    Optional<Exemplaire> findByIdWithFullDetails(@Param("id") int id);

    @SuppressWarnings("null")
    @EntityGraph(attributePaths = {"statusExemplaires", "statusExemplaires.etat"})
    @Override
    List<Exemplaire> findAll();
}