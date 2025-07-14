package com.exemple.repository;

import com.exemple.model.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
    boolean existsByDateFerie(LocalDate date);
}
