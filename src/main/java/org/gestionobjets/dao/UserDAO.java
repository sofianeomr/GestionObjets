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
        try (Connection conn = ConnexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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
        try (Connection conn = ConnexionDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
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



}
