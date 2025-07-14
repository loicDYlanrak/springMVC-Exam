package com.exemple.model;

import javax.persistence.*;

@Entity
public class RegleRetour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_regle;

    private String description;

    private String action;

    private int joursDecallage;

    public int getIdRegle() {
        return id_regle;
    }
    public void setIdRegle(int id_regle) {
        this.id_regle = id_regle;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public int getJoursDecallage() {
        return joursDecallage;
    }
    public void setJoursDecallage(int joursDecallage) {
        this.joursDecallage = joursDecallage;
    }
    @Override
    public String toString() {
        return "RegleRetour{" +
                "id_regle=" + id_regle +
                ", description='" + description + '\'' +
                ", action='" + action + '\'' +
                ", joursDecallage=" + joursDecallage +
                '}';
    }
    
}
