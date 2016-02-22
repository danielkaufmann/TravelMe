package com.example.daniel.travelme;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;



public class DatePickerFrag extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        Calendar cal = Calendar.getInstance();
        cal = callingActivity.getTripDateTime();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH));
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ConnectionActivity callingActivity = (ConnectionActivity) getActivity();
        callingActivity.setTripDateTime(cal);
    }
}