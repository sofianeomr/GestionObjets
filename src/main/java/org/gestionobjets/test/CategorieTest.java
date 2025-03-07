package org.gestionobjets.test;

import org.gestionobjets.models.Categorie;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategorieTest {

    private Categorie categorie;

    @Before
    public void setUp() {
        categorie = new Categorie("Électronique");
    }

    @Test
    public void testConstructeurAvecNom() {
        Categorie categorie = new Categorie("Livres");
        assertNotNull(categorie);
        assertEquals("Livres", categorie.getNom());
    }


    @Test
    public void testGettersEtSetters() {
        categorie.setId(10);
        categorie.setNom("Jouets");

        assertEquals(10, categorie.getId());
        assertEquals("Jouets", categorie.getNom());
    }

    @Test
    public void testConstructeurParDefaut() {
        Categorie categorie = new Categorie();
        assertNotNull(categorie);
        assertNull(categorie.getNom()); // Le nom devrait être null par défaut
    }
}
