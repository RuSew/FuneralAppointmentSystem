package com.tutorial.funeralappointment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.funeralappointment.databinding.ActivityAppointmentBinding;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity {

    private final String[] months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    ActivityAppointmentBinding binding;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private boolean isCancel = false;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Current Appointments");

        //Date picker
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        //Creating appointment type dropdown
        Spinner dropdown = findViewById(R.id.apptType);
        List<String> options = Arrays.asList("Current Appointments", "Cancelled Appointments");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, options);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Appointment> appointmentList = null;
                try {
                    if (position == 0) {
                        isCancel = false;
                        setTitle("Current Appointments");
                    } else {
                        isCancel = true;
                        setTitle("Cancelled Appointments");
                    }
                    appointmentList = Queries.getAppointments(selectedDate, isCancel);
                    setAdapter(appointmentList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        selectedDate = year + "-" + (month + 1) + "-" + day;
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                ArrayList<Appointment> appointmentList = null;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                System.out.println("date changed");

                selectedDate = year + "-" + (month + 1) + "-" + day;
                try {
                    appointmentList = Queries.getAppointments(selectedDate, isCancel);
                    setAdapter(appointmentList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);// Min time is not equal to current time. So -1000ms

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        return months[month];
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void setAdapter(ArrayList<Appointment> appointments) {
        ListAdapter listAdapter = new ListAdapter(AppointmentActivity.this, appointments);
        binding.appointmentListView.setAdapter(listAdapter);
        binding.appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isCancel){
                    Intent intent = new Intent(AppointmentActivity.this, ViewAppointmentDetails.class);
                    intent.putExtra("custId", appointments.get(position).getCustId());
                    intent.putExtra("refNo", appointments.get(position).getRefNo());
                    intent.putExtra("name", appointments.get(position).getClientName());
                    intent.putExtra("date", appointments.get(position).getApptDate());
                    intent.putExtra("time", appointments.get(position).getTimeSlot());
                    intent.putExtra("email", appointments.get(position).getEmail());
                    intent.putExtra("mobile", appointments.get(position).getMobile());
                    startActivity(intent);
                }
            }
        });

    }

    // refresh thia page when back button clicked
}