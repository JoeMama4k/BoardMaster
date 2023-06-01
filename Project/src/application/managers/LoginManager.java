package application.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.text.Text;

/**
 * 
 * The LoginManager class provides functionality for managing user login and
 * credentials.
 */
public class LoginManager {

	private static final String CREDENTIALS_FILE = "credentials.txt";

	public static UIManager uiManager;

	/**
	 * 
	 * Saves the user credentials to a file.
	 * 
	 * @param username the username to save
	 * @param password the password to save
	 */

	public static void saveCredentials(String username, String password) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, false))) {
			writer.write(username + ":" + password);
		} catch (IOException e) {
			LogManager.log("Failed to save credentials: " + e.getMessage());
		}
	}

	/**
	 * 
	 * Retrieves the saved user credentials from the file.
	 * 
	 * @return an array containing the username and password, or null if credentials
	 *         are not found
	 */
	public static String[] getCredentials() {
		try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
			String line = reader.readLine();
			if (line != null) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					return parts;
				}
			}
		} catch (IOException e) {
			LogManager.log("Failed to retrieve credentials: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * Checks if a login is valid based on the provided email/username and password.
	 * 
	 * @param email    the email of the user
	 * 
	 * @param username the username of the user
	 * 
	 * @param password the password of the user
	 * 
	 * @return true if the login is valid, false otherwise
	 */
	public static boolean isLoginValid(String email, String username, String password) {
		try {
			// Prepare the SQL query with parameters
			String sql = "SELECT * FROM Users WHERE (Email = ? OR Username = ?) AND Password = ?";
			PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, username);
			statement.setString(3, DatabaseManager.encryptPassword(password));

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

			// Close the statement and result set
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			LogManager.log("Failed to check if user exists" + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * Checks if a login is valid based on the provided email/username and password.
	 * 
	 * Displays an error message on failure.
	 * 
	 * @param email     the email of the user
	 * 
	 * @param username  the username of the user
	 * 
	 * @param password  the password of the user
	 * 
	 * @param errorText the text object to display the error message
	 * 
	 * @return true if the login is valid, false otherwise
	 */
	public static boolean isLoginValid(String email, String username, String password, Text errorText) {
		try {
			// Prepare the SQL query with parameters
			String sql = "SELECT * FROM Users WHERE (Email = ? OR Username = ?) AND Password = ?";
			PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, username);
			statement.setString(3, DatabaseManager.encryptPassword(password));

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

			// Close the statement and result set
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			LogManager.log("Failed to check if user exists" + e.getMessage());
			errorText.setText("Oops! Something Went Wrong, Try again.");
		}
		return false;
	}

	/**
	 * 
	 * Performs the login process with the provided email and password. If the login
	 * is valid, saves the credentials and shows the games UI.
	 * 
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @return true if the login is successful, false otherwise
	 */
	public static boolean login(String email, String password) {
		if (LoginManager.isLoginValid(email, email, password)) {
			LogManager.log(String.format("User %s has logged in", email));
			saveCredentials(email, password);
			uiManager.showGamesUI();
			return true;
		} else {
			LogManager.log(String.format("Someone failed to login with username/email: %s.", email));
			return false;
		}
	}

	/**
	 * 
	 * Performs the login process with the provided email and password. If the login
	 * is valid, saves the credentials and shows the games UI. Displays an error
	 * message on failure.
	 * 
	 * @param email     the email of the user
	 * @param password  the password of the user
	 * @param errorText the text object to display the error message
	 */
	public static void login(String email, String password, Text errorText) {
		if (LoginManager.isLoginValid(email, email, password, errorText)) {
			LogManager.log(String.format("User %s has logged in", email));
			saveCredentials(email, password);
			uiManager.showGamesUI();
		} else {
			LogManager.log(String.format("Someone failed to login with username/email: %s.", email));
			errorText.setText("Wrong Username or Password.");
		}
	}

	public static void logout() {
		try {
			String username = getCredentials()[0];
			FileWriter fileWriter = new FileWriter(CREDENTIALS_FILE, false);
			fileWriter.close();
			uiManager.showLoginUI();
			LogManager.log(username + " logged out");
		} catch (IOException e) {
			LogManager.log("An error occurred while logging out: " + e.getMessage());
		}

	}
}
