package com.exemple.service;

import com.exemple.model.Adherent;
import com.exemple.repository.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
    }

    public Adherent getAdherentById(Integer id) {
        return adherentRepository.findById(id).orElse(null);
    }

    public Adherent saveAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public void deleteAdherent(Integer id) {
        adherentRepository.deleteById(id);
    }

    public Adherent findByEmail(String email) {
        return adherentRepository.findByEmail(email);
    }
}