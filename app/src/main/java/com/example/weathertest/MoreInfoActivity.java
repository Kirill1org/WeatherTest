package com.example.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MoreInfoActivity extends AppCompatActivity {

    TextView valueTemp;
    TextView valueWind;
    TextView valuePreasure;
    TextView nameCity;
    ImageView iconCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info_activity);

        nameCity = findViewById(R.id.nameCity);
        iconCity = findViewById(R.id.imageWeather);
        valueTemp = findViewById(R.id.valueTemp);
        valueWind = findViewById(R.id.valueWind);
        valuePreasure = findViewById(R.id.valuePreauser);

        Intent intent = getIntent();

        nameCity.setText(intent.getStringExtra("NAME_CITY"));
        valueTemp.setText(intent.getStringExtra("TEMP_CITY"));
        valueWind.setText(intent.getStringExtra("WIND_CITY"));
        valuePreasure.setText(intent.getStringExtra("PREASURE_CITY"));
        Glide.with(this).load(intent.getStringExtra("IMAGE_CITY")).into(iconCity);

    }
}


