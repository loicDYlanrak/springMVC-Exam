package com.exemple.service;

import com.exemple.model.Exemplaire;
import com.exemple.repository.ExemplaireRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;
         
    public List<Exemplaire> getAllExemplaires() {
        return exemplaireRepository.findAll(); // This will now use the EntityGraph
    }

    public Exemplaire getExemplaireById(Integer id) {
        return exemplaireRepository.findById(id).orElse(null);
    }

    public Exemplaire saveExemplaire(Exemplaire exemplaire) {
        return exemplaireRepository.save(exemplaire);
    }

    public void deleteExemplaire(Integer id) {
        exemplaireRepository.deleteById(id);
    }
    
    public List<Exemplaire> getExemplairesByLivreId(Integer livreId) {
        return exemplaireRepository.findByLivreId(livreId); // This will now use the EntityGraph
    }
}