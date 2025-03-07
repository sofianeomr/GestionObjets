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
import java.util.Date;
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

    public List<Exchange> getSentRequestsByUserId(int userId) {
        List<Exchange> exchanges = new ArrayList<>();
        String sql = "SELECT * FROM exchanges WHERE demandeur_id = ?";

        System.out.println("🔍 [DEBUG] Recherche des échanges envoyés pour userId: " + userId);

        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Récupération des informations sur l'échange
                int objetDemandeId = resultSet.getInt("objet_demande_id");
                int objetProposeId = resultSet.getInt("objet_propose_id");
                String statut = resultSet.getString("statut");

                System.out.println("📌 [DEBUG] Échange trouvé - Objet demandé ID: " + objetDemandeId +
                        ", Objet proposé ID: " + objetProposeId + ", Statut: " + statut);

                // Récupération des objets
                ObjectDAO objectDAO = new ObjectDAO();
                Objet objetDemande = objectDAO.getObjectById(objetDemandeId);
                Objet objetPropose = objectDAO.getObjectById(objetProposeId);

                if (objetDemande == null || objetPropose == null) {
                    System.out.println("⚠️ [WARNING] Un des objets est NULL - Vérifiez la base de données !");
                    continue; // Passer cet échange s'il manque des données
                }

                System.out.println("✅ [DEBUG] Objets récupérés - " +
                        "Objet demandé: " + objetDemande.getNom() +
                        ", Objet proposé: " + objetPropose.getNom());

                // Utilisateur (Seulement ID, pas besoin de plus)
                Utilisateur utilisateur = new Utilisateur(userId, "", "", "");

                // Création de l'échange
                Exchange exchange = new Exchange(objetPropose, objetDemande, utilisateur);

                // Vérification du statut de l'échange et gestion des erreurs
                try {
                    exchange.setStatut(Exchange.Statut.valueOf(statut));
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ [ERROR] Statut invalide pour l'échange : " + statut);
                    continue; // Passer cet échange si le statut est invalide
                }

                // ✅ Debug: Affichage de l'objet Exchange final
                System.out.println("📦 [DEBUG] Échange finalisé: " + exchange);

                // Ajout de l'échange à la liste
                exchanges.add(exchange); // Cette ligne manquait avant !

            }
        } catch (SQLException e) {
            System.out.println("❌ [ERROR] Problème SQL dans getSentRequestsByUserId: " + e.getMessage());
            e.printStackTrace();
        }
        getReceivedRequestsByUserId(userId);
        System.out.println("📊 [DEBUG] Nombre total d'échanges trouvés: " + exchanges.size());
        return exchanges;
    }

    public List<Exchange> getReceivedRequestsByUserId(int userId) {
        List<Exchange> exchanges = new ArrayList<>();

        // Requête SQL pour récupérer les échanges reçus
        String sql = "SELECT id, dateEchange, statut, demandeur_id, objet_demande_id, objet_propose_id " +
                "FROM exchanges " +
                "INTERSECT " +
                "SELECT e.id, e.dateEchange, e.statut, e.demandeur_id, e.objet_demande_id, e.objet_propose_id " +
                "FROM exchanges e " +
                "JOIN objets o ON e.objet_demande_id = o.id " +
                "WHERE o.proprietaire_id = ?";

        System.out.println("🔍 [DEBUG] Début de getReceivedRequestsByUserId pour userId: " + userId);

        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Vérification de la connexion
            if (connection == null) {
                System.out.println("❌ [ERROR] Connexion à la base de données est NULL !");
                return new ArrayList<>(); // Retourne une liste vide si la connexion est fermée
            }

            // Paramètre de la requête SQL
            statement.setInt(1, userId);
            System.out.println("📡 [DEBUG] Exécution de la requête SQL avec userId: " + userId);

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Récupération des informations sur l'échange
                int id = resultSet.getInt("id");
                int objetDemandeId = resultSet.getInt("objet_demande_id");
                int objetProposeId = resultSet.getInt("objet_propose_id");
                int demandeurId = resultSet.getInt("demandeur_id");
                String statut = resultSet.getString("statut");
                Date dateEchange = resultSet.getDate("dateEchange");

                System.out.println("📌 [DEBUG] Échange reçu trouvé - ID: " + id +
                        ", Objet demandé ID: " + objetDemandeId +
                        ", Objet proposé ID: " + objetProposeId +
                        ", Demandeur ID: " + demandeurId +
                        ", Statut: " + statut);

                // Récupération des objets
                ObjectDAO objectDAO = new ObjectDAO();
                Objet objetDemande = objectDAO.getObjectById(objetDemandeId);
                Objet objetPropose = objectDAO.getObjectById(objetProposeId);

                if (objetDemande == null || objetPropose == null) {
                    System.out.println("⚠️ [WARNING] Un des objets est NULL - Vérifiez la base de données !");
                    continue; // Passer cet échange s'il manque des données
                }

                System.out.println("✅ [DEBUG] Objets récupérés - " +
                        "Objet demandé: " + objetDemande.getNom() +
                        ", Objet proposé: " + objetPropose.getNom());

                // Création de l'utilisateur demandeur
                Utilisateur demandeur = new Utilisateur(demandeurId, "", "", "");

                // Création de l'échange
                Exchange exchange = new Exchange(objetPropose, objetDemande, demandeur);
                exchange.setId(id);
                exchange.setDateEchange(dateEchange);

                // Vérification du statut de l'échange et gestion des erreurs
                try {
                    exchange.setStatut(Exchange.Statut.valueOf(statut));
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ [ERROR] Statut invalide pour l'échange : " + statut);
                    continue; // Passer cet échange si le statut est invalide
                }

                // ✅ Debug: Affichage de l'objet Exchange final
                System.out.println("📦 [DEBUG] Échange finalisé: " + exchange);

                // Ajout de l'échange à la liste
                exchanges.add(exchange);
            }

        } catch (SQLException e) {
            System.out.println("❌ [ERROR] Problème SQL dans getReceivedRequestsByUserId: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("📊 [DEBUG] Nombre total d'échanges reçus trouvés: " + exchanges.size());
        return exchanges;
    }

}