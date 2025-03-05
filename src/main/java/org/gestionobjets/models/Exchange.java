package org.gestionobjets.models;

import java.util.Date;

public class Exchange {
    private int id;
    private Objet objetPropose;
    private Objet objetDemande;
    private Utilisateur demandeur;
    private Date dateEchange;
    private String statut; // "EN_ATTENTE", "ACCEPTE", "REFUSE"

    public Exchange() {}

    public Exchange(int id, Objet objetPropose, Objet objetDemande, Utilisateur demandeur, Date dateEchange, String statut) {
        this.id = id;
        this.objetPropose = objetPropose;
        this.objetDemande = objetDemande;
        this.demandeur = demandeur;
        this.dateEchange = dateEchange;
        this.statut = statut;
    }


    public Exchange(Utilisateur demandeur, Objet objetPropose, Objet objetDemande) {
        this.demandeur = demandeur;
        this.objetPropose = objetPropose;
        this.objetDemande = objetDemande;
        this.dateEchange = new Date();
        this.statut = "EN_ATTENTE";
    }

    public Exchange(int id, int demandeurId, int proprietaireId, int objetId, String statut) {
        this.id = id;
        this.demandeur = new Utilisateur();
        this.demandeur.setId(demandeurId);
        this.objetDemande = new Objet();
        this.objetDemande.setId(objetId);
        this.objetDemande.setProprietaireId(proprietaireId);
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

    public int getDemandeurId() {
        return this.demandeur != null ? this.demandeur.getId() : -1;
    }

    public int getProprietaireId() {
        return this.objetDemande != null ? this.objetDemande.getProprietaireId() : -1;
    }

    public int getObjetId() {
        return this.objetDemande != null ? this.objetDemande.getId() : -1;
    }

}
