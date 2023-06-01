module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    opens application to javafx.graphics, javafx.fxml;
    opens application.controllers to javafx.fxml;
    opens application.controllers.games to javafx.fxml;

    exports application.controllers;
    exports application.controllers.games;
}
