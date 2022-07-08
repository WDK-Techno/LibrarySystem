package library.librarysystem.Controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import javafx.fxml.Initializable;
import library.librarysystem.DBConnection.DBHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SystemSettingsController implements Initializable {
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

        connection =handler.getConnection();

        String newquery="SELECT * FROM settings";

        try {
            pst = connection.prepareStatement(newquery);
            //pst.setString(1,charge);

            ResultSet result= pst.executeQuery();

            while(result.next()){
                Integer chargefromDB=result.getInt("chargeUnit");
                Integer issueTimefromDB=result.getInt("issueTime");


                System.out.println("Overdue Charge (Per Day): " +chargefromDB);
                System.out.println("Book Issue Time Period: " +issueTimefromDB);
                overdueCharge.setText(chargefromDB.toString());
                issueTimeInput.setText(issueTimefromDB.toString());


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void changeSystem(ActionEvent event) {
          String chargeUnit;
          String issueTime;

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
