package application.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.DatabaseManager;
import application.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SignUpController {

	@FXML
	private TextField emailField;

	@FXML
	private Pane pane;

	@FXML
	private TextField usernameField;
	
	@FXML
	private ImageView eye;
	
	@FXML
	private ImageView eye2;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField passwordField2;
	
	@FXML
	private TextField clearTextPassword;
	
	@FXML
	private TextField clearTextPassword2;

	@FXML
	private Text errorText;

	private UIManager uiManager;
	
	private ColorAdjust colorAdjust;

	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
		// Add an event handler to the pane to allow use of ENTER
		pane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				handleSignUpButtonClicked();
			}
		});
		
		// Set up the color adjustment effect to make the eye darker
		colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);

       
	}

	@FXML
	private void handleSignUpButtonClicked() {
		try {
			// Retrieve the email, username, and password entered by the user
			String email = emailField.getText();
			String username = usernameField.getText();
			String password = passwordField.getText();
			String password2 = passwordField2.getText();

			if (email.isEmpty() || username.isEmpty() || password.isEmpty() || password2.isEmpty()) {
				System.out.println("Please, fill all the fields.");
				errorText.setText("Please, fill all the fields.");
				return;
			}

			if (!isValidEmail(email)) {
				System.out.println("Please, enter a valid email address");
				errorText.setText("Please, enter a valid email address");
				return;
			}
			
			if (username.length() > 16) {
				System.out.println("Username too long (max 16 characters)");
				errorText.setText("Username too long (max 16 characters)");
				return;
			}

			if (!Objects.equals(password, password2)) {
				System.out.println("Passwords didn't match. Try again.");
				errorText.setText("Passwords didn't match. Try again.");
				return;
			}

			// Prepare the SQL query with parameters
			String sql = "SELECT * FROM Users WHERE Email = ? OR Username = ? LIMIT 1";
			PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, username);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getString("Email").equalsIgnoreCase(email)) {
					System.err.println("Email already in use");
					errorText.setText("Email already in use");
				} else if (resultSet.getString("Username").equalsIgnoreCase(username)) {
					System.err.println("Username already in use");
					errorText.setText("Username already in use");
				}
			} else {
				boolean userCreated = DatabaseManager.createUser(email, username, password);
				if (userCreated)
					uiManager.showGamesUI();
			}

			// Close the statement and result set
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			errorText.setText("Oops! Something Went Wrong, Try again.");
		}
	}

	@FXML
	private void handleLoginButtonClicked() {
		uiManager.showLoginUI();
	}

	public boolean isValidEmail(String email) {
		// Regular expression pattern for an email
		String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		// Check if the email matches the pattern
		return email.matches(emailPattern);
	}
	@FXML
	private void showPassword1() {
		passwordField.setVisible(false);
		clearTextPassword.setText(passwordField.getText());
		clearTextPassword.setVisible(true);
        eye.setEffect(colorAdjust);
    }
	@FXML
    private void hidePassword1() {
		clearTextPassword.setText("");
		clearTextPassword.setVisible(false);
		passwordField.setVisible(true);
		eye.setEffect(null);
    }
	
	@FXML
	private void showPassword2() {
		passwordField2.setVisible(false);
		clearTextPassword2.setText(passwordField2.getText());
		clearTextPassword2.setVisible(true);
		 eye2.setEffect(colorAdjust);
    }
	
	@FXML
    private void hidePassword2() {
		clearTextPassword2.setText("");
		clearTextPassword2.setVisible(false);
		passwordField2.setVisible(true);
		 eye2.setEffect(null);
    }
	
	
	
}

