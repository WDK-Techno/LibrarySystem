package library.librarysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApp.class.getResource("LoginMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(false);
        stage.setResizable(true);// avoid reesize window
        stage.setFullScreen(false);

    }

    public static void main(String[] args) {
        launch();
    }
}