package com.exemple.service;

import com.exemple.model.Bibliothecaire;
import com.exemple.repository.BibliothecaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliothecaireService {
    @Autowired
    private BibliothecaireRepository bibliothecaireRepository;

    public List<Bibliothecaire> getAllBibliothecaires() {
        return bibliothecaireRepository.findAll();
    }

    public Bibliothecaire getBibliothecaireById(Integer id) {
        return bibliothecaireRepository.findById(id).orElse(null);
    }

    public Bibliothecaire saveBibliothecaire(Bibliothecaire bibliothecaire) {
        return bibliothecaireRepository.save(bibliothecaire);
    }

    public void deleteBibliothecaire(Integer id) {
        bibliothecaireRepository.deleteById(id);
    }

    public Bibliothecaire getBibliothecaireByNom(String nom) {
        return bibliothecaireRepository.findByNom(nom);
    }
}