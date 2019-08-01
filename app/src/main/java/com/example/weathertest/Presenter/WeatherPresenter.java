package com.example.weathertest.Presenter;

import com.example.weathertest.Model.NetworkModule;
import com.example.weathertest.Model.WeatherService;
import com.example.weathertest.ResponseDAO.WeatherCity;
import com.example.weathertest.ResponseDAO.WeatherObjectList;
import com.example.weathertest.ResponseDAO.WeatherObjectUnit;
import com.example.weathertest.View.IWeatherView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter implements IWeatherPresenter {

    private static final String API_KEY="ae0e174643a1c579b63d27dbe6279127";

    private IWeatherView weatherView;
    private static String stringIDCities;

    private List<WeatherCity> weatherObjectList = new ArrayList<>();

    public WeatherPresenter(IWeatherView weatherView) {
        this.weatherView = weatherView;
    }

    @Override
    public void updateWeatherData() {

        if (stringIDCities == "") return;

        weatherObjectList.clear();
        WeatherService api = new NetworkModule().weatherService;


        api.getWeatherList(stringIDCities, "metric", API_KEY)
                .subscribeOn(Schedulers.io())
                .doOnError(err -> weatherView.showErrorCode(err))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addWeatherUpdateToList, Throwable -> weatherView.showErrorCode(Throwable));


    }

    @Override
    public void deleteWeatherCity(int position) {

        weatherObjectList.remove(position);
        String[] cities = stringIDCities.split(",");
        stringIDCities = "";

        if (position != 0) {
            for (int i = 0; i < cities.length; i++) {
                if ((i == 0) && (i != position)) stringIDCities += cities[i];
                else if (i != position) stringIDCities += "," + cities[i];
            }
        }

        if (position == 0) {
            for (int i = position + 1; i < cities.length; i++) {
                if (i == position + 1) stringIDCities += cities[i];
                else stringIDCities += "," + cities[i];

            }
        }


        this.updateWeatherData();
    }

    @Override
    public void addWeatherCity(String addCityName) {

        for (WeatherCity weatherCity : weatherObjectList) {
            if (weatherCity.getNameCity().toLowerCase().equals(addCityName.toLowerCase())) {
                weatherView.showMessage(weatherCity.getNameCity() + " already added");
                return;
            }
        }


        WeatherService api = new NetworkModule().weatherService;

        api.getWeatherUnit(addCityName, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addWeatherUnitCityToList, Throwable -> weatherView.showErrorCode(Throwable));

    }

    @Override
    public String getIDsString() {
        return stringIDCities;
    }

    @Override
    public void setIDsString(String stringIDCitys) {
        this.stringIDCities = stringIDCitys;
    }

    private void addWeatherUpdateToList(WeatherObjectList weatherObjectList) {

        for (int i = 0; i < weatherObjectList.getList().size(); i++) {

            this.weatherObjectList.add(new WeatherCity(weatherObjectList.getList().get(i).getName(),
                    Double.toString(Math.round(weatherObjectList.getList().get(i).getMain().getTemp() - 273)),
                    "http://openweathermap.org/img/w/" + weatherObjectList.getList().get(i).getWeather().get(0).getIcon() + ".png",
                    weatherObjectList.getList().get(i).getWind().getSpeed().toString(),
                    Double.toString(Math.round(weatherObjectList.getList().get(i).getMain().getPressure() / 1.333))));

        }
        weatherView.showWeatherData(this.weatherObjectList);


    }

    private void addWeatherUnitCityToList(WeatherObjectUnit weatherObjectUnit) {
        weatherObjectList.add(new WeatherCity(weatherObjectUnit.getName(),
                Double.toString(Math.round(weatherObjectUnit.getMain().getTemp() - 273)),
                "http://openweathermap.org/img/w/" + weatherObjectUnit.getWeather().get(0).getIcon() + ".png",
                weatherObjectUnit.getWind().getSpeed().toString(),
                Double.toString(Math.round(weatherObjectUnit.getMain().getPressure() / 1.333))));

        weatherView.showWeatherData(weatherObjectList);

        addCityIdToString(weatherObjectUnit.getId().toString());


    }

    static private void addCityIdToString(String cityID) {

        if (stringIDCities != "") stringIDCities += "," + cityID;
        else stringIDCities += cityID;

    }
}
