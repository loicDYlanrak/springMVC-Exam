package com.exemple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EtatExemplaire")
public class EtatExemplaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_etat;
    
    @Column(nullable = false)
    private String libelle;
    
    // Getters et Setters
    public Integer getId_etat() {
        return id_etat;
    }

    public void setId_etat(Integer id_etat) {
        this.id_etat = id_etat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}