package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private UIManager uiManager;

    @Override
    public void start(Stage primaryStage) {
        uiManager = new UIManager(primaryStage);
        uiManager.showSignUpUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
