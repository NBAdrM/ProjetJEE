package com.example.projetjee.utils;


import java.sql.*;import java.sql.Connection;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
public class DbConnnect {
    private static final String URL = "jdbc:mysql://localhost:3306/projetjeedb";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        // Charge le driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Crée et retourne la connexion à la base de données
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int addStudent(String firstName, String lastName, String email, String adress, String username, String password) throws SQLException, ClassNotFoundException {
        String hashedPassword = hashPassword(password);
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Person (first_name, last_name, email, address, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, email);
        stmt.setString(4, adress);
        stmt.setString(5, username);
        stmt.setString(6, hashedPassword);

        int rowsAffected = stmt.executeUpdate();

        // Récupérer l'ID généré automatiquement
        int generatedId = -1;
        if (rowsAffected > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        }

        stmt.close();

        query = "INSERT INTO Student (id) VALUES (?)";
        stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.close();
        conn.close();

        return generatedId;
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }

    public static boolean alreadyExisteUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT COUNT(*) FROM Person WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}