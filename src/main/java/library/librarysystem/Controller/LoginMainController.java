package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.librarysystem.DBConnection.DBHandler;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.scene.layout.AnchorPane;
import library.librarysystem.Function.ShowErrorMessage;
import library.librarysystem.LibraryApp;

public class LoginMainController implements Initializable{

    @FXML
    private AnchorPane backGround;
    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    private ShowErrorMessage error;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();
        error = new ShowErrorMessage();

        //chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        userNameInput.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });


    }



    @FXML
    void loginToSystem(ActionEvent event) {

        String name = userNameInput.getText();
        String pasword = passwordInput.getText();

        if(name=="" || pasword==""){
            error.show("Input Can not be Empty");
            System.out.println("Input Can not be Empty");
        }else{
            //database
            connection = handler.getConnection();

            String getDetailsQuery = "SELECT * FROM staff WHERE UserName = ? AND Password = ?";

            try {
                pst = connection.prepareStatement(getDetailsQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                pst.setString(1,name);
                pst.setString(2,pasword);

                ResultSet result = pst.executeQuery();

                int found = 0;

                while (result.next()){
                    found = found + 1;
                    String userNameFromDB = result.getString("UserName");
                    String userPasswordFromDB = result.getString("Password");
                    int userIDFromDB = result.getInt("StaffID");

                    System.out.println("User :--> " + userNameFromDB + " ID :- " + userIDFromDB + " Password :- " + userPasswordFromDB );

                    //print user name to text file
                    try {
//                        //write to the file
//                        FileWriter userFileWrite = new FileWriter("userLoginRecord.txt");
//
//                        userFileWrite.write(userNameFromDB);
//                        userFileWrite.close();
//                        System.out.println("complete userName write to file");
                        String readData = "";
                        String store ="";
                        File userLogin = new File("userLoginRecord.txt");


//                        check, file is already created or not
                        if (userLogin.createNewFile()){
                            System.out.println(userLogin.getName() + "is created");

                            FileWriter writeFile = new FileWriter("userLoginRecord.txt");

                            //get current time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();

                            //write username and dateTime to the file
                            writeFile.write(dtf.format(now)+ "\t\t" + userNameFromDB );
                            writeFile.close();


                        }
                        else{
                            System.out.println("Already Exist");

                            //read previous data from text file
                            Scanner getLoginRecord = new Scanner(userLogin);
                            while (getLoginRecord.hasNextLine()){
                                store = getLoginRecord.nextLine();
                                readData = readData.concat(store);
                                readData = readData.concat("\n");

                            }

                            //get current time
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();

                            readData = readData.concat(dtf.format(now)+ "\t\t" + userNameFromDB );
                            //write data to file
                            FileWriter writeFile = new FileWriter("userLoginRecord.txt");
                            writeFile.write(readData);
                            writeFile.close();
                        }




                    }catch (IOException e){
                        System.out.println("Create new file");
//                        e.printStackTrace();
                        File userFile = new File("userLoginRecord.txt");
                        FileWriter userFileWrite = new FileWriter("userLoginRecord.txt");

                        userFileWrite.write(userNameFromDB);
                        userFileWrite.close();
                        System.out.println("complete userName write to file");

                    }


                }
                if (found == 1){
                    System.out.println("Loggin Successfull");

                    //clear fields
                    passwordInput.setText("");
                    userNameInput.setText("");
//                System.out.println("Hello " + name);



                    loginButton.getScene().getWindow().hide();

                    Stage mainPage = new Stage();
                    Parent root = FXMLLoader.load(LibraryApp.class.getResource("MainUI.fxml"));
                    Scene scene = new Scene(root);
                    mainPage.setScene(scene);
                    mainPage.show();
                    mainPage.setResizable(false);
                    mainPage.setMaximized(true);



                }else {
                    System.out.println("Incorrect Input");
                    //Genarate pop error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("User Name or Password Incorrect");
                    alert.show();
                }



            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}