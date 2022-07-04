package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
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

public class LoginMainController implements Initializable{

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
    }



    @FXML
    void loginToSystem(ActionEvent event) {

        String name = userNameInput.getText();
        String pasword = passwordInput.getText();

        //database
        connection = handler.getConnection();

        String getDetailsQuery = "SELECT StaffID FROM Staff WHERE UserName = ? AND Password = ?";

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
            }
            if (found == 1){
                System.out.println("Loggin Successfull");
                System.out.println("Hello " + name);

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
