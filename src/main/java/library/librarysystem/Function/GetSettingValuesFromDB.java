package library.librarysystem.Function;

import library.librarysystem.DBConnection.DBHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSettingValuesFromDB {


    public float overDueCharge;
    public int overDueDates;

    public GetSettingValuesFromDB(){

        DBHandler handler = new DBHandler();

        Connection connection = handler.getConnection();

        String getDetailsQuery = "SELECT * FROM settings";
        try {
            PreparedStatement pst = connection.prepareStatement(getDetailsQuery);

            ResultSet result = pst.executeQuery();

            while(result.next()){

                overDueCharge =result.getFloat("chargeUnit");
                overDueDates=result.getInt("issueTime");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
