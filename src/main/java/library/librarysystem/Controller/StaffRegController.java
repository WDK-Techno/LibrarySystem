package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import library.librarysystem.DBConnection.DBHandler;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StaffRegController implements Initializable {

    @FXML
    private AnchorPane backGround;
    @FXML
    private TextField passwordInput;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private TextField userNameInput;


    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //database
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
    void registerStaff(ActionEvent event) {
        String name;
        String password;

        name = userNameInput.getText();
        password=passwordInput.getText();

        //SAVING DATA TO DATABASE

        connection = handler.getConnection();

        String insertQuery = "INSERT INTO Staff (UserName,Password)" + "VALUES (?,?)"; //create string including our query

        try {
            pst = connection.prepareStatement(insertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            //replace name and password for "values(?,?)" in inertQuery String
            pst.setString(1,name);
            pst.setString(2,password);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Hello " + name);
        System.out.println("Your password is " + password);

    }

}
