package com.example.weathertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.weathertest.Presenter.IWeatherPresenter;
import com.example.weathertest.Presenter.WeatherPresenter;
import com.example.weathertest.ResponseDAO.WeatherCity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weathertest.View.IWeatherView;
import com.example.weathertest.View.IdCityDialogFragment;

import java.util.List;


public class ContentMainScreen extends AppCompatActivity implements IWeatherView, onAddCityListener {


    private EditText editID;
    private SharedPreferences myPreferences;
    private IdCityDialogFragment idCityDialogFragment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rv;
    private IWeatherPresenter weatherPresenter;
    private ItemTouchHelper itemTouchHelper;

    public static final String APP_RECORDED_CITIES = "RecordedCities";

    private final RVAdapter.OnItemClickListener clickListener = weatherCity -> {

        Intent intent = new Intent(this, MoreInfoActivity.class);
        intent.putExtra("NAME_CITY", weatherCity.getNameCity());
        intent.putExtra("IMAGE_CITY", weatherCity.getImageCity());
        intent.putExtra("TEMP_CITY", weatherCity.getTemp());
        intent.putExtra("WIND_CITY", weatherCity.getWindCity());
        intent.putExtra("PREASURE_CITY", weatherCity.getPreasureCity());
        startActivity(intent);
    };


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = myPreferences.edit();
        editor
                .putString("CITY_IDS", weatherPresenter.getIDsString())
                .apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main_screen);
        Button btnAdd = findViewById(R.id.add);
        rv = findViewById(R.id.rv);
        editID = findViewById(R.id.editID);
        weatherPresenter = new WeatherPresenter(this);

        itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback((RVAdapter) rv.getAdapter()));


        myPreferences = getSharedPreferences(APP_RECORDED_CITIES, Context.MODE_PRIVATE);
        weatherPresenter.setIDsString(myPreferences.getString("CITY_IDS", ""));

        weatherPresenter.updateWeatherData();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherPresenter.updateWeatherData();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIDCityDialogFragment();
            }
        });

    }

    @Override
    public void showWeatherData(List<WeatherCity> weatherObjectList) {
        rv.setAdapter(new RVAdapter(getApplicationContext(), weatherObjectList, clickListener));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(rv);
        rv.getAdapter().notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showIDCityDialogFragment() {
        idCityDialogFragment = new IdCityDialogFragment();
        idCityDialogFragment.show(getSupportFragmentManager(), "IdCityDialogFragment");

    }

    @Override
    public void showErrorCode(Throwable t) {
        Toast.makeText(this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAfterAdd(String addedCityName) {
        weatherPresenter.addWeatherCity(addedCityName);
    }


    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private RVAdapter rvAdapter;


        public SwipeToDeleteCallback(RVAdapter rvAdapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            rvAdapter = rvAdapter;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            weatherPresenter.deleteWeatherCity(position);

        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
    }


}
