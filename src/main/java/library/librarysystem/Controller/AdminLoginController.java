package library.librarysystem.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;

public class AdminLoginController implements Initializable {

    @FXML
    private AnchorPane backGround;


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField userInputPassword;


    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userInputPassword.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });


    }




    @FXML
    void adminLogin(ActionEvent event) {
        String password;
        password = userInputPassword.getText();

        if(password==""){
            //Genarate pop error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Input Can't Be Empty");
            alert.show();
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