package org.gestionobjets;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Créer SessionFactory à partir du fichier hibernate.cfg.xml
            return new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("La création de SessionFactory a échoué : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static void main(String[] args) {
        // Cette ligne suffit pour déclencher la création de la base de données
        // et des tables correspondant à vos modèles
        SessionFactory factory = HibernateUtil.getSessionFactory();
        System.out.println("Base de données et tables créées avec succès!");
        shutdown();
    }
}
