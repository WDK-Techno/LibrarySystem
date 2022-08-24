package library.librarysystem.Controller;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import library.librarysystem.DBConnection.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import library.librarysystem.Function.ShowErrorMessage;
import library.librarysystem.LibraryApp;

public class AdminLoginController implements Initializable {

    @FXML
    private BorderPane backGround;

    @FXML
    private AnchorPane loadpagepane;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField userInputPassword;


    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    private ShowErrorMessage error;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userInputPassword.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });


    }

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
    void adminLogin(ActionEvent event) {
        String password;
        password = userInputPassword.getText();

        if(password==""){
            error.show("Input Can not be Empty");
            System.out.println("Input Can not be Empty");
        }else{
            connection = handler.getConnection();

            String adminQuery = "SELECT * FROM staff WHERE  UserName = ?";

            try {
                pst = connection.prepareStatement(adminQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                pst.setString(1, "admin");

                ResultSet result = pst.executeQuery();

                boolean found = false;
                String userPasswordFromDB="";
                while (result.next()) {

                    userPasswordFromDB = result.getString("Password");
                }
                if (password.equals(userPasswordFromDB)) {
                    found = true;

                    System.out.println("Loggin Successfull");


                    try {
                        home = FXMLLoader.load(LibraryApp.class.getResource("SystemSettings.fxml"));
                        setNode(home);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }




                }else{
                    System.out.println("Incorrect Password");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Password");
                    alert.show();

                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}