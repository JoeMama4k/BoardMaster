package application.controllers;

import application.managers.LoginManager;
import application.managers.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

/**
 * Controller class for the Login view.
 */
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
                handleLoginButtonClicked();
            }
        });

        // Set up the color adjustment effect to make the eye darker
        colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
    }

    /**
     * Handles the login button click event.
     */
    @FXML
    private void handleLoginButtonClicked() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Please enter both email and password.");
            errorText.setText("Please enter both email and password.");
        } else {
            LoginManager.login(email, password, errorText);
        }
    }

    /**
     * Handles the sign up button click event.
     */
    @FXML
    private void handleSignUpButtonClicked() {
        uiManager.showSignUpUI();
    }

    /**
     * Shows the password in clear text.
     */
    @FXML
    private void showPassword() {
        passwordField.setVisible(false);
        clearTextPassword.setText(passwordField.getText());
        clearTextPassword.setVisible(true);
        eye.setEffect(colorAdjust);
    }

    /**
     * Hides the password.
     */
    @FXML
    private void hidePassword() {
        clearTextPassword.setText("");
        clearTextPassword.setVisible(false);
        passwordField.setVisible(true);
        eye.setEffect(null);
    }

}
