package com.tutorial.funeralappointment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connect()
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/funeral_appointment", "rusiru", "rusiru123");
            return con;
        } catch (Exception e) {
            System.out.println("inter.DBConnect.connect()");
        }
        return null;
    }
}
