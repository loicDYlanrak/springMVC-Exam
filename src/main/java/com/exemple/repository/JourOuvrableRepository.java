package com.exemple.repository;

import com.exemple.model.JourOuvrable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourOuvrableRepository extends JpaRepository<JourOuvrable, Integer> {
    boolean existsByJourSemaine(int jourSemaine);
}
