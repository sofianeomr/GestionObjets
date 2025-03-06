package org.gestionobjets.dao;

import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Utilisateur;
import org.gestionobjets.models.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectDAO {
    private Connection connection;

    public ObjectDAO() {
        this.connection = ConnexionDatabase.getConnection();
    }

    public boolean addObject(Objet objet) {
        String query = "INSERT INTO objets (nom, categorie, description, proprietaire_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, objet.getNom());
            stmt.setString(2, objet.getCategorie().getNom()); // Assurez-vous que la catégorie est récupérée correctement
            stmt.setString(3, objet.getDescription());
            stmt.setInt(4, objet.getProprietaire().getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Objet> getAllObjets() {
        List<Objet> objets = new ArrayList<>();
        String query = "SELECT o.*, c.nom AS categorieNom, u.id AS proprietaireId, u.nom AS proprietaireNom " +
                "FROM objets o " +
                "JOIN categories c ON o.categorie_id = c.id " +
                "JOIN utilisateurs u ON o.proprietaire_id = u.id";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categorie categorie = new Categorie(rs.getString("categorieNom"));
                Utilisateur proprietaire = new Utilisateur(rs.getInt("proprietaireId"), rs.getString("proprietaireNom"), "", "");

                objets.add(new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        categorie,
                        proprietaire
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

    public List<Objet> searchObjects(String keyword) {
        List<Objet> objets = new ArrayList<>();
        String query = "SELECT o.*, c.nom AS categorieNom, u.id AS proprietaireId, u.nom AS proprietaireNom " +
                "FROM objets o " +
                "JOIN categories c ON o.categorie_id = c.id " +
                "JOIN utilisateurs u ON o.proprietaire_id = u.id " +
                "WHERE o.nom LIKE ? OR c.nom LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categorie categorie = new Categorie(rs.getString("categorieNom"));
                Utilisateur proprietaire = new Utilisateur(rs.getInt("proprietaireId"), rs.getString("proprietaireNom"), "", "");

                objets.add(new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        categorie,
                        proprietaire
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

    public Objet getObjectById(int id) {
        Objet objet = null;
        String sql = "SELECT o.*, c.nom AS categorieNom, u.id AS proprietaireId, u.nom AS proprietaireNom " +
                "FROM objets o " +
                "JOIN categories c ON o.categorie_id = c.id " +
                "JOIN utilisateurs u ON o.proprietaire_id = u.id " +
                "WHERE o.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categorie categorie = new Categorie(rs.getString("categorieNom"));
                Utilisateur proprietaire = new Utilisateur(rs.getInt("proprietaireId"), rs.getString("proprietaireNom"), "", "");

                objet = new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        categorie,
                        proprietaire
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objet;
    }

    public Objet getObjectByOwnerId(int ownerId) {
        Objet objet = null;
        String sql = "SELECT o.*, c.nom AS categorieNom, u.id AS proprietaireId, u.nom AS proprietaireNom " +
                "FROM objets o " +
                "JOIN categories c ON o.categorie_id = c.id " +
                "JOIN utilisateurs u ON o.proprietaire_id = u.id " +
                "WHERE o.proprietaire_id = ? LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categorie categorie = new Categorie(rs.getString("categorieNom"));
                Utilisateur proprietaire = new Utilisateur(rs.getInt("proprietaireId"), rs.getString("proprietaireNom"), "", "");

                objet = new Objet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        categorie,
                        proprietaire
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objet;
    }
}
