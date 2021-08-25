package com.tutorial.funeralappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        setTitle("Rusiru");
        //getActionBar().setIcon(R.drawable.logout);
    }
}