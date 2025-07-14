package com.exemple.repository;

import com.exemple.model.RegleRetour;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegleRetourRepository extends JpaRepository<RegleRetour, Integer> {

    @Query("SELECT r FROM RegleRetour r ORDER BY r.id_regle ASC")
    Optional<RegleRetour> findFirst();
}
