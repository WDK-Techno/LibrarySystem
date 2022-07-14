package library.librarysystem.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import library.librarysystem.DBConnection.DBHandler;
import javafx.scene.layout.AnchorPane;
import library.librarysystem.Function.ShowErrorMessage;

public class AddBookController implements Initializable {
    @FXML
    private BorderPane backGround;
    @FXML
    private JFXButton AddBookButton;

    @FXML
    private TextField Author;

    @FXML
    private TextField BookID;



    @FXML
    private TextField BookName;

    @FXML
    private TextField Category;


   private DBHandler handler;

   private Connection connection;

   private PreparedStatement pst,pst2;


   private ShowErrorMessage error;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler = new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        BookName.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

    }

    @FXML
    void Bookadd(ActionEvent event) {
        String bookName,author,category;

        bookName = BookName.getText();
        author = Author.getText();
        category=Category.getText();


        if(bookName==""||author==""||category==""){
            error.show("Input Can not be Empty");
            System.out.println("Input Can not be Empty");
        }
        else {
            connection = handler.getConnection();

            String insertQurrey = "INSERT INTO book(BookName,BookAuthor,BookCategory)"+ "VALUES(?,?,?)";

            String  getIdQuerry= "SELECT * FROM book WHERE BookName = ? ";


            try {
                pst = connection.prepareStatement(insertQurrey);
                pst2 = connection.prepareStatement(getIdQuerry);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                pst.setString(1,bookName);
                pst.setString(2,author);
                pst.setString(3,category);

                pst.executeUpdate();


                pst2.setString(1,bookName);

                ResultSet result = pst2.executeQuery();

                while (result.next()){


                    String bookNameFromDb = result.getString("BookName");
                    int bookIdFromDb= result.getInt("BookID");

                    System.out.println("Book name :" +bookNameFromDb);
                    System.out.println("Book ID :"+ bookIdFromDb);

                    BookID.setText( bookNameFromDb+"\t Book ID : "+bookIdFromDb);

                }




            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
