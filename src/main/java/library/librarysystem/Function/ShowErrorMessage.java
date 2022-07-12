package library.librarysystem.Function;

import javafx.scene.control.Alert;

public class ShowErrorMessage {

    public static void show(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }
}
