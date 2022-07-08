package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import library.librarysystem.DBConnection.DBHandler;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


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


        String collectOutPut;
        String store="";
        
        String author = authorInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM book WHERE BookAuthor LIKE ?";

        try {
            pst = connection.prepareStatement(getDetailsQuery);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1, "%" +author + "%");

            ResultSet result = pst.executeQuery();


            int found = 0;

            while (result.next()) {
                found = found + 1;
//
                String BookIDFromFB = result.getString("BookID");
                String BookNameFromDB = result.getString("BookName");
                String BookAuthorFromDB = result.getString("BookAuthor");
                String BookCategoryFromDB = result.getString("BookCategory");
                
                collectOutPut =
                        "BookID   : " + BookIDFromFB +"\n" +
                                "Name     : " + BookNameFromDB + "\n" +
                                "Author   : " + BookAuthorFromDB + "\n" +
                                "Category : " + BookCategoryFromDB + "\n";
                
                System.out.println(
                        "BookID   : " + BookIDFromFB +"\n" +
                                "Name     : " + BookNameFromDB + "\n" +
                                "Author   : " + BookAuthorFromDB + "\n" +
                                "Category : " + BookCategoryFromDB + "\n\n");


                store = store.concat(collectOutPut);
                store = store.concat( "-------------------------------------\n");
            }
                




            if (found >= 1){
                System.out.println("Search Completed");

                outPutTextArea.setText(store);

            }else {
                System.out.println("Incorrect Input");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Not Found");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void searchBookfromBookName(ActionEvent event) {



        String collectOutPut;
        String store="";

        String name = bookNameInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM book WHERE BookName LIKE ?";

        try {
            pst = connection.prepareStatement(getDetailsQuery);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,"%" + name + "%");

            ResultSet result = pst.executeQuery();


            int found = 0;

            while (result.next()) {
                found = found + 1;
//
                String BookIDFromFB = result.getString("BookID");
                String BookNameFromDB = result.getString("BookName");
                String BookAuthorFromDB = result.getString("BookAuthor");
                String BookCategoryFromDB = result.getString("BookCategory");


                collectOutPut =
                        "BookID   : " + BookIDFromFB +"\n" +
                        "Name     : " + BookNameFromDB + "\n" +
                        "Author   : " + BookAuthorFromDB + "\n" +
                        "Category : " + BookCategoryFromDB + "\n\n" ;

                System.out.println(
                        "BookID   : " + BookIDFromFB +"\n" +
                        "Name     : " + BookNameFromDB + "\n" +
                        "Author   : " + BookAuthorFromDB + "\n" +
                        "Category : " + BookCategoryFromDB + "\n\n") ;

                store = store.concat(collectOutPut);
                store = store.concat( "-------------------------------------\n");

            }
            if (found >= 1){
                System.out.println("Search Completed");
//
                outPutTextArea.setText(store);

            }else {
                System.out.println("Incorrect Input");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Not Found");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void emptyAuthorField(MouseEvent mouseEvent) {

        authorInput.setText("");
    }

    public void emptyBookField(MouseEvent mouseEvent) {

        bookNameInput.setText("");
    }

}
