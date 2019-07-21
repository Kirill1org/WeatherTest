package com.example.weathertest.Presenter;

public interface IWeatherPresenter {

    void updateWeatherData();

    void deleteWeatherCity(int position);

    void addWeatherCity(String addCityName);

    String getIDsString();

    void setIDsString(String stringIDCitys);


}
