package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.fxml.Initializable;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserRegController implements Initializable {

    @FXML
    private JFXButton registerButton;

    @FXML
    private TextField outputUserID;

    @FXML
    private TextField userAddressInput;

    @FXML
    private TextField userBirthInput;

    @FXML
    private TextField userContacInput;

    @FXML
    private TextField userEmailInput;

    @FXML
    private TextField userGenderInput;

    @FXML
    private TextField userNicInput;

    @FXML
    private TextField usernameInput;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;
    private PreparedStatement pst2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new DBHandler();


    }
    @FXML
    void registerUser(ActionEvent event) {

        String name,birth,NIC,gender,email,address, contact;

        name= usernameInput.getText();
        birth=userBirthInput.getText();
        NIC=userNicInput.getText();
        gender=userGenderInput.getText();
        contact=userContacInput.getText();
        email=userEmailInput.getText();
        address=userAddressInput.getText();

        connection = handler.getConnection();

        String query="INSERT INTO user(UserName,DOB,NIC,Gender,ContactNo,Email,Address)" + "VALUES (?,?,?,?,?,?,?)";
        String getquery="SELECT * FROM user WHERE NIC=?";
        try {
            pst=connection.prepareStatement(query);
            pst2=connection.prepareStatement(getquery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            pst.setString(1,name);
            pst.setString(2,birth);
            pst.setString(3,NIC);
            pst.setString(4,gender);
            pst.setString(5,contact);
            pst.setString(6,email);
            pst.setString(7,address);


            pst.executeUpdate();

            pst2.setString(1,NIC);

            ResultSet result = pst2.executeQuery();

            while(result.next()){
                int userIDfromDB=result.getInt("UserID");
                System.out.println("User ID: " +userIDfromDB);
                outputUserID.setText("User ID: " +userIDfromDB);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User Details");
        System.out.println("Name: " +name);
        System.out.println("Birth Date: " +birth);
        System.out.println("NIC: " +NIC);
        System.out.println("Gender: " +gender);
        System.out.println("Contact No: " +contact);
        System.out.println("Email: " +email);
        System.out.println("Address: " +address);

    }

}
