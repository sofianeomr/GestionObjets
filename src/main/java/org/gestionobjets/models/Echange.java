package org.gestionobjets.models;

import java.util.Date;

public class Echange {
    private int id;
    private Objet objetPropose;
    private Objet objetDemande;
    private Utilisateur demandeur;
    private Date dateEchange;
    private String statut; // "EN_ATTENTE", "ACCEPTE", "REFUSE"

    public Echange() {}

    public Echange(int id, Objet objetPropose, Objet objetDemande, Utilisateur demandeur, Date dateEchange, String statut) {
        this.id = id;
        this.objetPropose = objetPropose;
        this.objetDemande = objetDemande;
        this.demandeur = demandeur;
        this.dateEchange = dateEchange;
        this.statut = statut;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Objet getObjetPropose() {
        return objetPropose;
    }
    public void setObjetPropose(Objet objetPropose) {
        this.objetPropose = objetPropose;
    }
    public Objet getObjetDemande() {
        return objetDemande;
    }
    public void setObjetDemande(Objet objetDemande) {
        this.objetDemande = objetDemande;
    }
    public Utilisateur getDemandeur() {
        return demandeur;
    }
    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }
    public Date getDateEchange() {
        return dateEchange;
    }
    public void setDateEchange(Date dateEchange) {
        this.dateEchange = dateEchange;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
}
