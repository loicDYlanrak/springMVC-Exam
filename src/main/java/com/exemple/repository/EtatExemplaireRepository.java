package com.exemple.repository;

import com.exemple.model.EtatExemplaire;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatExemplaireRepository extends JpaRepository<EtatExemplaire, Integer> {
    Optional<EtatExemplaire> findByLibelle(String libelle);
}