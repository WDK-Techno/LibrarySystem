package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import library.librarysystem.DBConnection.DBHandler;
import library.librarysystem.Function.DateDifferent;
import library.librarysystem.Function.GetSettingValuesFromDB;
import library.librarysystem.Function.ShowErrorMessage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;


public class BookReturnController implements Initializable {

    @FXML
    private AnchorPane background;

    @FXML
    private TextField bookIdInput;

    @FXML
    private TextArea outputBookDetails;

    @FXML
    private TextArea outputUserDetails;

    @FXML
    private TextField overDueDateCountOutput;

    @FXML
    private TextField overdueChargeOutput;

    @FXML
    private JFXButton returnBookButton;

    @FXML
    private TextField userIdInput;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst1,pst2,pst3,pst;
    private ShowErrorMessage error;

    int foundbook = 0;
    int founduser = 0;
    String userID;

    int selectedBookID=0;

    Boolean bookIsIssued = false;
    Boolean userCanHandOverBooks = false;

    Boolean bookReadyToReturn = false;

    int[] userBorrowedBookIDs = new int[2];
    String[] issueDates = new String[2];



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userIdInput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                background.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });


    }


    @FXML
    void checkUserFromID(ActionEvent event) {


        //clear input other input fields
        bookIdInput.setText("");
        overdueChargeOutput.setText("");
        overDueDateCountOutput.setText("");

        userCanHandOverBooks = false;
        bookReadyToReturn = false;
        outputUserDetails.setText("");
        outputBookDetails.setText("");
        userID = userIdInput.getText();

        userBorrowedBookIDs[0]=0;
        userBorrowedBookIDs[1]=0;
        issueDates[0]=null;
        issueDates[1]=null;

        connection  = handler.getConnection();

        String getUserDetailsQuery = "SELECT * FROM user WHERE UserID = ?";
        String getIssuDetailsQuery = "SELECT * FROM book_issue WHERE UserID = ? AND Received = ?";
        String getBookDetailsQuery = "SELECT * FROM book WHERE BookID = ? OR BookID = ?";

        try {

            pst1 = connection.prepareStatement(getUserDetailsQuery);
            pst2 = connection.prepareStatement(getIssuDetailsQuery);
            pst3 = connection.prepareStatement(getBookDetailsQuery);


            pst1.setString(1,userID);

            pst2.setString(1,userID);
            pst2.setString(2,"No");

            ResultSet result = pst1.executeQuery();

            ResultSet result2 = pst2.executeQuery();


            //from user table
            founduser = 0;
            String userNameFromDB="";
            int userIDFromDB = 0;
            String nicFromDB ="";
            String genderFromDB ="";

            while (result.next()) {
                founduser = founduser + 1;
                userNameFromDB = result.getString("UserName");
                userIDFromDB = result.getInt("UserID");
                nicFromDB = result.getString("NIC");
                genderFromDB = result.getString("Gender");
            }


            //check user is existed or not
            if (founduser == 1){

                userIdInput.setText(""); //reset input feild text
                System.out.println("USER FOUND Successfull");

                //check user borrow books or not
                int foundBook = 0;
                userCanHandOverBooks = false;
                while (result2.next()){
                    userBorrowedBookIDs[foundBook] = result2.getInt("BookID");
                    issueDates[foundBook]=result2.getString("IssueDate");
                    foundBook = foundBook + 1;
                }

                if(foundBook >= 1){

                    System.out.println("User Can Hand Over Books");

                    //display user details
                    outputUserDetails.setText(
                            "User ID        :- " + userIDFromDB +"\n"+
                                    "User Name  :- " + userNameFromDB+"\n"+
                                    "NIC             :- "+ nicFromDB+"\n"+
                                    "Gender       :- "+genderFromDB+"\n");
                    System.out.println(
                            "User ID    :- " + userIDFromDB +"\n"+
                                    "User Name  :- " + userNameFromDB+"\n"+
                                    "NIC        :- "+ nicFromDB+"\n"+
                                    "Gender     :- "+genderFromDB+"\n");

                    //get details from book table acording to borrow books


                    pst3.setString(1, String.valueOf(userBorrowedBookIDs[0]));
                    pst3.setString(2, String.valueOf(userBorrowedBookIDs[1]));


                    ResultSet result3 = pst3.executeQuery();


                    String collectOutPut;
                    String store = "";
                    int count=0;
                    while (result3.next()) {
                        String BookIDFromFB = result3.getString("BookID");
                        String BookNameFromDB = result3.getString("BookName");
                        String BookAuthorFromDB = result3.getString("BookAuthor");
                        String BookCategoryFromDB = result3.getString("BookCategory");
                        if (userBorrowedBookIDs[count] != 0) {
                            collectOutPut =
                                    "BookID   : " + BookIDFromFB + "\n" +
                                            "Name     : " + BookNameFromDB + "\n" +
                                            "Author   : " + BookAuthorFromDB + "\n" +
                                            "Category : " + BookCategoryFromDB + "\n";

                            store = store.concat(collectOutPut);
                            store = store.concat("-------------------------------------\n");
                        }
                        count++;
                    }


                    outputBookDetails.setText(store);
                    System.out.println(store);
                    userCanHandOverBooks = true;


                }else {
                    System.out.println("User have not Barrow Any Book");
                    error.show("User have not Barrow Any Book");
                }

            }else {
                System.out.println("Check Again User ID !!");
                error.show("User ID Incorrect");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    @FXML
    void calculate(ActionEvent event) {

        bookReadyToReturn=false;

        //check whether input field empty or not
        if (bookIdInput.getText()==""){
            System.out.println("Input field Can't Be Empty");
            error.show("Input field Can't Be Empty");
        }
        else {


            selectedBookID =Integer.parseInt(bookIdInput.getText());
            if (userCanHandOverBooks) {
                boolean correctInput =false;
                int index =0;
                for(int i=0;i<2;i++){
                    if (selectedBookID==userBorrowedBookIDs[i]){
                        correctInput=true;
                        index=i;
                    }
                }
                if (correctInput) {
                    System.out.println("Issue Date : " + issueDates[index]);

                    DateDifferent diff = new DateDifferent();
                    int dateCount =diff.findDifferent(issueDates[index]);

                    overDueDateCountOutput.setText(String.valueOf(dateCount));

                    GetSettingValuesFromDB settings = new GetSettingValuesFromDB();
                    if (dateCount>settings.overDueDates){
                        System.out.println("Due Date is passed ");
                        overDueDateCountOutput.setBorder(new Border(new BorderStroke(RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

                        float overDueCharge = dateCount * settings.overDueCharge;
                        overdueChargeOutput.setText(String.valueOf(overDueCharge));

                    }else {

                        overDueDateCountOutput.setBorder(new Border(new BorderStroke(GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

                        overdueChargeOutput.setText("0.00");
                    }

                    bookReadyToReturn = true;
                    

                } else {
                    System.out.println("Incorrect Input,Select Only Above Book IDs");
                    error.show("Incorrect Input,Select Only Above Book IDs");
                }

            } else {
                System.out.println("Enter User ID first");
                error.show("Enter User ID first");
            }

        }

    }


    @FXML
    void returnBookProcess(ActionEvent event) {

        if (bookReadyToReturn){
            System.out.println("Processing");

           connection = handler.getConnection();

           String changeQuery = "UPDATE book_issue SET Received = ? , ReceivedDate = ? WHERE BookID = ?";

            try {
                pst = connection.prepareStatement(changeQuery);

                pst.setString(1,"Yes");
                pst.setString(2, String.valueOf(java.time.LocalDate.now()));
                pst.setString(3, String.valueOf(selectedBookID));

                pst.executeUpdate();

                System.out.println("Book Return Complete");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else {

            System.out.println("Cannot process!");
            error.show("Cannot process!");
        }

    }



}
