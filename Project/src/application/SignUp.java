package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUp {
	protected void show() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
			Scene scene = new Scene(root, 617, 497);
			Stage signupStage = new Stage();
			signupStage.setResizable(false);
			signupStage.setScene(scene);
			signupStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
