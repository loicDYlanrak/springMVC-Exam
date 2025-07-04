package com.exemple.repository;

import com.exemple.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusExemplaireRepository extends JpaRepository<StatusExemplaire, Integer> {
}