package com.exemple.model;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Abonnement")
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_abonnement;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;
    
    @Column(nullable = false, name = "date_debut")
    private LocalDate dateDebut;
    
    @Column(nullable = false, name = "date_fin")
    private LocalDate dateFin;

    public Integer getId_abonnement() {
        return id_abonnement;
    }

    public void setId_abonnement(Integer id_abonnement) {
        this.id_abonnement = id_abonnement;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}