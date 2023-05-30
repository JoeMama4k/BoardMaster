package application;

import java.sql.ResultSet;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

	@FXML
	private TextField emailField;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField passwordField2;

	@FXML
	private void handleSignUpButtonClicked() {
		boolean alreadyInUse = false;
		try {
			// Retrieve the email, username, and password entered by the user
			String email = emailField.getText();
			String username = usernameField.getText();
			String password = passwordField.getText();
			String password2 = passwordField2.getText();
			
			if (!password.equals(password2)) {
				System.out.println("Passwords don't match");
				return;
			}

			ResultSet users = DatabaseManager.executeQuery("SELECT * FROM Users");

			while (users.next()) {
				if (users.getString("Email").equalsIgnoreCase(email)) {
					System.out.println("Email already in use");
					alreadyInUse = true;
					return;
				}
				if (users.getString("Username").equalsIgnoreCase(username)) {
					System.out.println("Username already in use");
					alreadyInUse = true;
					return;
				}
			}

			if (!alreadyInUse) {
				DatabaseManager.createUser(email, username, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
