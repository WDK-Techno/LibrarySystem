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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import library.librarysystem.DBConnection.DBHandler;
import library.librarysystem.Function.ShowErrorMessage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;

public class BookIssueController implements Initializable {

    @FXML
    private BorderPane backGround;
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

    @FXML
    private Rectangle bookElegible;

    @FXML
    private Rectangle bookNotElegible;

    @FXML
    private Rectangle userElegible;

    @FXML
    private Rectangle userNotElegible;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    private ShowErrorMessage error;

    int foundbook = 0;
    int founduser = 0;
    String bookID;
    String userID;
    Boolean bookCanIssue;
    Boolean userCanGetBook;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        userElegible.setWidth(0);
//        userNotElegible.setWidth(0);
//        bookElegible.setWidth(0);
//        bookNotElegible.setWidth(0);

        handler= new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userIDinput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

    }




    @FXML
    public void checkIssueFomBookID(ActionEvent event) {
        bookInfoOutputTextArea.setText("");
        bookID = bookIDinput.getText();

        bookElegible.setFill(GRAY);
        bookInfoOutputTextArea.setBorder(new Border(new BorderStroke(GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

        connection = handler.getConnection();

        if (bookID==""){
            error.show("Input Can not be Empty");
            System.out.println("Input Can not be Empty");
        }
        else {

            //get details from Book Table
            String getDetailsQuery = "SELECT * FROM book WHERE BookID LIKE ?";

            try {
                pst = connection.prepareStatement(getDetailsQuery);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                pst.setString(1, bookID);

                ResultSet result = pst.executeQuery();


                foundbook = 0;
                while (result.next()) {
                    foundbook = foundbook + 1;
//
                    String BookIDFromFB = result.getString("BookID");
                    String BookNameFromDB = result.getString("BookName");
                    String BookAuthorFromDB = result.getString("BookAuthor");
                    String BookCategoryFromDB = result.getString("BookCategory");

                    bookInfoOutputTextArea.setText(
                            "BookID   : " + BookIDFromFB + "\n" +
                                    "Name     : " + BookNameFromDB + "\n" +
                                    "Author   : " + BookAuthorFromDB + "\n" +
                                    "Category : " + BookCategoryFromDB + "\n\n");

                    System.out.println("BookID   : " + BookIDFromFB + "\n" +
                            "Name     : " + BookNameFromDB + "\n" +
                            "Author   : " + BookAuthorFromDB + "\n" +
                            "Category : " + BookCategoryFromDB + "\n\n");
                }
                if (foundbook == 1) {


                    bookIDinput.setText("");//reset input field text

                    System.out.println("Book FOUND Successfull");

//                System.out.println("Hello " + name);

                } else {

                    bookElegible.setFill(RED);
                    bookInfoOutputTextArea.setBorder(new Border(new BorderStroke(RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

                    System.out.println("Incorrect BookID");
                    error.show("Incorrect Book ID");

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //get Details from book_issue Table
            String getIssuDetailsQuery = "SELECT * FROM book_issue WHERE bookID = ? AND Received = ?";

            try {
                pst = connection.prepareStatement(getIssuDetailsQuery);

                pst.setString(1, bookID);
                pst.setString(2, "No");

                ResultSet result2 = pst.executeQuery();

                int found = 0;
                bookCanIssue = false;
                while (result2.next()) {
                    found = found + 1;
                }
                if (found == 0) {
                    bookCanIssue = true;
                    bookElegible.setFill(GREEN);
                    bookInfoOutputTextArea.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                    System.out.println("Book Can Issue");
                } else {
                    bookElegible.setFill(RED);
                    bookInfoOutputTextArea.setBorder(new Border(new BorderStroke(RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                    System.out.println("Book Can not Issue");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @FXML
    public void checkIssueFromUserID(ActionEvent event) {
        userInfoOutputTextArea.setText("");
        userID = userIDinput.getText();

        userElegible.setFill(GRAY);
        userInfoOutputTextArea.setBorder(new Border(new BorderStroke(GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

        connection = handler.getConnection();


        String getDetailsQuery = "SELECT * FROM user WHERE UserID = ?";

        try {
            pst = connection.prepareStatement(getDetailsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,userID);

            ResultSet result = pst.executeQuery();


            founduser =0;
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

                userIDinput.setText(""); //reset input feild text

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

        //get Details from book_issue Table
        String getIssuDetailsQuery = "SELECT * FROM book_issue WHERE UserID = ? AND Received = ?";

        try {
            pst = connection.prepareStatement(getIssuDetailsQuery);

            pst.setString(1,userID);
            pst.setString(2,"No");

            ResultSet result2 = pst.executeQuery();

            int found = 0;
            userCanGetBook = false;
            while (result2.next()){
                found = found + 1;
            }
            if(found < 2){
                userCanGetBook = true;
                userElegible.setFill(GREEN);
                userInfoOutputTextArea.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                System.out.println("User Can get Book");

            }else {
                userElegible.setFill(RED);
                userInfoOutputTextArea.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                System.out.println("User Can not get Book");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void issueBook(ActionEvent event) {

        if (founduser==1 && foundbook==1 && bookCanIssue && userCanGetBook){
            System.out.printf("Issued");

            System.out.println("UserID IN :" + userID);
            System.out.println("BookID IN :" + bookID);

            connection = handler.getConnection();



            //Insert Data to DB
            String insertDataQuary = "INSERT INTO book_issue(UserID,BookID,IssueDate,Received) VALUES (?,?,?,?)";

            try {
                pst = connection.prepareStatement(insertDataQuary);

                pst.setString(1,userID);
                pst.setString(2,bookID);
                pst.setString(3, String.valueOf(java.time.LocalDate.now()));
                pst.setString(4,"No");

                pst.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if (foundbook !=1 || founduser != 1){
            System.out.println("Check Again User ID and Book ID !!");
            
            error.show("User ID and Book ID Incorrect");

        }else if (!bookCanIssue){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Book Already Issued!");
            alert.show();
        } else if (!userCanGetBook) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("User Already Borrow 2 Books!");
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
