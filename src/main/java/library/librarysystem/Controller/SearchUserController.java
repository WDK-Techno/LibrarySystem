package library.librarysystem.Controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;

public class SearchUserController implements Initializable {
    @FXML
    private BorderPane backGround;

    @FXML
    private TextField contactNoInput;

    @FXML
    private JFXButton contactNoSearchBtn1;

    @FXML
    private JFXButton nameSearchBtn;

    @FXML
    private TextField nicInput;

    @FXML
    private JFXButton nicSearchBtn;

    @FXML
    private TextArea outPutTextArea;

    @FXML
    private TextField userNameInput;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler= new DBHandler();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userNameInput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });



    }
    public void searchUserFromNIC(ActionEvent event) {

        String nic = nicInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM user WHERE NIC = ? ";

        try {
            pst = connection.prepareStatement(getDetailsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,nic);

            ResultSet result = pst.executeQuery();

            int found = 0;

            while (result.next()){
                found = found + 1;
                String userNameFromDB = result.getString("UserName");
                int userIDFromDB = result.getInt("UserID");
                String DOBfromDB = result.getString("DOB");
                String nicFromDB = result.getString("NIC");
                String genderFromDB = result.getString("Gender");
                String contactFromDB = result.getString("ContactNo");
                String emailFromDB = result.getString("Email");
                String addressFromDB = result.getString("Address");

                outPutTextArea.setText(
                        "User ID        :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B          :- "+ DOBfromDB +"\n"+
                        "NIC             :- "+ nicFromDB+"\n"+
                        "Gender       :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                        "Email           :- "+emailFromDB+"\n"+
                        "Address       :- "+ addressFromDB+"\n");

                System.out.println(
                        "User ID    :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B      :- "+ DOBfromDB +"\n"+
                        "NIC        :- "+ nicFromDB+"\n"+
                        "Gender     :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                         "Email     :- "+emailFromDB+"\n"+
                         "Address   :- "+ addressFromDB+"\n");
            }
            if (found == 1){
                System.out.println("USER FOUND Successfull");
//                System.out.println("Hello " + name);

            }else {
                System.out.println("Check Again NIC number!!");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("NIC Incorrect");
                alert.show();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void searchUserFromContactNo(ActionEvent event) {

        String contact = contactNoInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM user WHERE ContactNo = ? ";

        try {
            pst = connection.prepareStatement(getDetailsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1,contact);

            ResultSet result = pst.executeQuery();

            int found = 0;

            while (result.next()){
                found = found + 1;
                String userNameFromDB = result.getString("UserName");
                int userIDFromDB = result.getInt("UserID");
                String DOBfromDB = result.getString("DOB");
                String nicFromDB = result.getString("NIC");
                String genderFromDB = result.getString("Gender");
                String contactFromDB = result.getString("ContactNo");
                String emailFromDB = result.getString("Email");
                String addressFromDB = result.getString("Address");

                outPutTextArea.setText(
                        "User ID        :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B          :- "+ DOBfromDB +"\n"+
                        "NIC             :- "+ nicFromDB+"\n"+
                        "Gender       :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                        "Email           :- "+emailFromDB+"\n"+
                         "Address       :- "+ addressFromDB+"\n");

                System.out.println(
                        "User ID    :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B      :- "+ DOBfromDB +"\n"+
                        "NIC        :- "+ nicFromDB+"\n"+
                        "Gender     :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                        "Email      :- "+emailFromDB+"\n"+
                         "Address   :- "+ addressFromDB+"\n");
            }
            if (found == 1){
                System.out.println("USER FOUND Successfull");
//                System.out.println("Hello " + name);

            }else {
                System.out.println("Check Again Contact number!!");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Contact Number Incorrect");
                alert.show();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void searchUserfromName(ActionEvent event) {

        String print ;
        String store = "";
        String name = userNameInput.getText();

        connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM user WHERE UserName LIKE ?";

        try {
            pst = connection.prepareStatement(getDetailsQuery);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            pst.setString(1, "%"+ name + "%");

            ResultSet result = pst.executeQuery();

            int found = 0;

            while (result.next()){
                found = found + 1;
                String userNameFromDB = result.getString("UserName");
                int userIDFromDB = result.getInt("UserID");
                String DOBfromDB = result.getString("DOB");
                String nicFromDB = result.getString("NIC");
                String genderFromDB = result.getString("Gender");
                String contactFromDB = result.getString("ContactNo");
                String emailFromDB = result.getString("Email");
                String addressFromDB = result.getString("Address");

//                outPutTextArea.setText(
//                        "User ID        :- " + userIDFromDB +"\n"+
//                                "User Name  :- " + userNameFromDB+"\n"+
//                                "D.O.B          :- "+ DOBfromDB +"\n"+
//                                "NIC             :- "+ nicFromDB+"\n"+
//                                "Gender       :- "+genderFromDB+"\n"+
//                                "Contact No :- "+contactFromDB+"\n"+
//                                "Email           :- "+emailFromDB+"\n"+
//                                "Address       :- "+ addressFromDB+"\n");

                print = "User ID        :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B          :- "+ DOBfromDB +"\n"+
                        "NIC             :- "+ nicFromDB+"\n"+
                        "Gender       :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                        "Email           :- "+emailFromDB+"\n"+
                        "Address       :- "+ addressFromDB+"\n";

//                store = print + "\n";
                store = store.concat(print);
                store = store.concat( "-------------------------------------\n");

                System.out.println(
                        "User ID    :- " + userIDFromDB +"\n"+
                        "User Name  :- " + userNameFromDB+"\n"+
                        "D.O.B      :- "+ DOBfromDB +"\n"+
                        "NIC        :- "+ nicFromDB+"\n"+
                        "Gender     :- "+genderFromDB+"\n"+
                        "Contact No :- "+contactFromDB+"\n"+
                        "Email      :- "+emailFromDB+"\n"+
                        "Address    :- "+ addressFromDB+"\n");
            }
            if (found >= 1){
                System.out.println("USER FOUND Successfull");
//                System.out.println("Hello " + name);
                outPutTextArea.setText(store);

            }else {
                System.out.println("Check Again User Name!");
                //Genarate pop error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("User Name Incorrect");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void mouseClickName(MouseEvent mouseEvent) {
        contactNoInput.setText("");
        nicInput.setText("");
    }

    public void mouseClickNIC(MouseEvent mouseEvent) {
        contactNoInput.setText("");
        userNameInput.setText("");

    }

    public void mouseClickContact(MouseEvent mouseEvent) {
        nicInput.setText("");
        userNameInput.setText("");

    }
}
