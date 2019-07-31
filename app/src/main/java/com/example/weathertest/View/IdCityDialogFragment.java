package com.example.weathertest.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;


import com.example.weathertest.R;
import com.example.weathertest.onAddCityListener;

public class IdCityDialogFragment extends DialogFragment implements IDialogFragment {

    private onAddCityListener onAddCityListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onAddCityListener = (onAddCityListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onAddCityListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().onDetachedFromWindow();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_id, null))
                .setPositiveButton(R.string.id_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText ed = (EditText) getDialog().findViewById(R.id.editID);
                        onAddCityListener.updateAfterAdd(ed.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        IdCityDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}