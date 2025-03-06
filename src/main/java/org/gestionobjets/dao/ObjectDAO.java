package org.gestionobjets.dao;

import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectDAO {
    private Connection connection;

    public ObjectDAO() {
        this.connection = ConnexionDatabase.getConnection();
    }



    public boolean addObject(Objet objet) {
        if (objet.getNom() == null || objet.getNom().trim().isEmpty() ||
                objet.getCategorie() == null || objet.getCategorie().trim().isEmpty() ||
                objet.getDescription() == null || objet.getDescription().trim().isEmpty()) {
            System.out.println("Erreur : Impossible d'ajouter un objet avec des champs vides !");
            return false;
        }

        String query = "INSERT INTO objets (nom, categorie, description, proprietaireId) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, objet.getNom());
            stmt.setString(2, objet.getCategorie());
            stmt.setString(3, objet.getDescription());
            stmt.setInt(4, objet.getProprietaireId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Récupérer les objets de l'utilisateur
    public List<Objet> getUserObjects(int userId) {
        List<Objet> objets = new ArrayList<>();
        String query = "SELECT * FROM objets WHERE proprietaireId = ?";
        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                objets.add(new Objet(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("categorie"), rs.getString("description"),
                        rs.getInt("proprietaireId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

    // Récupérer les objets des autres utilisateurs
    public List<Objet> getOtherUsersObjects(int userId) {
        List<Objet> objets = new ArrayList<>();
        String query = "SELECT * FROM objets WHERE proprietaireId <> ?";
        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                objets.add(new Objet(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("categorie"), rs.getString("description"),
                        rs.getInt("proprietaireId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }




    public List<Objet> searchObjects(String keyword) {
        List<Objet> objets = new ArrayList<>();
        String query = "SELECT * FROM objets WHERE nom LIKE ? OR categorie LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                objets.add(new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("categorie"),
                        new Utilisateur(rs.getInt("proprietaireId"), "", "", "")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

    public Objet getObjectById(int id) {
        Objet objet = null;
        String sql = "SELECT * FROM objets WHERE id = ?";

        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                objet = new Objet(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("categorie"),
                       // rs.getInt("proprietaire_id"));
                        rs.getInt("proprietaireId"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objet;
    }

    public Objet getObjectByOwnerId(int ownerId) {
        Objet objet = null;
        String sql = "SELECT * FROM objets WHERE proprietaire_id = ? LIMIT 1"; // Récupère un seul objet

        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                objet = new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("categorie"),
                        //rs.getInt("proprietaire_id")
                        rs.getInt("proprietaireId"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objet;
    }


}
