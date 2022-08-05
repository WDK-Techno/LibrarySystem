package library.librarysystem.ControllerMainUI;

import com.jfoenix.controls.JFXButton;
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

public class MainUI_ManageController {

    @FXML
    private JFXButton addBookBtn;

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
    void addBookLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("AddBook.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addUserLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("UserReg.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void removeBookLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("RemoveBook.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void sendMailLoad(ActionEvent event) {

        try {
            home = FXMLLoader.load(LibraryApp.class.getResource("OverDueDateUser.fxml"));
            setNode(home);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
