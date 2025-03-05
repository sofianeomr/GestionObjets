package org.gestionobjets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDatabase {
    private static final String URL_DATABASE = "jdbc:mysql://localhost:3306/objectsxchange";
    private static final String USER = "root";
    private static final String PASSWORD = "fonsa";
    private static Connection connection = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Charge le driver MySQL
            connection = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données.");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
