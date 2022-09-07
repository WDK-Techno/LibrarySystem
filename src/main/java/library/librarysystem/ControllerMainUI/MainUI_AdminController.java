package library.librarysystem.ControllerMainUI;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import library.librarysystem.LibraryApp;

import java.io.IOException;

public class MainUI_AdminController {

    @FXML
    private AnchorPane loadpagepane;


    Pane home;


    private void setNode (Node node){
        loadpagepane.getChildren().clear();
        loadpagepane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(800));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }



    @FXML
    void staffRegLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("StaffReg.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void systemSettingsLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("SystemSettings.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
