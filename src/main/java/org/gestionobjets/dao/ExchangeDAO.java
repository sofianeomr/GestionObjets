package org.gestionobjets.dao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.gestionobjets.models.Categorie;
import org.gestionobjets.models.Exchange;
import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ExchangeDAO {

    private Connection connection;
    public enum Statut {
        EN_ATTENTE,
        ACCEPTE,
        REFUSE
    }
    public ExchangeDAO() {
        this.connection = ConnexionDatabase.getConnection();
    }

    /*
    public boolean requestExchange(Exchange exchange) {
        String query = "INSERT INTO echanges (demandeurId, proprietaireId, objetId, statut) VALUES (?, ?, ?, 'pending')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exchange.getDemandeurId());
            stmt.setInt(2, exchange.getProprietaireId());
            stmt.setInt(3, exchange.getObjetId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    */

    public void requestExchange(Exchange exchange) {
        String sql = "INSERT INTO exchanges (objet_propose_id, objet_demande_id, demandeur_id, statut, dateEchange) VALUES (?, ?, ?, 'EN_ATTENTE', NOW())";
        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exchange.getObjetPropose().getId());
            statement.setInt(2, exchange.getObjetDemande().getId());
            statement.setInt(3, exchange.getDemandeur().getId());

            // Exécution de la requête
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la demande d'échange : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean updateExchangeStatus(int exchangeId, String status) {
        String query = "UPDATE echanges SET statut = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, exchangeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Exchange> getUserExchangeHistory(int userId) {
        List<Exchange> history = new ArrayList<>();
        String query = "SELECT e.*, o.id AS objetId, o.nom AS objetNom, o.description AS objetDescription, " +
                "u.id AS proprietaireId, u.nom AS proprietaireNom " +
                "FROM echanges e " +
                "JOIN objets o ON e.objetDemandeId = o.id " +
                "JOIN utilisateurs u ON o.proprietaireId = u.id " +
                "WHERE e.demandeurId = ? OR o.proprietaireId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Créez une instance de Categorie
                Categorie categorie = new Categorie(rs.getString("categorieNom"));

                // Créez une instance de Utilisateur
                Utilisateur proprietaire = new Utilisateur(
                        rs.getInt("proprietaireId"),
                        rs.getString("proprietaireNom"),
                        "", // email non nécessaire ici
                        ""  // motDePasse non nécessaire ici
                );

                // Créez une instance de Objet
                Objet objetDemande = new Objet(
                        rs.getInt("objetId"),
                        rs.getString("objetNom"),
                        rs.getString("objetDescription"),
                        categorie,
                        proprietaire
                );

                // Créez une instance de Utilisateur pour le demandeur
                Utilisateur demandeur = new Utilisateur(
                        rs.getInt("demandeurId"),
                        "", // nom non nécessaire ici
                        "", // email non nécessaire ici
                        ""  // motDePasse non nécessaire ici
                );

                // Créez une instance de Exchange
                Exchange exchange = new Exchange(
                        null, // objetPropose non nécessaire ici
                        objetDemande,
                        demandeur
                );

                history.add(exchange);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public List<Exchange> getSentRequestsByUserId(int userId) {

        List<Exchange> exchanges = new ArrayList<>();
        String sql = "SELECT * FROM exchanges WHERE demandeur_id = ?";
        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Récupération des informations sur l'échange
                int objetDemandeId = resultSet.getInt("objet_demande_id");
                int objetProposeId = resultSet.getInt("objet_propose_id");
                String statut = resultSet.getString("statut");

                // Créer des objets pour l'échange
                ObjectDAO objectDAO = new ObjectDAO();
                Objet objetDemande = objectDAO.getObjectById(objetDemandeId);
                Objet objetPropose = objectDAO.getObjectById(objetProposeId);

                // Utilisateur (ID de l'utilisateur demandé)
                // Remplacer le fait de récupérer l'utilisateur entier par son ID, si nécessaire
                Utilisateur utilisateur = new Utilisateur(userId, "", "", ""); // Pas besoin de récupérer les données utilisateur ici

                // Créer l'objet Exchange
                Exchange exchange = new Exchange(objetPropose, objetDemande, utilisateur);
                exchange.setStatut(Exchange.Statut.valueOf(statut)); // Définit le statut de l'échange

                // Ajouter l'échange à la liste
                exchanges.add(exchange);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchanges;
    }



}
