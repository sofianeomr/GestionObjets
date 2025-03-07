package org.gestionobjets.test;

import org.gestionobjets.dao.UserDAO;
import org.gestionobjets.models.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        userDAO = new UserDAO() {
            protected Connection getConnection() {
                return mockConnection;
            }
        };
    }

    @Test
    public void testRegisterUser() throws SQLException {
        Utilisateur user = new Utilisateur("NomTest", "emailTest@example.com", "passwordTest");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.registerUser(user);
        assertTrue(result);

        verify(mockPreparedStatement).setString(1, "NomTest");
        verify(mockPreparedStatement).setString(2, "emailTest@example.com");
        verify(mockPreparedStatement).setString(3, "passwordTest");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testConnexionUser() throws SQLException {
        String email = "emailTest@example.com";
        String motDePasse = "passwordTest";

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("NomTest");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Utilisateur user = userDAO.connexionUser(email, motDePasse);
        assertNotNull(user);
        assertEquals("NomTest", user.getNom());
    }
}
