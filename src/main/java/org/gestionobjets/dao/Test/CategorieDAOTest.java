package org.gestionobjets.dao.Test;

import org.gestionobjets.dao.CategorieDAO;
import org.gestionobjets.models.Categorie;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategorieDAOTest {

    private CategorieDAO categorieDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        categorieDAO = new CategorieDAO() {
            protected Connection getConnection() {
                return mockConnection;
            }
        };
    }

    @Test
    public void testAddCategorie() throws SQLException {
        Categorie categorie = new Categorie("Nouvelle Catégorie");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = categorieDAO.addCategorie(categorie);
        assertTrue(result);

        verify(mockPreparedStatement).setString(1, "Nouvelle Catégorie");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllCategories() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("Catégorie 1");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        List<Categorie> categories = categorieDAO.getAllCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Catégorie 1", categories.get(0).getNom());
    }

    @Test
    public void testGetCategorieById() throws SQLException {
        int id = 1;
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("Catégorie Test");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Categorie categorie = categorieDAO.getCategorieById(id);
        assertNotNull(categorie);
        assertEquals("Catégorie Test", categorie.getNom());
    }

    @Test
    public void testUpdateCategorie() throws SQLException {
        Categorie categorie = new Categorie( "Catégorie à Mettre à Jour");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = categorieDAO.updateCategorie(categorie);
        assertTrue(result);

        verify(mockPreparedStatement).setString(1, "Catégorie à Mettre à Jour");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }


}
