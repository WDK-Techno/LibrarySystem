package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

        String getDetailsQuery = "SELECT * FROM book WHERE BookName LIKE"+"\'"+"%"+"?"+"\'";

        try {
            pst = connection.prepareStatement(getDetailsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        outPutTextArea.setText("Hello WDK : " + name);

    }


}
