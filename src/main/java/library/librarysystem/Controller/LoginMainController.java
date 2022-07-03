package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginMainController {

    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    @FXML
    void loginToSystem(ActionEvent event) {

        String name = userNameInput.getText();
        System.out.println("System Login Success");
        System.out.println(name);

    }

}
