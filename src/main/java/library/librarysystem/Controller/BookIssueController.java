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


    int foundbook = 0;
    int founduser = 0;
    String bookID;
    String userID;
    Boolean bookCanIssue;
    Boolean userCanGetBook;

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




    @FXML
    public void checkIssueFomBookID(ActionEvent event) {
        bookInfoOutputTextArea.setText("");
        bookID = bookIDinput.getText();

        connection = handler.getConnection();

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



            foundbook =0;
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

                bookIDinput.setText("");//reset input field text

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

        //get Details from book_issue Table
        String getIssuDetailsQuery = "SELECT * FROM book_issue WHERE bookID = ? AND Received = ?";

        try {
            pst = connection.prepareStatement(getIssuDetailsQuery);

            pst.setString(1,bookID);
            pst.setString(2,"No");

            ResultSet result2 = pst.executeQuery();

            int found = 0;
            bookCanIssue = false;
            while (result2.next()){
                found = found + 1;
            }
            if(found == 0){
                bookCanIssue = true;
                System.out.println("Book Can Issue");
            }else {
                System.out.println("Book Can not Issue");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void checkIssueFromUserID(ActionEvent event) {
        userInfoOutputTextArea.setText("");
        userID = userIDinput.getText();


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
                System.out.println("User Can get Book");

            }else {
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
            //Genarate pop error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("User ID and Book ID Incorrect");
            alert.show();
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
