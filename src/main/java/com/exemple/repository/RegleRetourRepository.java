package com.exemple.repository;

import com.exemple.model.RegleRetour;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegleRetourRepository extends JpaRepository<RegleRetour, Integer> {

    Optional<RegleRetour> findFirst();
}
