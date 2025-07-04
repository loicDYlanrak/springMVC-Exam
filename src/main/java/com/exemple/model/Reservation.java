package com.exemple.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reservation;
    
    @ManyToOne
    @JoinColumn(name = "id_exemplaire")
    private Exemplaire exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;
    
    private LocalDateTime date_reservation;

    public LocalDateTime getDate_reservation() {
        return date_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setDate_reservation(LocalDateTime date_reservation) {
        this.date_reservation = date_reservation;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
    
}