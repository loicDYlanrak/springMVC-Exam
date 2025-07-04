package com.exemple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TypePret")
public class TypePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_type_pret;
    
    @Column(nullable = false)
    private String libelle;


    public Integer getId_type_pret() {
        return id_type_pret;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setId_type_pret(Integer id_type_pret) {
        this.id_type_pret = id_type_pret;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
}
