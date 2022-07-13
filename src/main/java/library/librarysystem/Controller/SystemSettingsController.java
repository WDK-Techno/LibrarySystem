package library.librarysystem.Controller;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import javafx.fxml.Initializable;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import library.librarysystem.Function.GetSettingValuesFromDB;

public class SystemSettingsController implements Initializable{
    @FXML
    private AnchorPane backGround;

    @FXML
    private JFXButton changeButton;

    @FXML
    private TextField issueTimeInput;

    @FXML
    private TextField overdueCharge;

    private DBHandler handler;
    private Connection connection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String charge=overdueCharge.getText();
        handler = new DBHandler();

//        chanage staring focuse from first input field to other one.
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        overdueCharge.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                backGround.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });




        //get Details from DB
        GetSettingValuesFromDB settings = new GetSettingValuesFromDB();
        System.out.println("Overdue Charge (Per Day): " + settings.overDueCharge);
        System.out.println("Book Issue Time Period: " +settings.overDueDates);
        overdueCharge.setText(Float.toString(settings.overDueCharge));
        issueTimeInput.setText(Integer.toString(settings.overDueDates));

    }
    @FXML
    void changeSystem(ActionEvent event) {
          String chargeUnit;
          String issueTime;

          chargeUnit=overdueCharge.getText();
          issueTime=issueTimeInput.getText();

          if(chargeUnit=="" || issueTime==""){
              //Genarate pop error
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setHeaderText(null);
              alert.setContentText("Input Can't Be Empty");
              alert.show();
          }

          chargeUnit=overdueCharge.getText();
          issueTime=issueTimeInput.getText();

          connection = handler.getConnection();

          String changequery="INSERT INTO settings (chargeUnit,issueTime)"+"VALUES (?,?)";
        try {
            pst=connection.prepareStatement(changequery);

            pst.setString(1,chargeUnit);
            pst.setString(2,issueTime);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("New Overdue Charge: " +chargeUnit);
        System.out.println("New Book Issue Time Period: " +issueTime);

    }

}
