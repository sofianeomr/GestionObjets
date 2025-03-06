package org.gestionobjets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDatabase {
    private static final String URL_DATABASE = "jdbc:mysql://localhost:3306/objectsxchange";
    private static final String USER = "root";
    private static final String PASSWORD = "fonsa";
    private static Connection connection = null;

    // Méthode pour obtenir une connexion valide
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver
                connection = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données.");
        }
        return connection;
    }

    // Méthode pour fermer la connexion proprement
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
