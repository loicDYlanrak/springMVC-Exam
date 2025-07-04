package com.exemple.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "StatusExemplaire")
public class StatusExemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_status_exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_exemplaire")
    private Exemplaire exemplaire;
    
    private LocalDate date_changement;
    
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat")
    private EtatExemplaire etat;
    
    @ManyToOne
    @JoinColumn(name = "id_biblio")
    private Bibliothecaire bibliothecaire;
    
    public EtatExemplaire getEtat() {
        return etat;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }
    public LocalDate getDate_changement() {
        return date_changement;
    }
    
    public Integer getId_status_exemplaire() {
        return id_status_exemplaire;
    }
    public Bibliothecaire getBibliothecaire() {
        return bibliothecaire;
    }

    public void setBibliothecaire(Bibliothecaire bibliothecaire) {
        this.bibliothecaire = bibliothecaire;
    }

    public void setEtat(EtatExemplaire etat) {
        this.etat = etat;
    }

    public void setDate_changement(LocalDate date_changement) {
        this.date_changement = date_changement;
    }

    public void setId_status_exemplaire(Integer id_status_exemplaire) {
        this.id_status_exemplaire = id_status_exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }
}