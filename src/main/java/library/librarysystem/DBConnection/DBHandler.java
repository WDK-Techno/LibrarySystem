package library.librarysystem.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Configs {

    Connection dbconnection;

    public Connection getConnection(){

        String connectionString = "jdbc:mysql://"+Configs.dbhost +":"+Configs.dbport+"/"+Configs.dbname;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            dbconnection = DriverManager.getConnection(connectionString,Configs.dbuser,Configs.dbpassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dbconnection;
    }

}
