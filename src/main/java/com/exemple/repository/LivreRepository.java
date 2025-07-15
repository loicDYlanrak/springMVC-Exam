package com.exemple.repository;

import com.exemple.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    @Query("SELECT l FROM Livre l WHERE "
         + "(:search IS NULL OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(l.auteur) LIKE LOWER(CONCAT('%', :search, '%'))) "
         + "AND (:ageMinimum IS NULL OR l.age_minimum >= :ageMinimum) "
         + "AND (:anneePublication IS NULL OR l.annee_publication = :anneePublication)")
    List<Livre> findByFilters(@Param("search") String search,
                              @Param("ageMinimum") Integer ageMinimum,
                              @Param("anneePublication") Integer anneePublication);

    List<Livre> findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(String titre, String auteur);
}