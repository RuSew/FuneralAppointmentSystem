package com.tutorial.funeralappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn;
        loginBtn = findViewById(R.id.loginBtn);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Queries login = new Queries();
                try {
                    User user = Queries.getUser(username.getText().toString(), password.getText().toString());
                    if (user != null) {
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