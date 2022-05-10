package Labo.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.xdevapi.Statement;

public class DatabaseConnection {
    protected static Connection databaseLink;

    public static Connection getConnection(){
        String databaseName = "materiels";
        String databaseUser = "Mohammed";
        String databasePassword = "Materiels12304@";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

}
