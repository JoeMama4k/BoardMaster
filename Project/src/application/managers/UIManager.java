package application.managers;

import java.io.IOException;

import application.controllers.GamesController;
import application.controllers.LoginController;
import application.controllers.SignUpController;
import application.controllers.games.TicTacToeController;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * The UIManager class manages the user interface of the application.
 */
public class UIManager {
	private Stage primaryStage;

	/**
	 * 
	 * Constructs a new UIManager with the specified primary stage.
	 * 
	 * @param primaryStage the primary stage of the application
	 */
	public UIManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// Set Icon
		Image icon = new Image(getClass().getResourceAsStream("/application/images/Taskbar Icon.png"));
		primaryStage.getIcons().add(icon);
		// Disable Resize Window
		primaryStage.setResizable(false);

	}

	/**
	 * 
	 * Shows the login user interface.
	 */
	public void showLoginUI() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/Login.fxml"));
			Parent loginRoot = loader.load();
			LoginController loginController = loader.getController();
			loginController.setUIManager(this);
			primaryStage.setTitle("Login");
			primaryStage.setScene(new Scene(loginRoot));
			primaryStage.show();
		} catch (IOException e) {
			LogManager.log("Couldn't show login UI " + e.getMessage());
		}
	}

	/**
	 * 
	 * Shows the sign-up user interface.
	 */
	public void showSignUpUI() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/SignUp.fxml"));
			Parent signUpRoot = loader.load();
			SignUpController signUpController = loader.getController();
			signUpController.setUIManager(this);
			primaryStage.setTitle("Sign Up");
			primaryStage.setScene(new Scene(signUpRoot));
			primaryStage.show();
		} catch (IOException e) {
			LogManager.log("Couldn't show sign-up UI " + e.getMessage());
		}
	}

	/**
	 * 
	 * Shows the games user interface.
	 */
	public void showGamesUI() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/Games.fxml"));
			Parent gamesRoot = loader.load();
			GamesController gamesController = loader.getController();
			gamesController.setUIManager(this);
			primaryStage.setTitle("Games");
			primaryStage.setScene(new Scene(gamesRoot));
			primaryStage.show();
		} catch (IOException e) {
			LogManager.log("Couldn't show games UI " + e.getMessage());
		}
	}

	// Games

	/**
	 * 
	 * Shows the Tic-Tac-Toe game user interface.
	 */
	public void showTicTacToeUI() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/games/TicTacToe.fxml"));
			Parent gamesRoot = loader.load();
			TicTacToeController tictactoeController = loader.getController();
			tictactoeController.setUIManager(this);
			primaryStage.setTitle("Tic-Tac-Toe");
			primaryStage.setScene(new Scene(gamesRoot));
			primaryStage.show();
		} catch (IOException e) {
			LogManager.log("Couldn't show tic-tac-toe UI " + e.getMessage());
		}
	}
}
