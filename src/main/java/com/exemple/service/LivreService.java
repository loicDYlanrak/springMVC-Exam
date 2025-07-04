package com.exemple.service;

import com.exemple.model.Livre;
import com.exemple.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Livre getLivreById(Integer id) {
    
        return livreRepository.findById(id).orElse(null);
    }

    public Livre saveLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public void deleteLivre(Integer id) {
        livreRepository.deleteById(id);
    }
}