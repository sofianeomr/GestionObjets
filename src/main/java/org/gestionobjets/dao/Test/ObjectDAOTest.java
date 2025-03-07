package org.gestionobjets.dao.Test;

import org.gestionobjets.dao.ObjectDAO;
import org.gestionobjets.models.Categorie;
import org.gestionobjets.models.Objet;
import org.gestionobjets.models.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ObjectDAOTest {

    private ObjectDAO objectDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        objectDAO = new ObjectDAO() {
            protected Connection getConnection() {
                return mockConnection;
            }
        };
    }

    @Test
    public void testAddObject() throws SQLException {
        Categorie categorie = new Categorie("Catégorie Test");
        Utilisateur proprietaire = new Utilisateur(3, "NomTest", "emailTest@example.com", "passwordTest");
        Objet objet = new Objet("Objet Test", "Description", categorie, proprietaire);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = objectDAO.addObject(objet);
        assertTrue(result);

        verify(mockPreparedStatement).setString(1, "Objet Test");
        verify(mockPreparedStatement).setString(3, "Description");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetObjectById() throws SQLException {
        int objectId = 1;

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("Objet Test");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Objet objet = objectDAO.getObjectById(objectId);
        assertNotNull(objet);
        assertEquals("Objet Test", objet.getNom());
    }
}
