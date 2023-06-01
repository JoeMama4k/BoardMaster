package application.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

/**
 * 
 * The DatabaseManager class provides functionality for managing the database.
 */
public class DatabaseManager {

	private static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7622471";
	private static final String DB_USERNAME = "sql7622471";
	private static final String DB_PASSWORD = "sEPS2ywYQW";

	/**
	 * 
	 * Executes the given SQL query and returns the result set.
	 * 
	 * @param query the SQL query to execute
	 * 
	 * @return the result set obtained from the query
	 * 
	 * @throws SQLException if an SQL error occurs
	 */
	public static ResultSet executeQuery(String query) throws SQLException {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

			return statement.executeQuery(query);
		}
	}

	/**
	 * 
	 * Retrieves a database connection.
	 * 
	 * @return a connection to the database
	 * @throws SQLException if a connection cannot be established
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	/**
	 * 
	 * Creates a new user in the database.
	 * 
	 * @param email    the email of the user
	 * 
	 * @param username the username of the user
	 * 
	 * @param password the password of the user
	 * 
	 * @return true if the user is created successfully, false otherwise
	 */
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

				LogManager.log(
						String.format("New account with email %s and username %s has been created", email, username));
				return true;
			}
		} catch (SQLException e) {
			LogManager.log("Error creating user: " + e.getMessage());
			return false;
		}

	}

	/**
	 * 
	 * Encrypts the given password using the SHA-256 algorithm.
	 * 
	 * @param password the password to encrypt
	 * 
	 * @return the encrypted password
	 */
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
