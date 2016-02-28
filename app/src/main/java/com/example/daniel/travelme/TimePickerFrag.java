package com.example.daniel.travelme;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFrag extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        Calendar cal = Calendar.getInstance();
        Date date = callingActivity.getTripDateTime();
        cal.setTime(date);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        date = cal.getTime();
        callingActivity.setTripDateTime(date);
    }
}