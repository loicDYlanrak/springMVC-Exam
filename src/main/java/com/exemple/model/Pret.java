package com.exemple.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Pret")
public class Pret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pret;
    
    @ManyToOne
    @JoinColumn(name = "id_exemplaire")
    private Exemplaire exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;
    
    @ManyToOne
    @JoinColumn(name = "id_type_pret")
    private TypePret typePret;
    
    private LocalDate date_pret;
    private LocalDate date_retour;
    
    public Adherent getAdherent() {
        return adherent;
    }

    public LocalDate getDate_pret() {
        return date_pret;
    }

    public LocalDate getDate_retour() {
        return date_retour;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public int getId_pret() {
        return id_pret;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    public void setId_pret(int id_pret) {
        this.id_pret = id_pret;
    }

    public void setDate_pret(LocalDate date_pret) {
        this.date_pret = date_pret;
    }

    public void setDate_retour(LocalDate date_retour) {
        this.date_retour = date_retour;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }  
}