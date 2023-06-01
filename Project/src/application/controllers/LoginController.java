package application.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.DatabaseManager;
import application.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class LoginController {

	@FXML
	private TextField emailField;

	@FXML
	private Pane pane;
	
	@FXML
	private ImageView eye;
	
	@FXML
	private TextField clearTextPassword;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Text errorText;

	private UIManager uiManager;
	
	private ColorAdjust colorAdjust;


	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
		// Add an event handler to the pane to allow use of ENTER
		pane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				handleLoginButtonClicked();
			}
		});
		
		// Set up the color adjustment effect to make the eye darker
				colorAdjust = new ColorAdjust();
		        colorAdjust.setBrightness(-0.5);
	}

	@FXML
	private void handleLoginButtonClicked() {
		String email = emailField.getText();
		String password = passwordField.getText();

		if (email.isEmpty() || password.isEmpty()) {
			System.out.println("Please enter both email and password.");
			errorText.setText("Please enter both email and password.");
		} else {
			try {
				// Prepare the SQL query with parameters
				String sql = "SELECT * FROM Users WHERE (Email = ? OR Username = ?) AND Password = ?";
				PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql);
				statement.setString(1, email);
				statement.setString(2, email);
				statement.setString(3, DatabaseManager.encryptPassword(password));

				// Execute the query
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					System.out.println("Login successful!");

					// Open the games UI
					uiManager.showGamesUI();
				} else {
					errorText.setText("Wrong Username or Password.");
					System.out.println("Wrong Username or Password.");
				}

				// Close the statement and result set
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				errorText.setText("Oops! Something Went Wrong, Try again.");
			}
		}
	}

	@FXML
	private void handleSignUpButtonClicked() {
		uiManager.showSignUpUI();
	}
	
	@FXML
	private void showPassword() {
		passwordField.setVisible(false);
		clearTextPassword.setText(passwordField.getText());
		clearTextPassword.setVisible(true);
        eye.setEffect(colorAdjust);
    }
	@FXML
    private void hidePassword() {
		clearTextPassword.setText("");
		clearTextPassword.setVisible(false);
		passwordField.setVisible(true);
		eye.setEffect(null);
    }

}
