package org.gestionobjets.dao;

import org.gestionobjets.models.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        this.connection = ConnexionDatabase.getConnection();
    }

    public boolean registerUser(Utilisateur user) {
        String query = "INSERT INTO utilisateurs (nom, email, motDePasse) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getMotDePasse());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Utilisateur connexionUser(String email, String motDePasse) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND motDePasse = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 si l'utilisateur n'est pas trouvé
    }

    public Utilisateur getUserById(int userId) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";  // Assurez-vous que le nom de la table et des colonnes est correct

        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Remplir la requête préparée avec l'ID de l'utilisateur
            statement.setInt(1, userId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Vérifier s'il y a un utilisateur avec cet ID
            if (resultSet.next()) {
                // Créer un objet Utilisateur avec les données récupérées
                utilisateur = new Utilisateur(
                        resultSet.getInt("id"),  // ID de l'utilisateur
                        resultSet.getString("nom"),  // Nom de l'utilisateur
                        resultSet.getString("email"),  // Email de l'utilisateur
                        resultSet.getString("motdepasse")  // Mot de passe de l'utilisateur
                );
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;  // Retourner l'objet Utilisateur, ou null si l'utilisateur n'a pas été trouvé
    }

}
