package com.example.daniel.travelme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFrag extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        Calendar cal = Calendar.getInstance();
        Date date = callingActivity.getTripDateTime();
        cal.setTime(date);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = new Date();
        date = cal.getTime();
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        callingActivity.setTripDateTime(date);
    }
}
