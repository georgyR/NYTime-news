package com.androidacademy.msk.exerciseproject.screen.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;


public class TimePickerDialogFragment extends DialogFragment {

    public static final String HOUR_KEY = "YEAR_KEY";
    public static final String MINUTE_KEY = "MINUTE_KEY";

    private TimePickerDialog.OnTimeSetListener listener;

    public static TimePickerDialogFragment newInstance(int hour, int minute) {

        Bundle args = new Bundle();
        args.putInt(HOUR_KEY, hour);
        args.putInt(MINUTE_KEY, minute);

        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimePickerDialog.OnTimeSetListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("activity must implement DatePickerDialog.OnDateSetListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = getArguments().getInt(HOUR_KEY);
        int minute = getArguments().getInt(MINUTE_KEY);

        return new TimePickerDialog(getActivity(), listener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
