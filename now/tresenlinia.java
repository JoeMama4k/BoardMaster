<--! no esta acabado i falta modificar la base de datos i no te he puesto ni paquete ni java-->
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
public class TresEnRatlla extends Application {
    private Button[][] caselles;
    private int torn;
    private Connection conn;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        caselles = new Button[3][3];
        torn = 1;
        GridPane gridPane = crearTauler();
        connectarBaseDades();
        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setTitle("Tres en Ratlla");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private GridPane crearTauler() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                Button casella = new Button();
                casella.setMinSize(100, 100);
                casella.setOnAction(e -> marcarCasella(casella));
                caselles[fila][columna] = casella;
                gridPane.add(casella, columna, fila);
            }
        }
        return gridPane;
    }
    private void marcarCasella(Button casella) {
        if (!casella.getText().isEmpty()) {
            return;
        }
        int fila = GridPane.getRowIndex(casella);
        int columna = GridPane.getColumnIndex(casella);

        if (torn == 1) {
            casella.setText("X");
        } else {
            casella.setText("O");
        }
        if (comprovarGuanyador(fila, columna)) {
            mostrarAlerta("Guanyador", "Jugador " + torn + " ha guanyat!");
            reiniciarJoc();
        } else if (comprovarEmpat()) {
            mostrarAlerta("Empat", "El joc ha acabat en empat.");
            reiniciarJoc();
        } else {
            torn = (torn == 1) ? 2 : 1;
        }
    }
    private boolean comprovarGuanyador(int fila, int columna) {
        String marca = (torn == 1) ? "X" : "O";
        // Comprovar fila
        if (caselles[fila][0].getText().equals(marca) && caselles[fila][1].getText().equals(marca) && caselles[fila][2].getText().equals(marca)) {
            return true;
        }
        // Comprovar columna
        if (caselles[0][columna].getText().equals(marca) && caselles[1][columna].getText().equals(marca) && caselles[2][columna].getText().equals(marca)) {
            return true;
        }
        // Comprovar diagonals
        if (caselles[0][0].getText().equals(marca) &&  caselles[1][1].getText().equals(marca) && caselles[2][2].getText().equals(marca)) {
            return true;
        }
        if (caselles[0][2].getText().equals(marca) && caselles[1][1].getText().equals(marca) && caselles[2][0].getText().equals(marca)) {
            return true;
        }
        return false;
    }
    private boolean comprovarEmpat() {
        for (Button[] fila : caselles) {
            for (Button casella : fila) {
                if (casella.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    private void reiniciarJoc() {
        for (Button[] fila : caselles) {
            for (Button casella : fila) {
                casella.setText("");
            }
        }
    }
    private void connectarBaseDades() {
        String url = "jdbc:mariadb://localhost:3306/nom_de_la_base_dades";
        String usuari = "usuari";
        String contrasenya = "contrasenya";

        try {
            conn = DriverManager.getConnection(url, usuari, contrasenya);
            System.out.println("Connexió a la base de dades establerta.");
        } catch (SQLException e) {
            System.err.println("Error en connectar a la base de dades: " + e.getMessage());
        }
    }
    private void mostrarAlerta(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
    @Override
    public void stop() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connexió a la base de dades tancada.");
            }
        } catch (SQLException e) {
            System.err.println("Error en tancar la connexió a la base de dades: " + e.getMessage());
        }
    }
}

<--!
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
