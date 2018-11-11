package com.androidacademy.msk.exerciseproject.screen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.androidacademy.msk.exerciseproject.R;

public class LeaveWithoutSaveDialogFragment extends DialogFragment {

    private DialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("activity must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.message_leave_without_saving)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    listener.onDialogPositiveClick();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                    listener.onDialogNegativeClick();
                });

        return builder.create();
    }
    public interface DialogListener {

        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
