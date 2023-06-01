package application.controllers;

import javafx.scene.control.Label;

import application.managers.LoginManager;
import application.managers.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for the Games view.
 */
public class GamesController {

	@FXML
	private Label welcomeText;

	private UIManager uiManager;

	/**
	 * Welcomes the User.
	 */
	public void initialize() {
		welcomeText.setText("Welcome " + LoginManager.getCredentials()[0]);
	}

	/**
	 * Sets the UI manager for the controller.
	 *
	 * @param uiManager the UI manager to be set
	 */
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	/**
	 * Handles the click event for the Tic Tac Toe game button.
	 */
	@FXML
	private void handleTicTacToeClicked() {
		System.out.println("Tic Tac Toe");
		uiManager.showTicTacToeUI();
	}

	/**
	 * Handles the click event for the Connect 4 game button.
	 */
	@FXML
	private void handleConnect4Clicked() {
		System.out.println("Connect 4");
		showWIPInfo();
	}

	/**
	 * Handles the click event for the Checkers game button.
	 */
	@FXML
	private void handleCheckersClicked() {
		System.out.println("Checkers");
		showWIPInfo();
	}

	/**
     * Displays a work in progress information dialog.
     */
	private void showWIPInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Work In Progress");
		alert.setHeaderText(null);

		alert.setContentText("This game is currently Work In Progress");

		alert.showAndWait();
	}

	/**
     * Handles the log out action.
     */
	@FXML
	private void handleLogOut() {
		LoginManager.logout();
	}

}
