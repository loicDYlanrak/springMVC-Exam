package com.exemple.service;

import com.exemple.model.EtatExemplaire;
import com.exemple.repository.EtatExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatExemplaireService {

    @Autowired
    private EtatExemplaireRepository etatExemplaireRepository;
    
    public List<EtatExemplaire> findAll() {
        return etatExemplaireRepository.findAll();
    }
    
    public EtatExemplaire findById(Integer id) {
        return etatExemplaireRepository.findById(id).orElse(null);
    }

    
}