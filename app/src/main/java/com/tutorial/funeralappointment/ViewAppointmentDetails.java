package com.tutorial.funeralappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class ViewAppointmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_details);
        setTitle("Appointment Details");
        Intent intent = this.getIntent();
        if (intent != null) {
            TextView name = findViewById(R.id.name);
            TextView date = findViewById(R.id.date);
            TextView time = findViewById(R.id.time);
            TextView email = findViewById(R.id.email);
            TextView mobile = findViewById(R.id.mobile);

            name.setText(intent.getStringExtra("name"));
            date.setText(intent.getStringExtra("date"));
            time.setText(intent.getStringExtra("time"));
            email.setText(intent.getStringExtra("email"));
            mobile.setText(intent.getStringExtra("mobile"));
        }

        Button cancel = findViewById(R.id.cancelBtn);

        String refNo = intent.getStringExtra("refNo");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Queries.cancelAppointment(refNo);
                    sendMail(intent.getStringExtra("date"), intent.getStringExtra("time"), refNo, intent.getStringExtra("email"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    private void sendMail(String date, String time, String refNo, String email) {
        String subject = "Appointment Cancellation";
        String body = "Dear Sir/Madam,\n\n" +
                "Please note that the appointment that was scheduled on the - "+ date + " " + time + " is cancelled. Please be kind enough to reschedule your appointment.\n" +
                "Your reference number is - " + refNo + "\n\nThank you.";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, body);
        javaMailAPI.execute();
    }

}