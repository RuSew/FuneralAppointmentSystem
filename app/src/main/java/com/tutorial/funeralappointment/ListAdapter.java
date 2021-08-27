package com.tutorial.funeralappointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Appointment> {


    public ListAdapter(Context context, ArrayList<Appointment> userArrayList) {

        super(context, R.layout.list_item, userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Appointment appointment = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        TextView clientName = convertView.findViewById(R.id.clientName);
        TextView apptDate = convertView.findViewById(R.id.apptDate);
        TextView apptTime = convertView.findViewById(R.id.apptTime);

        clientName.setText(appointment.getClientName());
        apptDate.setText(appointment.getApptDate());
        apptTime.setText(appointment.getTimeSlot());


        return convertView;
    }
}