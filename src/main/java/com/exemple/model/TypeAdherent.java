package com.exemple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TypeAdherent")
public class TypeAdherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_type_adherent;
    
    @Column(nullable = false)
    private String libelle;
    
    @Column(nullable = false)
    private Integer duree_pret;
    
    @Column(nullable = false)
    private Integer quota;
    
    @Column(nullable = false)
    private Integer nb_reservation_max;
    
    @Column(nullable = false)
    private Integer duree_penalite;
    
    @Column(nullable = false)
    private Integer nb_jour_max_prologement;

    // Getters and Setters
    public Integer getId_type_adherent() {
        return id_type_adherent;
    }

    public void setId_type_adherent(Integer id_type_adherent) {
        this.id_type_adherent = id_type_adherent;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getDuree_pret() {
        return duree_pret;
    }

    public void setDuree_pret(Integer duree_pret) {
        this.duree_pret = duree_pret;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getNb_reservation_max() {
        return nb_reservation_max;
    }

    public void setNb_reservation_max(Integer nb_reservation_max) {
        this.nb_reservation_max = nb_reservation_max;
    }

    public Integer getDuree_penalite() {
        return duree_penalite;
    }

    public void setDuree_penalite(Integer duree_penalite) {
        this.duree_penalite = duree_penalite;
    }

    public Integer getNb_jour_max_prologement() {
        return nb_jour_max_prologement;
    }

    public void setNb_jour_max_prologement(Integer nb_jour_max_prologement) {
        this.nb_jour_max_prologement = nb_jour_max_prologement;
    }
}