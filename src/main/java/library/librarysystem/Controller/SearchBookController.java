package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Handler;

public class SearchBookController implements Initializable {

    @FXML
    private TextField authorInput;

    @FXML
    private JFXButton authorSearchBtn;

    @FXML
    private TextField bookNameInput;

    @FXML
    private JFXButton bookSearchBtn;

    @FXML
    private TextArea outPutTextArea;


    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler= new DBHandler();
    }


    @FXML
    void searchBookFromAuthor(ActionEvent event) {


    }

    @FXML
    void searchBookfromBookName(ActionEvent event) {
        String name = bookNameInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM book WHERE BookName LIKE"+"'"+"%"+"?"+"'";

        try {
            pst = connection.prepareStatement(getDetailsQuery);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,name);
            ResultSet result = pst.executeQuery();


            int found = 0;

            while (result.next()) {
                found = found + 1;
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

        outPutTextArea.setText("Hello WDK : " + name);

    }


}
