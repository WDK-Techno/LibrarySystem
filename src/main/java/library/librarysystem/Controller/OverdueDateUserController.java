package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import javafx.fxml.Initializable;
import library.librarysystem.DBConnection.DBHandler;
import library.librarysystem.Function.ShowErrorMessage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OverdueDateUserController implements Initializable {

    @FXML
    private AnchorPane backGround;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private JFXButton getDetailsButton;

    @FXML
    private TextField inputDate;

    @FXML
    private JFXButton sendEmailButton;

    @FXML
    private TextArea typeEmail;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    private ShowErrorMessage error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        inputDate.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    @FXML
    void getOverdueDateUsers(ActionEvent event) {

        String enteredDate;
        enteredDate=inputDate.getText();

        if(enteredDate==""){
            error.show("Input Can not be Empty");
            System.out.println("Input Can not be Empty");
        }else{
            connection = handler.getConnection();

            String collectDetails;
            String output="";

            String newQuery="SELECT * FROM book_issue WHERE IssueDate<=? AND Received=?" ;
            try {
                pst=connection.prepareStatement(newQuery);
                pst.setString(1,enteredDate);
                pst.setString(2,"No");

                ResultSet result = pst.executeQuery();

                while (result.next()){

                    String UserIDFromDB = result.getString("UserID");
                    String BookIDFromDB = result.getString("BookID");
                    String IssueDateFromDB = result.getString("IssueDate");

                    collectDetails=
                            "UserID   : " + UserIDFromDB +"\n" +
                                    "BookID    : " + BookIDFromDB + "\n" +
                                    "Book Issue Date   : " + IssueDateFromDB + "\n" ;

                    System.out.println(
                            "User ID   : " + UserIDFromDB +"\n" +
                                    "Book ID    : " + BookIDFromDB+ "\n" +
                                    "Book Issue Date : " + IssueDateFromDB + "\n\n");



                    output = output.concat(collectDetails);
                    output= output.concat( "-------------------------------------\n");


                }

                 detailsTextArea.setText(output);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    } @FXML
    public void sendEmail(ActionEvent event) {

    }

}
