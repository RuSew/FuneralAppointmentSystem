package com.tutorial.funeralappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class ViewAppointmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_details);

        int cancelled = 0;
        Intent intent = this.getIntent();
        if (intent != null) {
            TextView name = findViewById(R.id.name);
            TextView date = findViewById(R.id.date);
            TextView time = findViewById(R.id.time);
            TextView email = findViewById(R.id.email);
            TextView mobile = findViewById(R.id.mobile);
            TextView remark = findViewById(R.id.remark);
            TextView textRemark = findViewById(R.id.remarkText);
            TextView remarkColon = findViewById(R.id.remarkColon);

            name.setText(intent.getStringExtra("name"));
            date.setText(intent.getStringExtra("date"));
            time.setText(intent.getStringExtra("time"));
            email.setText(intent.getStringExtra("email"));
            mobile.setText(intent.getStringExtra("mobile"));
            mobile.setText(intent.getStringExtra("mobile"));
            cancelled = intent.getIntExtra("cancelled", 0);

            if (intent.getStringExtra("remark") != null) {
                textRemark.setVisibility(View.VISIBLE);
                remarkColon.setVisibility(View.VISIBLE);
                remark.setVisibility(View.VISIBLE);
                remark.setText(intent.getStringExtra("remark"));
            } else {
                textRemark.setVisibility(View.INVISIBLE);
                remark.setVisibility(View.INVISIBLE);
                remarkColon.setVisibility(View.INVISIBLE);
            }
        }

        Button cancel = findViewById(R.id.cancelBtn);
        cancel.setBackgroundColor(getResources().getColor(R.color.btn_color));

        String refNo = intent.getStringExtra("refNo");

        if (cancelled == 1) {
            cancel.setVisibility(View.INVISIBLE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel.setBackgroundColor(getResources().getColor(R.color.disabled));
                AlertDialog.Builder customDialog = new AlertDialog.Builder(ViewAppointmentDetails.this);
                customDialog.setTitle("Cancellation remark");

                final EditText remarkText = new EditText(ViewAppointmentDetails.this);
                customDialog.setView(remarkText);
                final float scale = getResources().getDisplayMetrics().density;
                int padding_5dp = (int) (10 * scale + 0.5f);
                remarkText.setPadding(padding_5dp,padding_5dp,padding_5dp,padding_5dp);

                customDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String remark = remarkText.getText().toString();

                        try {
                            int remarkAdded = Queries.addRemark(remark, refNo);
                            if (remarkAdded > 0) {
                                Queries.cancelAppointment(refNo);
                                sendMail(intent.getStringExtra("date"), intent.getStringExtra("time"), refNo, intent.getStringExtra("email"));
                                cancel.setVisibility(View.INVISIBLE);
                                new Handler().postDelayed(() -> {
                                    onBackPressed();
                                }, 5000);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                customDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        cancel.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    }
                });
                customDialog.show();
            }
        });

    }

    private void sendMail(String date, String time, String refNo, String email) {
        String subject = "Appointment Cancellation";
        String body = "Dear Sir/Madam,\n\n" +
                "Please note that the appointment that was scheduled on the - " + date + " " + time + " is cancelled. Please be kind enough to reschedule your appointment.\n" +
                "Your reference number is - " + refNo + "\n\nThank you.";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, body);
        javaMailAPI.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewAppointmentDetails.this, AppointmentActivity.class);
        startActivity(intent);
        finish();
    }

}