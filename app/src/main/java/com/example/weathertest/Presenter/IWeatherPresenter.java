package com.example.weathertest.Presenter;

public interface IWeatherPresenter {
    void getWeatherData();

    void deleteWeatherCity(int position);

    void addWeaherCity(String newCityID);

    String getIDsString();

    void setIDsString(String stringIDCitys);

}
