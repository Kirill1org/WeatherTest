package com.example.weathertest.View;

import com.example.weathertest.ResponseDAO.WeatherCity;
import com.example.weathertest.ResponseDAO.WeatherObject;

import java.util.List;

public interface IWeatherView {
    void showWeatherData(List<WeatherCity> weatherObjectList);
    void showIDCityDialogFragment();
    void updateAfterAdd();
}
