package com.exemple.repository;

import com.exemple.model.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    @Query(value = "SELECT * FROM exemplaire WHERE id_livre = ?1", nativeQuery = true)
    List<Exemplaire> findByLivreIdNative(Integer idLivre);
}