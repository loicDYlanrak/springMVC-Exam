package com.exemple.model;

import javax.persistence.*;

@Entity
public class JourOuvrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_jour_ouvrable;

    private int jourSemaine;

    public int getIdJourOuvrable() {
        return id_jour_ouvrable;
    }
    public void setIdJourOuvrable(int id_jour_ouvrable) {
        this.id_jour_ouvrable = id_jour_ouvrable;
    }
    public int getJourSemaine() {
        return jourSemaine;
    }
    public void setJourSemaine(int jourSemaine) {
        this.jourSemaine = jourSemaine;
    }
    
}
