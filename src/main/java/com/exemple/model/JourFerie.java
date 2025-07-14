package com.exemple.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_jour_ferie;

    private LocalDate dateFerie;

    private String nom;

    public LocalDate getDateFerie() {
        return dateFerie;
    }

    public void setDateFerie(LocalDate dateFerie) {
        this.dateFerie = dateFerie;
    }

    public int getIdJourFerie() {
        return id_jour_ferie;
    }

    public void setIdJourFerie(int id_jour_ferie) {
        this.id_jour_ferie = id_jour_ferie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
}
