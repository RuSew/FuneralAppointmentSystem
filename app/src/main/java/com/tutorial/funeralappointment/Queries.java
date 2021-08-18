package com.tutorial.funeralappointment;

public class Queries {
    public static void getUser(){
        String query = "SELECT UserId FROM user WHERE UserName = 'Admin' AND PASSWORD = '123';";
        DatabaseConnection con = new DatabaseConnection();
        try{
            con.connect();

        }catch (Exception e){

        }
    }
}
