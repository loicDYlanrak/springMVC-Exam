package com.exemple.repository;

import com.exemple.model.TypePret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePretRepository extends JpaRepository<TypePret, Integer> {
    
    Optional<TypePret> findByLibelle(String libelle);
}