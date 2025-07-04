package com.exemple.repository;

import com.exemple.model.EtatExemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatExemplaireRepository extends JpaRepository<EtatExemplaire, Integer> {
}