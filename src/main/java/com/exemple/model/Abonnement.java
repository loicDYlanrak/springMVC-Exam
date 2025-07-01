package com.exemple.model;

import javax.persistence.*;
import java.util.Date;

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
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(nullable = false, name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // Getters and Setters
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}