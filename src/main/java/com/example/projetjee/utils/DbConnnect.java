package com.example.projetjee.utils;


import java.sql.*;import java.sql.Connection;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
public class DbConnnect {
    private static final String URL = "jdbc:mysql://localhost:3306/projetjeedb";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";

    private static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        // Charge le driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Crée et retourne la connexion à la base de données
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int addPerson(String firstName, String lastName, String email, String adress, String username, String password) throws SQLException, ClassNotFoundException {
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
        conn.close();

        return generatedId;
    }

    public static int addStudent(String firstName, String lastName, String email, String adress, String username, String password) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName,lastName,email,adress,username,password);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Student (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addStudent(String firstName, String lastName, String email, String adress, String username, String password, String report) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName,lastName,email,adress,username,password);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Student (id,report) VALUES (?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);
        stmt.setString(2, report);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addTeacher(String firstName, String lastName, String email, String adress, String username, String password) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName,lastName,email,adress,username,password);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Teacher (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addAdmin(String firstName, String lastName, String email, String adress, String username, String password) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName,lastName,email,adress,username,password);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Admin (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }


    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static boolean checkNoneHashedPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }

    public static boolean checkPassword(int id,String password) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();
        String query = "SELECT password FROM Person WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            rs.close();
            stmt.close();
            conn.close();
            return checkNoneHashedPassword(password,hashedPassword);
        }
        rs.close();
        stmt.close();
        conn.close();
        return false;
    }

    public static boolean alreadyExisteUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT COUNT(*) FROM Person WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            boolean out = rs.getInt(1) > 0;
            rs.close();
            stmt.close();
            conn.close();
            return out;
        }
        rs.close();
        stmt.close();
        conn.close();
        return false;
    }

    public static void addReportStudent(int id,String report) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "UPDATE Student SET report = ? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, report);
        stmt.setInt(2,id);

        stmt.close();
        conn.close();
    }

    public static void main(String[] args){
        try {
            addStudent("adrien","maestri","lala@gmail.com","43 la","a","a");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}