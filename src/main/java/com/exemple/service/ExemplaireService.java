package com.exemple.service;

import com.exemple.model.Exemplaire;
import com.exemple.repository.ExemplaireRepository;
import com.exemple.repository.PretRepository;
import com.exemple.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;
         
    @Autowired
    private PretRepository pretRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Exemplaire> getAllExemplaires() {
        return exemplaireRepository.findAll();
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

    public boolean isDisponible(int idExemplaire) {
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire).orElse(null);
        if (exemplaire == null) {
            return false;
        }

        // Vérifier les prêts en cours
        if (pretRepository.existsByExemplaireAndDateRetourIsNull(exemplaire)) {
            return false;
        }
        
        // Vérifier les réservations
        return !reservationRepository.existsByExemplaire(exemplaire);
    }

    public List<Exemplaire> findDisponiblesByLivre(int idLivre) {
        List<Exemplaire> exemplaires = exemplaireRepository.findByLivreIdNative(idLivre);
        
        return exemplaires.stream()
                .filter(exemplaire -> isDisponible(exemplaire.getId_exemplaire()))
                .collect(Collectors.toList());
    }

    
} 