package org.gestionobjets.repository;

import org.gestionobjets.models.Utilisateur;
import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Echange;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Utilisateur> utilisateurs = new ArrayList<>();
    public static List<Objet> objets = new ArrayList<>();
    public static List<Echange> echanges = new ArrayList<>();

    public static Utilisateur findUtilisateurByEmail(String email) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public static void addUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public static void addObjet(Objet objet) {
        objets.add(objet);
    }

    public static Objet findObjetById(int id) {
        for (Objet o : objets) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public static List<Objet> searchObjets(String query) {
        List<Objet> results = new ArrayList<>();
        for (Objet o : objets) {
            if (o.getNom().toLowerCase().contains(query.toLowerCase()) ||
                    o.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    o.getCategorie().toLowerCase().contains(query.toLowerCase())) {
                results.add(o);
            }
        }
        return results;
    }

    public static void addEchange(Echange echange) {
        echanges.add(echange);
    }
}
