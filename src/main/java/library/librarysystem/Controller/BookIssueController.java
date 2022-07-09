package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;

public class BookIssueController implements Initializable {

    @FXML
    private AnchorPane backGround;
    @FXML
    private JFXButton bookIDcheck;

    @FXML
    private TextField bookIDinput;

    @FXML
    private TextArea bookInfoOutputTextArea;

    @FXML
    private JFXButton userIDCheck;

    @FXML
    private TextField userIDinput;

    @FXML
    private TextArea userInfoOutputTextArea;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler= new DBHandler();


        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userIDinput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

    }

    int foundbook = 0;
    int founduser = 0;
    @FXML
    public void issueBookfFomBookID(ActionEvent event) {

        String bookID = bookIDinput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM book WHERE BookID LIKE ?";

        try {
            pst = connection.prepareStatement(getDetailsQuery);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1, bookID);

            ResultSet result = pst.executeQuery();




            while (result.next()) {
                foundbook = foundbook + 1;
//
                String BookIDFromFB = result.getString("BookID");
                String BookNameFromDB = result.getString("BookName");
                String BookAuthorFromDB = result.getString("BookAuthor");
                String BookCategoryFromDB = result.getString("BookCategory");

                bookInfoOutputTextArea.setText(
                        "BookID   : " + BookIDFromFB +"\n" +
                                "Name     : " + BookNameFromDB + "\n" +
                                "Author   : " + BookAuthorFromDB + "\n" +
                                "Category : " + BookCategoryFromDB + "\n\n");

                System.out.println("BookID   : " + BookIDFromFB +"\n" +
                        "Name     : " + BookNameFromDB + "\n" +
                        "Author   : " + BookAuthorFromDB + "\n" +
                        "Category : " + BookCategoryFromDB + "\n\n");
            }
            if (foundbook == 1){
                System.out.println("Book FOUND Successfull");

//                System.out.println("Hello " + name);

            }else {
                System.out.println("Incorrect BookID");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Incorrect Book ID");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void issueBookfFomUserID(ActionEvent event) {

        String uID = userIDinput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM user WHERE UserID = ? ";

        try {
            pst = connection.prepareStatement(getDetailsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,uID);

            ResultSet result = pst.executeQuery();



            while (result.next()){
                founduser = founduser + 1;
                String userNameFromDB = result.getString("UserName");
                int userIDFromDB = result.getInt("UserID");
                String nicFromDB = result.getString("NIC");
                String genderFromDB = result.getString("Gender");

                userInfoOutputTextArea.setText(
                        "User ID        :- " + userIDFromDB +"\n"+
                                "User Name  :- " + userNameFromDB+"\n"+
                                "NIC             :- "+ nicFromDB+"\n"+
                                "Gender       :- "+genderFromDB+"\n");

                System.out.println(
                        "User ID    :- " + userIDFromDB +"\n"+
                                "User Name  :- " + userNameFromDB+"\n"+
                                "NIC        :- "+ nicFromDB+"\n"+
                                "Gender     :- "+genderFromDB+"\n");
            }
            if (founduser == 1){
                System.out.println("USER FOUND Successfull");
//                System.out.println("Hello " + name);

            }else {
                System.out.println("Check Again User ID !!");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("User ID Incorrect");
                alert.show();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void issueBook(ActionEvent event) {

        if (founduser==1 && foundbook==1){
            System.out.printf("Issued");
        }
        else {
            System.out.println("Check Again User ID and Book ID !!");
            //Genarate pop error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("User ID and Book ID Incorrect");
            alert.show();
        }

    }

    @FXML
    public void emptyUserID(MouseEvent event) {
        userIDinput.setText("");
    }

    @FXML
    public void emtyBookId(javafx.scene.input.MouseEvent event) {
        bookIDinput.setText("");
    }


}
