package library.librarysystem.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class SearchBookController {

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

    @FXML
    void searchBookFromAuthor(ActionEvent event) {

    }

    @FXML
    void searchBookfromBookName(ActionEvent event) {
        String name = bookNameInput.getText();
        outPutTextArea.setText("Hello WDK : " + name);

    }

}
