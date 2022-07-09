package library.librarysystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {

    @FXML
    private Button AddLoggingButton;

    @FXML
    private TextField UserInputPassword;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();

    }




    @FXML
    void userLogin(ActionEvent event) {
        String password;
        password = UserInputPassword.getText();

        connection = handler.getConnection();

        String adminQuery = "SELECT * FROM Staff WHERE  UserName = ?";

        try {
            pst = connection.prepareStatement(adminQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            pst.setString(1, "admin");

            ResultSet result = pst.executeQuery();

            boolean found = false;

            while (result.next()) {

                String userPasswordFromDB = result.getString("Password");
            }
            if (password == "Password") {
                found = true;
            System.out.println("Loggin Successfull");

        }else{
            System.out.println("System Logging Failed");
        }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}