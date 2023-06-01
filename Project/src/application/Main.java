package application;

import application.managers.LoginManager;
import application.managers.UIManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * 
	 * The start method is called when the application is starting.
	 * 
	 * @param primaryStage the primary stage of the application
	 */
	@Override
	public void start(Stage primaryStage) {
		LoginManager.uiManager = new UIManager(primaryStage);
		String[] credentials = LoginManager.getCredentials();
		if (credentials != null && (LoginManager.login(credentials[0], credentials[1]))) {
			return;
		}
		LoginManager.uiManager.showSignUpUI();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
