package com.exemple.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prologement") 
public class Prolongement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_prologement;
    
    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;
    
    public int getId_prologement() {
        return id_prologement;
    }

    public void setId_prologement(int id_prologement) {
        this.id_prologement = id_prologement;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }
}