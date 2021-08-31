package com.tutorial.funeralappointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Appointment> getAppointments(String date, boolean isCancel) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        String query = "";
        try {
            if (!isCancel) {
                query = "SELECT " +
                        " CONCAT( C.FirstName, \" \", C.LastName ) AS CLIENTNAME, " +
                        " A.AppRefNo AS REFNO, " +
                        " A.Remark AS REMARK, " +
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
                        " DATE_FORMAT( A.Date, \"%Y-%c-%e\" ) = ? AND isCancel = 0;";
            } else {
                query = "SELECT " +
                        " CONCAT( C.FirstName, \" \", C.LastName ) AS CLIENTNAME, " +
                        " A.AppRefNo AS REFNO, " +
                        " A.Remark AS REMARK, " +
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
                        " DATE_FORMAT( A.Date, \"%Y-%c-%e\" ) = ? AND isCancel = 1;";
            }

            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, date);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setClientName(resultSet.getString("CLIENTNAME"));
                appointment.setRefNo(resultSet.getString("REFNO"));
                appointment.setTimeSlot(resultSet.getString("TIMESLOT"));
                appointment.setIsCancelled(resultSet.getInt("CANCELLED"));
                appointment.setDone(resultSet.getBoolean("DONE"));
                appointment.setCustId(resultSet.getString("CUSTID"));
                appointment.setApptDate(resultSet.getString("APPTDATE"));
                appointment.setEmail(resultSet.getString("EMAIL"));
                appointment.setMobile(resultSet.getString("MOBILE"));
                if (isCancel) {
                    appointment.setRemark(resultSet.getString("REMARK"));
                } else {
                    appointment.setRemark(null);
                }

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
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, refNo);
            statement.executeUpdate();

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

    public static int addRemark(String remark, String refNo) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            String query = "UPDATE appointment " +
                    "SET Remark = ? " +
                    "WHERE" +
                    " AppRefNo = ?";
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, remark);
            statement.setString(2, refNo);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
        return result;
    }

}