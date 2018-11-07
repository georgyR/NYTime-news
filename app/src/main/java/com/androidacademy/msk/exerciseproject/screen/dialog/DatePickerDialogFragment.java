package com.androidacademy.msk.exerciseproject.screen.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment {

    public static final String YEAR_KEY = "YEAR_KEY";
    public static final String MONTH_KEY = "MONTH_KEY";
    public static final String DAY_KEY = "DAY_KEY";

    private DatePickerDialog.OnDateSetListener listener;

    public static DatePickerDialogFragment newInstance(int year,
                                                       int month,
                                                       int day) {
        Bundle args = new Bundle();
        args.putInt(YEAR_KEY, year);
        args.putInt(MONTH_KEY, month);
        args.putInt(DAY_KEY, day);


        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DatePickerDialog.OnDateSetListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("activity must implement DatePickerDialog.OnDateSetListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = getArguments().getInt(YEAR_KEY);
        int month = getArguments().getInt(MONTH_KEY);
        int day = getArguments().getInt(DAY_KEY);
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}