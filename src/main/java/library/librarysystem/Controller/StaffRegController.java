package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StaffRegController {

    @FXML
    private TextField passwordInput;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private TextField userNameInput;

    @FXML
    void registerStaff(ActionEvent event) {
        String name;
        String password;

       name = userNameInput.getText();
       password=passwordInput.getText();

        System.out.println("Hello " + name);
        System.out.println("Your password is " + password);

    }


}
