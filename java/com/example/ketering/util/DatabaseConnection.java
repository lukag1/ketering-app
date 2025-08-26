package com.example.ketering.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Postavite vaše podatke za konekciju na bazu
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ketering_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root"; // Vaš username za bazu
    private static final String JDBC_PASSWORD = ""; // Vaš password za bazu

    // Statička metoda za uspostavljanje konekcije
    public static Connection getConnection() throws SQLException {
        try {
            // Učitavanje MySQL drajvera
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Ako drajver nije pronađen, to je kritična greška
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        
        // Vraćanje konekcije
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}