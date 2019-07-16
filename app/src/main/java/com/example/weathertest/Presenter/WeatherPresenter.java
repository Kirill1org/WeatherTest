package com.example.weathertest.Presenter;

import com.example.weathertest.Model.NetworkModule;
import com.example.weathertest.Model.WeatherService;
import com.example.weathertest.ResponseDAO.WeatherCity;
import com.example.weathertest.ResponseDAO.WeatherObject;
import com.example.weathertest.View.IDialogFragment;
import com.example.weathertest.View.IWeatherView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter implements IWeatherPresenter {

    private IWeatherView weatherView;
    private IDialogFragment dialogFragment;
    private static String stringIDCitys;

    private List<WeatherCity> weatherObjectList = new ArrayList<>();

    public WeatherPresenter(IWeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public WeatherPresenter(IDialogFragment dialogFragment) {
        this.dialogFragment = dialogFragment;
    }

    @Override
    public void getWeatherData() {

        weatherObjectList.clear();
        WeatherService api = new NetworkModule().weatherService;
        Disposable disposable = api.getWeather(stringIDCitys, "metric", "ae0e174643a1c579b63d27dbe6279127")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addToWeatherList, Throwable::printStackTrace);


    }

    @Override
    public void deleteWeatherCity(int position) {

        weatherObjectList.remove(position);
        String[] cities = stringIDCitys.split(",");
        stringIDCitys = "";

        if (position != 0) {
            for (int i = 0; i < cities.length; i++) {
                if ((i == 0) && (i != position)) stringIDCitys += cities[i];
                else if (i != position) stringIDCitys += "," + cities[i];
            }
        }

        if (position == 0) {
            for (int i = position + 1; i < cities.length; i++) {
                if (i == position + 1) stringIDCitys += cities[i];
                else stringIDCitys += "," + cities[i];

            }
        }


        getWeatherData();
    }

    @Override
    public void addWeaherCity(String newCityID) {

        if (stringIDCitys != "") stringIDCitys += "," + newCityID;
        else stringIDCitys += newCityID;

    }

    @Override
    public String getIDsString() {
        return stringIDCitys;
    }

    @Override
    public void setIDsString(String stringIDCitys) {
        this.stringIDCitys = stringIDCitys;
    }

    private void addToWeatherList(WeatherObject weatherObject) {

        for (int i = 0; i < weatherObject.getList().size(); i++) {

            weatherObjectList.add(new WeatherCity(weatherObject.getList().get(i).getName(),
                            Double.toString(Math.round(weatherObject.getList().get(i).getMain().getTemp() - 273)),
                            "http://openweathermap.org/img/w/" + weatherObject.getList().get(i).getWeather().get(0).getIcon() + ".png",
                            weatherObject.getList().get(i).getWind().getSpeed().toString(),
                            weatherObject.getList().get(i).getMain().getPressure().toString()));

        }
        weatherView.showWeatherData(weatherObjectList);

    }
}
