package org.gestionobjets.dao;

import org.gestionobjets.models.Exchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeDAO {

    private Connection connection;

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
        String sql = "INSERT INTO exchanges (objet_propose_id, objet_demande_id, demandeur_id, statut) VALUES (?, ?, ?, 'EN_ATTENTE')";
        try (Connection connection = ConnexionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exchange.getObjetPropose().getId());
            statement.setInt(2, exchange.getObjetDemande().getId());
            statement.setInt(3, exchange.getDemandeur().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
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
        String query = "SELECT * FROM echanges WHERE demandeurId = ? OR proprietaireId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                history.add(new Exchange(rs.getInt("id"),
                        rs.getInt("demandeurId"),
                        rs.getInt("proprietaireId"),
                        rs.getInt("objetId"),
                        rs.getString("statut")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}
