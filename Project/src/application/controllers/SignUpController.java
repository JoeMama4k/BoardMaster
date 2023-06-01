package application.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.managers.DatabaseManager;
import application.managers.LogManager;
import application.managers.LoginManager;
import application.managers.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Controller class for the Sign Up view.
 */
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

    @FXML
    private ProgressBar passwordStrengthBar;

    private UIManager uiManager;

    private ColorAdjust colorAdjust;

    /**
     * Sets the UI manager for the controller.
     *
     * @param uiManager the UI manager to be set
     */
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

    /**
     * Handles the sign up button click event.
     */
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
                if (userCreated) {
                    LoginManager.saveCredentials(username, password);
                    uiManager.showGamesUI();
                }

            }

            // Close the statement and result set
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            LogManager.log("There was an error while checking if the account is available " + e.getMessage());
            errorText.setText("Oops! Something Went Wrong, Try again.");
        }
    }

    /**
     * Handles the login button click event.
     */
    @FXML
    private void handleLoginButtonClicked() {
        uiManager.showLoginUI();
    }

    /**
     * Validates if the given email is in a valid format.
     *
     * @param email the email to be validated
     * @return true if the email is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        // Regular expression pattern for an email
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }

    /**
     * Shows the first password in clear text.
     */
    @FXML
    private void showPassword1() {
        passwordField.setVisible(false);
        clearTextPassword.setText(passwordField.getText());
        clearTextPassword.setVisible(true);
        eye.setEffect(colorAdjust);
    }

    /**
     * Hides the first password.
     */
    @FXML
    private void hidePassword1() {
        clearTextPassword.setText("");
        clearTextPassword.setVisible(false);
        passwordField.setVisible(true);
        eye.setEffect(null);
    }

    /**
     * Shows the second password in clear text.
     */
    @FXML
    private void showPassword2() {
        passwordField2.setVisible(false);
        clearTextPassword2.setText(passwordField2.getText());
        clearTextPassword2.setVisible(true);
        eye2.setEffect(colorAdjust);
    }

    /**
     * Hides the second password.
     */
    @FXML
    private void hidePassword2() {
        clearTextPassword2.setText("");
        clearTextPassword2.setVisible(false);
        passwordField2.setVisible(true);
        eye2.setEffect(null);
    }

    /**
     * Calculates and updates the strength of the password.
     */
    @FXML
    private void getPasswordStrength() {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            passwordStrengthBar.setVisible(false);
        } else {
            passwordStrengthBar.setVisible(true);
            double strength = calculatePasswordStrength(password);
            updatePasswordStrengthBar(strength);
        }
    }

    /**
     * Updates the password strength progress bar.
     *
     * @param strength the strength value of the password
     */
    private void updatePasswordStrengthBar(double strength) {
        passwordStrengthBar.setProgress(strength);

        // Update the color of the password strength bar based on the strength value
        if (strength < 0.3) {
            passwordStrengthBar.setStyle("-fx-accent: red;");
        } else if (strength < 0.7) {
            passwordStrengthBar.setStyle("-fx-accent: yellow;");
        } else {
            passwordStrengthBar.setStyle("-fx-accent: green;");
        }
    }

    /**
     * Calculates the strength of the password.
     *
     * @param password the password to calculate the strength for
     * @return the strength of the password as a value between 0.0 and 1.0
     */
    private double calculatePasswordStrength(String password) {
        int length = password.length();
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int numberCount = 0;
        int symbolCount = 0;

        // Count the occurrences of uppercase letters, lowercase letters, numbers, and symbols
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            } else if (Character.isDigit(c)) {
                numberCount++;
            } else {
                symbolCount++;
            }
        }

        // Calculate the strength based on the factors
        double lengthFactor = Math.min(length / 10.0, 1.0);
        double uppercaseFactor = uppercaseCount > 0 ? 1.0 : 0.0;
        double lowercaseFactor = lowercaseCount > 0 ? 1.0 : 0.0;
        double numberFactor = numberCount > 0 ? 1.0 : 0.0;
        double symbolFactor = symbolCount > 0 ? 1.0 : 0.0;

        return (lengthFactor + uppercaseFactor + lowercaseFactor + numberFactor + symbolFactor) / 5.0;
    }

}
