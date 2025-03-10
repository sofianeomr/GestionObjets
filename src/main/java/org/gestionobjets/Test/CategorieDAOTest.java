package org.gestionobjets.Test;

import org.gestionobjets.dao.CategorieDAO;
import org.gestionobjets.models.Categorie;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CategorieDAOTest {

    private CategorieDAO categorieDAO;

    @Before
    public void setUp() {
        categorieDAO = new CategorieDAO();
        // Assurez-vous que la base de données de test est configurée et vide avant chaque test
    }

    @Test
    public void testAddCategorie() {
        Categorie categorie = new Categorie("Nouvelle Catégorie");
        boolean result = categorieDAO.addCategorie(categorie);
        assertTrue(result);

        // Vérifiez que la catégorie a été ajoutée à la base de données
        Categorie addedCategorie = categorieDAO.getCategorieById(categorie.getId());
        assertNotNull(addedCategorie);
        assertEquals("Nouvelle Catégorie", addedCategorie.getNom());
    }

    @Test
    public void testGetAllCategories() {
        // Ajoutez quelques catégories pour le test
        categorieDAO.addCategorie(new Categorie("Catégorie 1"));
        categorieDAO.addCategorie(new Categorie("Catégorie 2"));

        List<Categorie> categories = categorieDAO.getAllCategories();
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    public void testGetCategorieById() {
        // Ajoutez une catégorie pour le test
        Categorie categorie = new Categorie("Catégorie Test");
        categorieDAO.addCategorie(categorie);

        Categorie retrievedCategorie = categorieDAO.getCategorieById(categorie.getId());
        assertNotNull(retrievedCategorie);
        assertEquals("Catégorie Test", retrievedCategorie.getNom());
    }

    @Test
    public void testUpdateCategorie() {
        // Ajoutez une catégorie pour le test
        Categorie categorie = new Categorie("Catégorie à Mettre à Jour");
        categorieDAO.addCategorie(categorie);

        // Mettez à jour la catégorie
        categorie.setNom("Nouveau Nom");
        boolean result = categorieDAO.updateCategorie(categorie);
        assertTrue(result);

        // Vérifiez que la catégorie a été mise à jour
        Categorie updatedCategorie = categorieDAO.getCategorieById(categorie.getId());
        assertEquals("Nouveau Nom", updatedCategorie.getNom());
    }

    @Test
    public void testDeleteCategorie() {
        // Ajoutez une catégorie pour le test
        Categorie categorie = new Categorie("Catégorie à Supprimer");
        categorieDAO.addCategorie(categorie);

        boolean result = categorieDAO.deleteCategorie(categorie.getId());
        assertTrue(result);

        // Vérifiez que la catégorie a été supprimée
        Categorie deletedCategorie = categorieDAO.getCategorieById(categorie.getId());
        assertNull(deletedCategorie);
    }
}
