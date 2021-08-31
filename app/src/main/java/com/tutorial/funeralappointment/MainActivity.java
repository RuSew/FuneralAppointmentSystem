package com.tutorial.funeralappointment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

//Splash screen
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigate to login activity
        new Handler().postDelayed(() -> {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            boolean loggedIn = sp.getBoolean("loggedIn", false);
            Intent intent;
            if (loggedIn) {
                intent = new Intent(MainActivity.this, AppointmentActivity.class);
            } else {
                intent = new Intent(MainActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();


        }, 3000);

    }
}