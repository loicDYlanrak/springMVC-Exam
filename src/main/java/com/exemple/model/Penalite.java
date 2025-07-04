package com.exemple.model;

import javax.persistence.*;

@Entity
@Table(name = "Penalite")
public class Penalite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_penalite;
    
    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;
    
    
    // Getters et Setters
    public Integer getId_penalite() {
        return id_penalite;
    }

    public void setId_penalite(Integer id_penalite) {
        this.id_penalite = id_penalite;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }
}