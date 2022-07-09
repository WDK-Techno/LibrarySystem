package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;

public class LoginMainController implements Initializable{

    @FXML
    private AnchorPane backGround;
    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();
        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userNameInput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });


    }



    @FXML
    void loginToSystem(ActionEvent event) {

        String name = userNameInput.getText();
        String pasword = passwordInput.getText();

        if(name=="" || pasword==""){
            //Genarate pop error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Input Can't Be Empty");
            alert.show();
        }else{
            //database
            connection = handler.getConnection();

            String getDetailsQuery = "SELECT * FROM Staff WHERE UserName = ? AND Password = ?";

            try {
                pst = connection.prepareStatement(getDetailsQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                pst.setString(1,name);
                pst.setString(2,pasword);

                ResultSet result = pst.executeQuery();

                int found = 0;

                while (result.next()){
                    found = found + 1;
                    String userNameFromDB = result.getString("UserName");
                    String userPasswordFromDB = result.getString("Password");
                    int userIDFromDB = result.getInt("StaffID");

                    System.out.println("User :--> " + userIDFromDB + " ID :- " + userIDFromDB + " Password :- " + userPasswordFromDB );
                }
                if (found == 1){
                    System.out.println("Loggin Successfull");
//                System.out.println("Hello " + name);

                }else {
                    System.out.println("Incorrect Input");
                    //Genarate pop error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("User Name or Password Incorrect");
                    alert.show();
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }



}
