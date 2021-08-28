package com.tutorial.funeralappointment;

import android.app.ProgressDialog;
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
        ProgressDialog progressDialog = new ProgressDialog(this);

        sp = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                try {
                    Queries login = new Queries();
//                    User user = login.getUser(username.getText().toString(), password.getText().toString());
                    User user = login.getUser("Admin", "123");
                    toastMessage("Logged in successfully");
                    if (user != null) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("loggedIn", true);
                        editor.apply();

                        progressDialog.hide();
                        Intent intent = new Intent(LoginActivity.this, AppointmentActivity.class);
                        startActivity(intent);
                    }else{
                        toastMessage("Invalid Credentials");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}