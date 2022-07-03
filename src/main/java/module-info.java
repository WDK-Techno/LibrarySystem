module library.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.jfoenix;


    opens library.librarysystem to javafx.fxml;
    exports library.librarysystem;
    exports library.librarysystem.Controller;
    opens library.librarysystem.Controller to javafx.fxml;
}