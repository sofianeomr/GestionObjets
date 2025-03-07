package org.gestionobjets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String nom;

    // Constructeur par défaut
    public Categorie() {}

    // Constructeur avec nom
    public Categorie(String nom) {
        this.nom = nom;
    }

    // Constructeur avec ID
    public Categorie(int id) {
        this.id = id;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
