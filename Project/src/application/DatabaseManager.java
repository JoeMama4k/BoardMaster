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

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// Register the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Create the connection
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			// Create the statement
			statement = connection.createStatement();

			// Execute the query
			resultSet = statement.executeQuery(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	
	public static void createUser(String email, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Encrypt the password
            String encryptedPassword = encryptPassword(password);

            // Prepare the SQL query
            String sql = "INSERT INTO Users (Email, Username, Password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, encryptedPassword);

            // Execute the query
            statement.executeUpdate();

            // Close the statement
            statement.close();
            System.out.println("Successfully created new user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	 protected static String encryptPassword(String password) {
	        try {
	            // Create an instance of the SHA-256 hashing algorithm
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");

	            // Generate the hash value for the password
	            byte[] hash = digest.digest(password.getBytes());

	            // Encode the hash using Base64 encoding
	            String encodedHash = Base64.getEncoder().encodeToString(hash);

	            return encodedHash;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }

	        return null;
	    }
	
	
}
