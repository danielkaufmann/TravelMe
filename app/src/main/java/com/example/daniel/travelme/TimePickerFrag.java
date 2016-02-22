package com.example.daniel.travelme;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFrag extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener{

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        callingActivity.setTripDateTime(cal);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        Calendar cal = callingActivity.getTripDateTime();

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, cal.get(cal.HOUR_OF_DAY), cal.get(cal.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }
}