package org.gestionobjets.models;

public class Objet {
    private int id;
    private String nom;
    private String description;
    private String categorie;
    private Utilisateur proprietaire;

    public Objet() {}

    public Objet(int id, String nom, String description, String categorie, Utilisateur proprietaire) {
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
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public Utilisateur getProprietaire() {
        return proprietaire;
    }
    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }
}
