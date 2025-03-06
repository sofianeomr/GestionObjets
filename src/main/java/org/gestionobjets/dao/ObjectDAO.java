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
        String query = "INSERT INTO objets (nom, categorie_id, description, proprietaire_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Debug : Afficher les valeurs avant l'insertion
            System.out.println("Tentative d'insertion d'un objet avec :");
            System.out.println("Nom : " + objet.getNom());
            System.out.println("Catégorie : " + objet.getCategorie().getId());
            System.out.println("Description : " + objet.getDescription());
            System.out.println("Propriétaire ID : " + objet.getProprietaire().getId());

            stmt.setString(1, objet.getNom());
            stmt.setInt(2, objet.getCategorie().getId()); // Assurez-vous que la catégorie est récupérée correctement
            stmt.setString(3, objet.getDescription());
            stmt.setInt(4, objet.getProprietaire().getId());

            int rowsAffected = stmt.executeUpdate();

            // Debug : Vérifier si l'insertion a réussi
            if (rowsAffected > 0) {
                System.out.println("Insertion réussie !");
                return true;
            } else {
                System.out.println("Échec de l'insertion.");
                return false;
            }

        } catch (SQLException e) {
            // Debug : Afficher l'erreur SQL complète
            System.out.println("Erreur SQL lors de l'insertion : " + e.getMessage());
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

    public List<Objet> getObjectsByOwnerId(int ownerId) {
        List<Objet> objets = new ArrayList<>();
        String sql = "SELECT * FROM objets WHERE proprietaire_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Objet objet = new Objet(rs.getInt("id"), rs.getString("nom"), rs.getString("description"));
                objets.add(objet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

    public List<Objet> getAllObjetsExceptOwner(int ownerId) {
        List<Objet> objets = new ArrayList<>();
        String sql = "SELECT * FROM objets WHERE proprietaire_id != ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Objet objet = new Objet(rs.getInt("id"), rs.getString("nom"), rs.getString("description"));
                objets.add(objet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objets;
    }

}
