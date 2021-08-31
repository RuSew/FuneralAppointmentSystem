package com.tutorial.funeralappointment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn;
        loginBtn = findViewById(R.id.loginBtn);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        loginBtn.setBackgroundColor(getResources().getColor(R.color.btn_color));


        sp = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setBackgroundColor(getResources().getColor(R.color.disabled));
                try {
                    User user = Queries.getUser(username.getText().toString(), password.getText().toString());
                    if (user.getUserId() != null) {
                        loginBtn.setBackgroundColor(getResources().getColor(R.color.btn_color));
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("loggedIn", true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, AppointmentActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        loginBtn.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}