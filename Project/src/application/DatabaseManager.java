package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7622471";
    private static final String DB_USERNAME = "sql7622471";
    private static final String DB_PASSWORD = "sEPS2ywYQW";

    public static ResultSet executeQuery(String query) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            return statement.executeQuery(query);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static boolean createUser(String email, String username, String password) {
        try (Connection connection = getConnection()) {
            // Encrypt the password
            String encryptedPassword = encryptPassword(password);

            // Prepare the SQL query
            String sql = "INSERT INTO Users (Email, Username, Password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, encryptedPassword);

                // Execute the query
                statement.executeUpdate();

                System.out.println("Successfully created new user");
                return true;
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (e.g., display an error message)
            System.err.println("Error creating user: " +   e.getMessage());
            return false;
        }
        
    }

     public static String encryptPassword(String password) {
        try {
            // Create an instance of SHA-256 
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Generate the hash value for the password
            byte[] hash = digest.digest(password.getBytes());

            // Encode the hash using Base64
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
