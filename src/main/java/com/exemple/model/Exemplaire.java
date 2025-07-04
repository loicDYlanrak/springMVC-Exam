package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "Exemplaire")
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @OneToMany(mappedBy = "exemplaire", fetch=FetchType.LAZY)
    @OrderBy("date_changement DESC") // Order by date descending to easily get the latest
    @SuppressWarnings("FieldMayBeFinal")
    private List<StatusExemplaire> statusExemplaires = new ArrayList<>();

    // Getters and Setters
    public Integer getId_exemplaire() {
        return id_exemplaire;
    }

    public void setId_exemplaire(Integer id_exemplaire) {
        this.id_exemplaire = id_exemplaire;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    // Getter for statusExemplaires
    public List<StatusExemplaire> getStatusExemplaires() {
        return statusExemplaires;
    }
    
    // Get current status (convenience method)
    public StatusExemplaire getCurrentStatus() {
        if (statusExemplaires == null || statusExemplaires.isEmpty()) {
            return null;
        }
        return statusExemplaires.get(0); // Because we ordered by date descending
    }
}