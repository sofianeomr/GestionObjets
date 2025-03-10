package org.gestionobjets.Test;

import org.gestionobjets.dao.ExchangeDAO;
import org.gestionobjets.models.Exchange;
import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExchangeDAOTest {

    private ExchangeDAO exchangeDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @Before
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        exchangeDAO = new ExchangeDAO() {
            protected Connection getConnection() {
                return mockConnection;
            }
        };
    }

    @Test
    public void testRequestExchange() throws SQLException {
        Objet objetPropose = new Objet(1, "Objet Proposé", "Description", null, null);
        Objet objetDemande = new Objet(2, "Objet Demandé", "Description", null, null);
        Utilisateur demandeur = new Utilisateur(1, "Demandeur", "email@example.com", "password");
        Exchange exchange = new Exchange(objetPropose, objetDemande, demandeur);

        exchangeDAO.requestExchange(exchange);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateExchangeStatus() throws SQLException {
        int exchangeId = 1;
        String newStatus = "ACCEPTE";

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = exchangeDAO.updateExchangeStatus(exchangeId, newStatus);
        assertTrue(result);

        verify(mockPreparedStatement).setString(1, "ACCEPTE");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}
