package com.example.weathertest.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.weathertest.Presenter.IWeatherPresenter;
import com.example.weathertest.Presenter.WeatherPresenter;
import com.example.weathertest.R;
import com.example.weathertest.onAddCityListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class IdCityDialogFragment extends DialogFragment implements IDialogFragment{

    private IWeatherPresenter weatherPresenter;
    private IWeatherView weatherView;
    private onAddCityListener onAddCityListener;
    private Gson gson;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onAddCityListener = (onAddCityListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().onDetachedFromWindow();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        weatherPresenter.getWeatherData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        weatherPresenter = new WeatherPresenter(this);
        gson = new Gson();

        builder.setView(inflater.inflate(R.layout.dialog_id, null))
                .setPositiveButton(R.string.id_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText ed = (EditText) getDialog().findViewById(R.id.editID);
                        weatherPresenter.addWeaherCity(ed.getText().toString());
                        onAddCityListener.updateAfterAdd();
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