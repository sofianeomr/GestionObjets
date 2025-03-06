package org.gestionobjets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "objets")
public class Objet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Utilisateur proprietaire;

    // Constructeur par défaut
    public Objet() {}
    public Objet(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = null;  // ou une valeur par défaut si nécessaire
        this.proprietaire = null;  // ou une valeur par défaut si nécessaire
    }

    // Constructeur avec nom, description, catégorie et propriétaire
    public Objet(String nom, String description, Categorie categorie, Utilisateur proprietaire) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.proprietaire = proprietaire;
    }

    // Constructeur avec id, nom, description, catégorie et propriétaire
    public Objet(int id, String nom, String description, Categorie categorie, Utilisateur proprietaire) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.proprietaire = proprietaire;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }
}
