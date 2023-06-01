package application.controllers.games;

import java.util.Optional;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javafx.scene.control.ButtonType;

import javax.swing.JPanel;

import application.managers.LogManager;
import application.managers.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TicTacToeController {

	private UIManager uiManager;

	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	@FXML
	private ImageView a1;
	@FXML
	private ImageView a2;
	@FXML
	private ImageView a3;
	@FXML
	private ImageView b1;
	@FXML
	private ImageView b2;
	@FXML
	private ImageView b3;
	@FXML
	private ImageView c1;
	@FXML
	private ImageView c2;
	@FXML
	private ImageView c3;

	private char[][] board = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
	private char currPlayer = 'X';
	private boolean fullBoard = false;
	private boolean alreadyWon = false;

	/**
	 * Initializes the controller to handle moves.
	 */
	public void initialize() {
		// Set up MouseClicked events for each cell
		a1.setOnMouseClicked(event -> handleMove(a1, 0, 0));
		a2.setOnMouseClicked(event -> handleMove(a2, 0, 1));
		a3.setOnMouseClicked(event -> handleMove(a3, 0, 2));
		b1.setOnMouseClicked(event -> handleMove(b1, 1, 0));
		b2.setOnMouseClicked(event -> handleMove(b2, 1, 1));
		b3.setOnMouseClicked(event -> handleMove(b3, 1, 2));
		c1.setOnMouseClicked(event -> handleMove(c1, 2, 0));
		c2.setOnMouseClicked(event -> handleMove(c2, 2, 1));
		c3.setOnMouseClicked(event -> handleMove(c3, 2, 2));
	}

	/**
	 * Handles a move by the player.
	 * 
	 * @param imageView the ImageView representing the cell clicked by the player
	 * @param row       the row index of the cell
	 * @param column    the column index of the cell
	 */
	private void handleMove(ImageView imageView, int row, int column) {

		if (alreadyWon)
			return;
		if (!isPosAlreadyUsed(row, column)) {
			return;
		}

		imageView.setImage(getPlayerImage());
		board[row][column] = currPlayer;

		if (checkWin()) {
			// Create an alert when a player wins
			alreadyWon = true;
			String message = fullBoard ? "Scratch!" : "Player " + currPlayer + " Wins";

			LogManager.log(message);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Winner");
			alert.setHeaderText(null);

			// Set an thumbnail next to the text

			ImageView thumbnail = new ImageView(getPlayerImage());
			thumbnail.setFitWidth(imageView.getFitWidth());
			thumbnail.setFitHeight(imageView.getFitHeight());

			alert.setGraphic(thumbnail);

			alert.setContentText(message);

			// Create custom buttons
			ButtonType goBackButton = new ButtonType("Go Back");
			ButtonType playAgainButton = new ButtonType("Play Again");

			// Add custom buttons to the dialog
			alert.getButtonTypes().setAll(goBackButton, playAgainButton);

			// Display the alert dialog and wait for user input
			Optional<ButtonType> result = alert.showAndWait();

			// Process user input
			if (result.isPresent()) {
				if (result.get() == playAgainButton) {
					uiManager.showTicTacToeUI();
				} else if (result.get() == goBackButton) {
					goBack();
				}
			}
		} else {
			changePlayer();
		}
	}

	/**
	 * Returns the Image representing the current player.
	 * 
	 * @return the Image representing the current player
	 */
	private javafx.scene.image.Image getPlayerImage() {
		if (currPlayer == 'X') {
			return new javafx.scene.image.Image(
					getClass().getResourceAsStream("/application/images/games/tictactoe/cross.png"));
		} else {
			return new javafx.scene.image.Image(
					getClass().getResourceAsStream("/application/images/games/tictactoe/nought.png"));
		}
	}

	/**
	 * Checks if the given position on the board is already used.
	 * 
	 * @param row    the row index of the position
	 * @param column the column index of the position
	 * @return true if the position is already used, false otherwise
	 */
	private boolean isPosAlreadyUsed(int row, int column) {
		return board[row][column] == ' ';
	}

	/**
	 * Checks if a winning line is formed on the board.
	 * 
	 * @param a the first character
	 * @param b the second character
	 * @param c the third character
	 * @return true if a winning line is formed, false otherwise
	 */
	private boolean isWinningLine(char a, char b, char c) {
		return a == b && b == c && a != ' ';
	}

	/**
	 * Checks if there is a winning condition on the board.
	 * 
	 * @return true if there is a winning condition, false otherwise
	 */
	private boolean checkWin() {
		for (int row = 0; row < 3; row++) {
			if (isWinningLine(board[row][0], board[row][1], board[row][2])) {
				return true;
			}
			if (isWinningLine(board[0][row], board[1][row], board[2][row])) {
				return true;
			}
		}

		if (isWinningLine(board[0][0], board[1][1], board[2][2])) {
			return true;
		}
		if (isWinningLine(board[0][2], board[1][1], board[2][0])) {
			return true;
		}

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (board[row][column] == ' ') {
					return false;
				}
			}
		}
		fullBoard = true;
		return true;
	}
	
	
	/**
     * Changes the current player.
     */
	private void changePlayer() {
		currPlayer = (currPlayer == 'X' ? 'O' : 'X');
	}

	/**
     * Goes back to the Games UI.
     */
	@FXML
	private void goBack() {
		uiManager.showGamesUI();
	}

}
