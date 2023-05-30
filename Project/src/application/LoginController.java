package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private void handleLoginButtonClicked() throws SQLException {
		String email = emailField.getText();
		String password = passwordField.getText();
		if (email.isEmpty() || password.isEmpty()) {
			// Display an error message
			System.out.println("Please enter both email and password.");
		} else {
			// Get all users in the DB
			ResultSet users = DatabaseManager.executeQuery("SELECT * FROM Users");

			String encryptedPassword = DatabaseManager.encryptPassword(password);

			// Check if the user exists
			while (users.next()) {
				if (users.getString("Email").equals(email) && users.getString("Password").equals(encryptedPassword)) {
					System.out.println("Login successful!");
					return;
				} else if (users.getString("Username").equals(email)
						&& users.getString("Password").equals(encryptedPassword)) {
					System.out.println("Login successful!");
					return;
				}
			}
			System.out.println("Wrong Username or Password ");
		}

	}

	@FXML
	private void handleSignUpButtonClicked() {
		SignUp signupPage = new SignUp();
		signupPage.show();
	}

}
