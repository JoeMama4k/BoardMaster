package application.controllers;

import application.UIManager;
import javafx.fxml.FXML;
public class GamesController {

	
	private UIManager uiManager;

    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
    
	@FXML
	private void handleTicTacToeClicked() {
		System.out.println("Tic Tac Toe");
		uiManager.showTicTacToeUI();
	}
	
	@FXML
	private void handleConnect4Clicked() {
		System.out.println("Connect 4");
	}
	
	@FXML
	private void handleCheckersClicked() {
		System.out.println("Checkers");
	}
	
	 

}
