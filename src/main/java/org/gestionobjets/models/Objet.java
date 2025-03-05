package org.gestionobjets.models;

public class Objet {
    private int id;
    private String nom;
    private String description;
    private String categorie;
    private Utilisateur proprietaire;
    private int proprietaireId; // Ajoutez cet attribut si non existan


    public Objet() {}

    public Objet(int id, String nom, String description, String categorie, Utilisateur proprietaire, int proprietaireId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.proprietaire = proprietaire;
        this.proprietaireId = proprietaireId;
    }

    public Objet(String nom, String categorie, String description, int proprietaireId) {
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.proprietaireId = proprietaireId;
    }

    // Constructeur sans propriétaire (seulement proprietaireId)
    public Objet(int id, String nom, String description, String categorie, int proprietaireId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.proprietaireId = proprietaireId;
    }

    // Constructeur avec un objet Utilisateur comme propriétaire
    public Objet(int id, String nom, String description, String categorie, Utilisateur proprietaire) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.proprietaire = proprietaire;
        this.proprietaireId = proprietaire.getId(); // On stocke aussi l'ID
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

    public int getProprietaireId() {
        return proprietaireId;
    }

    public void setProprietaireId(int proprietaireId) {
        this.proprietaireId = proprietaireId;
    }
}
