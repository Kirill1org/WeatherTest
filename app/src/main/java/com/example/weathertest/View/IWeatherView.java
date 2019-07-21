package com.example.weathertest.View;

import com.example.weathertest.ResponseDAO.WeatherCity;

import java.util.List;

public interface IWeatherView {
    void showWeatherData(List<WeatherCity> weatherObjectList);

    void showIDCityDialogFragment();

    void showErrorCode(Throwable t);

    void showMessage(String message);
}
