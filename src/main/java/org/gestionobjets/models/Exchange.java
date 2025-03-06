package org.gestionobjets.models;

import jakarta.persistence.*;

import java.sql.Struct;
import java.util.Date;

@Entity
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "objet_propose_id")
    private Objet objetPropose;

    @ManyToOne
    @JoinColumn(name = "objet_demande_id")
    private Objet objetDemande;

    @ManyToOne
    @JoinColumn(name = "demandeur_id")
    private Utilisateur demandeur;

    @Column(nullable = false)
    private Date dateEchange;


    public enum Statut {
        EN_ATTENTE,
        ACCEPTE,
        REFUSE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;

    // Constructeur par défaut
    public Exchange() {
    }

    // Constructeur avec paramètres
    public Exchange(Objet objetPropose, Objet objetDemande, Utilisateur demandeur) {
        this.objetPropose = objetPropose;
        this.objetDemande = objetDemande;
        this.demandeur = demandeur;
        this.dateEchange = new Date();
        this.statut = Statut.EN_ATTENTE;
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

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}