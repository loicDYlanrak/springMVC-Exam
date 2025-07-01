package com.exemple.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Adherent")
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_adherent;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String pwd;
    
    @Column(nullable = false, name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    @ManyToOne
    @JoinColumn(name = "id_type_adherent", nullable = false)
    private TypeAdherent typeAdherent;

    // Getters and Setters
    public Integer getId_adherent() {
        return id_adherent;
    }

    public void setId_adherent(Integer id_adherent) {
        this.id_adherent = id_adherent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public TypeAdherent getTypeAdherent() {
        return typeAdherent;
    }

    public void setTypeAdherent(TypeAdherent typeAdherent) {
        this.typeAdherent = typeAdherent;
    }
}