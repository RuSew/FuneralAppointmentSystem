package com.tutorial.funeralappointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Queries {

    private static DatabaseConnection dataSource;

    public static User getUser(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            String query = "SELECT UserId AS ID FROM user WHERE UserName = ? AND PASSWORD = ?;";
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user.setUserId(resultSet.getString("ID"));
                user.setUsername(username);
            }

        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception exception) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception exception) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception exception) {
                }
            }
        }
        return user;
    }

    public static ArrayList<Appointment> getAppointments(String date) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        try {
            String query = "SELECT " +
                    " CONCAT( C.FirstName, \" \", C.LastName ) AS CLIENTNAME, " +
                    " A.AppRefNo AS REFNO, " +
                    " DATE_FORMAT( A.TimeSlot, \"%h:%i %p\" ) AS TIMESLOT, " +
                    " A.isCancel AS CANCELLED, " +
                    " A.isDone AS DONE, " +
                    " A.CusId AS CUSTID, " +
                    " DATE_FORMAT(A.Date, \"%d %b %Y\") AS APPTDATE, " +
                    " C.Email AS EMAIL, " +
                    " C.Mobile AS MOBILE " +
                    "FROM " +
                    " appointment A " +
                    " INNER JOIN customer C ON A.CusId = C.CusId  " +
                    "WHERE " +
                    " DATE_FORMAT( A.Date, \"%Y-%c-%d\" ) = ?;";
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, date);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setClientName(resultSet.getString("CLIENTNAME"));
                appointment.setRefNo(resultSet.getString("REFNO"));
                appointment.setTimeSlot(resultSet.getString("TIMESLOT"));
                appointment.setCancelled(resultSet.getBoolean("CANCELLED"));
                appointment.setDone(resultSet.getBoolean("DONE"));
                appointment.setCustId(resultSet.getString("CUSTID"));
                appointment.setApptDate(resultSet.getString("APPTDATE"));
                appointment.setEmail(resultSet.getString("EMAIL"));
                appointment.setMobile(resultSet.getString("MOBILE"));

                appointmentList.add(appointment);
            }

        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception exception) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception exception) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception exception) {
                }
            }
        }
        return appointmentList;
    }

    public static void cancelAppointment(String refNo) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String query = "UPDATE appointment " +
                    "SET isCancel = 1 " +
                    "WHERE" +
                    " AppRefNo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, refNo);
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }

            }
        }
    }

    public static Appointment getCustomerDetails(String custId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Appointment appointment = new Appointment();
        try {
            String query = "SELECT " +
                    " C.Email AS EMAIL, " +
                    " A.AppRefNo AS REFNO, " +
                    " A.TimeSlot AS TIME, " +
                    " DATE_FORMAT( A.TimeSlot, \"%Y-%m-%d\" ) AS DATE  " +
                    "FROM " +
                    " customer C " +
                    " INNER JOIN appointment A ON A.CusId = C.CusId  " +
                    "WHERE " +
                    " C.CusId = ?  " +
                    " AND A.isCancel = 0";
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, custId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                appointment.setEmail(resultSet.getString("EMAIL"));
                appointment.setRefNo(resultSet.getString("REFNO"));
                appointment.setTimeSlot(resultSet.getString("TIME"));
                appointment.setApptDate(resultSet.getString("DATE"));
            }

        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception exception) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception exception) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception exception) {
                }
            }
        }
        return appointment;
    }
}