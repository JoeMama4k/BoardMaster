<--! no esta acabado i falta modificar la base de datos-->
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
        if (caselles[fila][0].getText().equals(marca) &&
            caselles[fila][1].getText().equals(marca) &&
            caselles[fila][2].getText().equals(marca)) {
            return true;
        }

        // Comprovar columna
        if (caselles[0][columna].getText().equals(marca) &&
            caselles[1][columna].getText().equals(marca) &&
            caselles[2][columna].getText().equals(marca)) {
            return true;
        }

        // Comprovar diagonals
        if (caselles[0][0].getText().equals(marca) &&
            caselles[1][1].getText().equals(marca) &&
            caselles[2][2].getText().equals(marca)) {
            return true;
        }

        if (caselles[0][2].getText().equals(marca) &&
            caselles[1][1].getText().equals(marca) &&
            caselles[2][0].getText().equals(marca)) {
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
