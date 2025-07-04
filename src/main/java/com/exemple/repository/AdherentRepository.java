package com.exemple.repository;

import com.exemple.model.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    Adherent findByEmail(String email);
    boolean existsByEmail(String email);
}