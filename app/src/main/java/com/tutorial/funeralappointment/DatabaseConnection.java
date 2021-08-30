package com.tutorial.funeralappointment;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String host = "192.168.0.157";//when running on localhost, put your local IP address as host.
        String username = "rusiru";
        String password = "rusiru";
        String port = "3306";//mysql port
        String dbName = "funeralappointment";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" +
                    dbName, username, password);
            return connection;
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}


/*Below code is for connecting through SSH tunnel*/

//    int lport = 21098;
//    String rhost = "ezack.info";
//    String host = "ezack.info";
//    int rport = 3306;
//    String user = "ezacafcu";
//    String password = "123Pula#456";
//    String dbuserName = "user";
//    String dbpassword = "komJb37m!L#O";
//    String url = "jdbc:mysql://198.54.115.100:" + lport + "/funeralsystem";
//    String driverName = "com.mysql.jdbc.Driver";
//    Connection conn = null;
//    Session session = null;
//        try {
//                //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
//                java.util.Properties config = new java.util.Properties();
//                config.put("StrictHostKeyChecking", "no");
//                JSch jsch = new JSch();
//                session = jsch.getSession(user, host, 22);
//                session.setPassword(password);
//                session.setConfig(config);
//                session.connect();
//                System.out.println("Connected");
//                int assinged_port = session.setPortForwardingL(lport, rhost, rport);
//                System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
//                System.out.println("Port Forwarded");
//
//                //mysql database connectivity
//                Class.forName(driverName).newInstance();
//                conn = DriverManager.getConnection(url, dbuserName, dbpassword);
//                System.out.println("Database connection established");
//                System.out.println("DONE");
//                return conn;
//                } catch (Exception e) {
//                e.printStackTrace();
//                } finally {
//                if (conn != null && !conn.isClosed()) {
//                System.out.println("Closing Database Connection");
//                conn.close();
//                }
//                if (session != null && session.isConnected()) {
//                System.out.println("Closing SSH Connection");
//                session.disconnect();
//                }
//                }