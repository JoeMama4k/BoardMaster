<--! no esta acabado i falta modificar la base de datos i no te he puesto ni paquete ni java-->
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicTacToe extends Application {
    private Button[][] cells;
    private char currentPlayer;
    private boolean gameOver;
    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        cells = new Button[3][3];
        currentPlayer = 'X';
        gameOver = false;

        GridPane gridPane = createGridPane();
        addButtonsToGrid(gridPane);

        connectToDatabase();

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(gridPane, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        return gridPane;
    }

    private void addButtonsToGrid(GridPane gridPane) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = createButton(row, col);
                gridPane.add(button, col, row);
                cells[row][col] = button;
            }
        }
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setPrefSize(100, 100);
        button.setOnAction(e -> makeMove(row, col));
        return button;
    }

    private void makeMove(int row, int col) {
        if (gameOver || !cells[row][col].getText().isEmpty()) {
            return;
        }

        cells[row][col].setText(Character.toString(currentPlayer));
        if (checkWin(row, col)) {
            gameOver = true;
            showResult("Player " + currentPlayer + " wins!");
            saveGameResult(currentPlayer);
        } else if (checkTie()) {
            gameOver = true;
            showResult("It's a tie!");
            saveGameResult(' ');
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkWin(int row, int col) {
        String mark = Character.toString(currentPlayer);

        // Check row
        if (cells[row][0].getText().equals(mark) &&
                cells[row][1].getText().equals(mark) &&
                cells[row][2].getText().equals(mark)) {
            return true;
        }

        // Check column
        if (cells[0][col].getText().equals(mark) &&
                cells[1][col].getText().equals(mark) &&
                cells[2][col].getText().equals(mark)) {
            return true;
        }

        // Check diagonals
        if (cells[0][0].getText().equals(mark) &&
                cells[1][1].getText().equals(mark) &&
                cells[2][2].getText().equals(mark)) {
            return true;
        }

        if (cells[0][2].getText().equals(mark) &&
                cells[1][1].getText().equals(mark) &&
                cells[2][0].getText().equals(mark)) {
            return true;
        }

        return false;
    }

    private boolean checkTie() {
        for (Button[] row : cells) {
            for (Button cell : row) {
                if (cell.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showResult(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void connectToDatabase() {
        String host = "sql7.freesqldatabase.com";
        String databaseName = "sql7622471";
        String username = "sql7622471";
        String password = "sEPS2ywYQW";
        int port = 3306;

        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    private void saveGameResult(char winner) {
        if (connection != null) {
            try {
                String query = "INSERT INTO game_results (winner) VALUES (?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, String.valueOf(winner));
                statement.executeUpdate();
                statement.close();
                System.out.println("Game result saved to the database.");
            } catch (SQLException e) {
                System.out.println("Failed to save game result to the database.");
                e.printStackTrace();
            }
        }
    }
}

<--!
    eliminar
en el seguent codi cal ferlo amb javafx i conectarlo amb la base de dades (mb els seguents dedes Host: sql7.freesqldatabase.com, Database name: sql7622471, Database user: sql7622471, Database password: sEPS2ywYQW, Port number: 3306, Account number: 774799)
tabe cal posar una bariable amb una imatge que representara la x i la o per el sibol dels jugadors. per ultim neteja el codi no utilitzat.
    ///_________________________________________\\\
package tasca2_metodes;

import java.util.Scanner;

/**
 * (*) Escriu un programa per jugar al tres en ratlla.
 * Dos jugadors, per torns, posen sobre un tauler de 3x3 un dels seus símbols, O
 * o X.
 * 
 * Quan un jugador ha col·locat una fitxa de manera que té tres fitxes alineades
 * verticalment, horitzontal o diagonal guanya la partida.
 * 
 * Si s'acaben les caselles buides i no s'ha arribat a cap alineament la partida
 * acaba en empat
 * 
 * -------------
 * | | | |
 * -------------
 * | | | |
 * -------------
 * | | | |
 * -------------
 * 
 * Entra una fila (0, 1, or 2) pel jugador X: 1
 * Entra una columna (0, 1, or 2) pel jugador X: 1
 * 
 * -------------
 * | | | |
 * -------------
 * | | X | |
 * -------------
 * | | | |
 * -------------
 * 
 * Entra una fila (0, 1, or 2) pel jugador O: 2
 * Entra una columna (0, 1, or 2) pel jugador O: 1
 * 
 * -------------
 * | | | |
 * -------------
 * | | X | O |
 * -------------
 * | | | |
 * -------------
 * 
 * .......
 * 
 * -------------
 * | X | | O |
 * -------------
 * | | X | O |
 * -------------
 * | | | X |
 * -------------
 * 
 * Ha guanyat el jugador X en 3 tirades.
 * 
 * Fes l'exercici amb una filosofia modular. Descomposa el problema principal en
 * problemes més petits. Per exemple podem tenir un mètode que pinti el tauler,
 * o un altre que comprovi si la posició triada és correcte
 * 
 * Programa el necessari perquè no hi puguin haver errors a l'entrar les dades.
 * Posa els missatges adequats perquè el jugador tingui clar què ha de fer en
 * tot moment.
 * 
 * @author Joan Carcellé Obradors
 * 
 */

public class Metodes3_3 {
  static char[][] board = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
  static byte row = 0;
  static  byte column = 0;
  static final Scanner input = new Scanner(System.in);
  static char currPlayer = 'O';
  static boolean fullBoard = false;
  static final String INPUTERRMSG = "Invalid input. Please enter two numbers between 0 and 2 with a space (e.g 2 0).";

  public static void main(String[] args) {
    System.out.println(
        "This program is a Tic Tac Toe game where two players take turns making moves. The game is played on a 3x3 board. The player who gets three of their symbols in a row (horizontally, vertically or diagonally) first wins. If the board is full and no player has won, the game ends in a tie which is called a 'scratch'. The program prompts the current player to make a move by entering the row and column they would like to play in. It then checks if the move is valid (i.e. if the position is already occupied or if the input is outside the board) and if it is not, it prompts the player to make another move. The program continues until a player wins or the game ends in a tie, then displays the final state of the board and the outcome of the game.\n");
    while (!checkWin()) {
      changePlayer();
      printBoard();
      askPlay();
    }
    input.close();
    printBoard();
    System.out.println(fullBoard ? "Scratch!" : "Player " + currPlayer + " Wins");
  }

  // Splitted checkWin method into 2 methods to reduce the Cognitive Complexity

  /**
   * 
   * Check if three characters are equal and not empty.
   * 
   * @param a the first character to compare.
   * @param b the second character to compare.
   * @param c the third character to compare.
   * @return true if the three characters are equal and not empty, false
   *         otherwise.
   */
  static boolean isWinningLine(char a, char b, char c) {
    return a == b && b == c && a != ' ';
  }

  /**
   * 
   * Check if there is a win or a tie.
   * 
   * @return true if there is a win or a tie, false otherwise.
   */
  static boolean checkWin() {
    // Check vertical alignment
    for (int row = 0; row < 3; row++) {
      // Check vertical alignment
      if (isWinningLine(board[0][row], board[1][row], board[2][row])) {
        return true;
      }
      // Check horizontal alignment
      if (isWinningLine(board[row][0], board[row][1], board[row][2])) {
        return true;
      }
    }

    // Check diagonal alignment
    if (isWinningLine(board[0][0], board[1][1], board[2][2])) {
      return true;
    }
    if (isWinningLine(board[0][2], board[1][1], board[2][0])) {
      return true;
    }

    // Check if the board is full for a tie
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
   * 
   * Print the Tic Tac Toe board.
   */
  static void printBoard() {
    System.out.println("-------------");
    for (int i = 0; i < 3; i++) {
      System.out.print("| ");
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j] + " | ");
      }
      System.out.println();
      System.out.println("-------------");
    }
  }

  /**
   * 
   * Ask the current player for their move.
   */
  static void askPlay() {
    
    do {
      System.out.print("Player " + currPlayer + " Make a Move (0, 1, or 2; e.g 0 1) : ");
      if (getInput()) {
        if (!isPosAlreadyUsed(row, column)) {
          System.out.println("This position is already occupied. Choose another one.");
        } else {
          break;
        }
      }
    } while (true);
    board[row][column] = currPlayer;
  }

  /**
   * Prompts the player for input and validates it.
   * 
   * @return {@code true} if the input of the user is valid, {@code false} otherwise.
   */
  static boolean getInput() {
    if (input.hasNextByte()) {
      row = input.nextByte();
      if (input.hasNextByte()) {
        column = input.nextByte();
        if (row > 2 || row < 0 || column > 2 || column < 0) {
          System.err.println(INPUTERRMSG);
          return false;
        }
        return true;
      }
    }
    System.err.println(INPUTERRMSG);
    return false;
  }

  /**
   * Change the current player.
   */
  static void changePlayer() {
    currPlayer = (currPlayer == 'X' ? 'O' : 'X');
  }

  /**
   * Check if the position is already used.
   * 
   * @param row    The row of the position
   * @param column The column of the position
   * @return `true` if the position is not used, `false` otherwise
   */
  static boolean isPosAlreadyUsed(byte row, byte column) {
    return board[row][column] == ' ';
  }
}

-->
