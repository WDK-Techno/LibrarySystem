package library.librarysystem.Controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import library.librarysystem.LibraryApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {

    @FXML
    private AnchorPane loadpagepane;


    Pane home;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadChildPage();

    }



    private void setNode (Node node){
        loadpagepane.getChildren().clear();
        loadpagepane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void loadChildPage(){
        try {


           home = FXMLLoader.load(LibraryApp.class.getResource("OverdueDateUser.fxml"));

            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
