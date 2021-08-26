package com.tutorial.funeralappointment;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/funeral_appointment", "rusiru", "rusiru");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
