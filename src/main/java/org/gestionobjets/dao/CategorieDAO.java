package org.gestionobjets.dao;

import org.gestionobjets.models.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

    private Connection connection;

    public CategorieDAO() {
        this.connection = ConnexionDatabase.getConnection();
    }

    // Ajouter une nouvelle catégorie
    public boolean addCategorie(Categorie categorie) {
        String query = "INSERT INTO categories (nom) VALUES (?) ON DUPLICATE KEY UPDATE nom = VALUES(nom)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categorie.getNom());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Récupérer toutes les catégories
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Categorie categorie = new Categorie( rs.getString("nom"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Récupérer une catégorie par son ID
    public Categorie getCategorieById(int id) {
        Categorie categorie = null;
        String query = "SELECT * FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                categorie = new Categorie(rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    // Mettre à jour une catégorie
    public boolean updateCategorie(Categorie categorie) {
        String query = "UPDATE categories SET nom = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categorie.getNom());
            stmt.setInt(2, categorie.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprimer une catégorie
    public boolean deleteCategorie(int id) {
        String query = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}