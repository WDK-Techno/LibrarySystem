package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.fxml.Initializable;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RemoveBookController implements Initializable {

    @FXML
    private TextArea bookDetails;

    @FXML
    private JFXButton checkButton;

    @FXML
    private TextField removeBookID;

    @FXML
    private JFXButton removeButton;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new DBHandler();
    }
    @FXML
    void checkBook(ActionEvent event) {
      String bookID;
      bookID=removeBookID.getText();

        connection = handler.getConnection();
        String query1="SELECT * FROM book WHERE BookID=?";
        try {
            pst=connection.prepareStatement(query1);
            pst.setString(1,bookID);

            ResultSet result = pst.executeQuery();

            int found=0;
            while(result.next()){
                found=found+1;

                String BookIDFromDB = result.getString("BookID");
                String BookNameFromDB = result.getString("BookName");
                String BookAuthorFromDB = result.getString("BookAuthor");
                String BookCategoryFromDB = result.getString("BookCategory");

                bookDetails.setText(
                        "BookID   : " + BookIDFromDB +"\n" +
                                "Name     : " + BookNameFromDB + "\n" +
                                "Author   : " + BookAuthorFromDB + "\n" +
                                "Category : " + BookCategoryFromDB + "\n\n" );
                System.out.println(
                        "BookID   : " + BookIDFromDB +"\n" +
                                "Name     : " + BookNameFromDB + "\n" +
                                "Author   : " + BookAuthorFromDB + "\n" +
                                "Category : " + BookCategoryFromDB + "\n\n");

            }
            if(found==1){
                System.out.println("Search completed.");
            }else{
                System.out.println("Incorrect Input.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book ID is not Found.");
                alert.show();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void removeBook(ActionEvent event) {
        String bookID,bookName,bookAuthor,bookCategory;

        bookID=removeBookID.getText();

        connection = handler.getConnection();
        String query2="DELETE FROM book WHERE BookID=?";
        try {
            pst = connection.prepareStatement(query2);
            pst.setString(1,bookID);

            pst.executeUpdate();
            System.out.println("Book is Deleted!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
