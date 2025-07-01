package com.exemple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Livre")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_livre;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(nullable = false)
    private String auteur;
    
    @Column(nullable = false)
    private Integer age_minimum;
    
    private Integer annee_publication;

    // Getters and Setters
    public Integer getId_livre() {
        return id_livre;
    }

    public void setId_livre(Integer id_livre) {
        this.id_livre = id_livre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Integer getAge_minimum() {
        return age_minimum;
    }

    public void setAge_minimum(Integer age_minimum) {
        this.age_minimum = age_minimum;
    }

    public Integer getAnnee_publication() {
        return annee_publication;
    }

    public void setAnnee_publication(Integer annee_publication) {
        this.annee_publication = annee_publication;
    }
}