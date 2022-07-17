package library.librarysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApp.class.getResource("MainUI.fxml"));
  //      Scene scene = new Scene(fxmlLoader.load(), 800, 600);
   //     Scene scene = new Scene(fxmlLoader.load(), 1425, 975);



//        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080); //for logginMain
        Scene scene = new Scene(fxmlLoader.load()); //for logginMain
//        Parent root = FXMLLoader.load(getClass().getResource("staffReg.fxml")); //2nd method
//        Group root = new Group(stage);
        stage.setTitle("Library Management System");
//        stage.setScene(new Scene(root)); //2nd method
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        stage.setResizable(false);// avoid reesize window
        stage.setFullScreen(false);

    }


}