package com.example.weathertest.Model;

import com.example.weathertest.ResponseDAO.WeatherObjectList;
import com.example.weathertest.ResponseDAO.WeatherObjectUnit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/group")
    Observable<WeatherObjectList> getWeatherList(@Query("id") String cityIDs, @Query("UNITS") String units, @Query("APPID") String appID);

    @GET("data/2.5/weather")
    Observable<WeatherObjectUnit> getWeatherUnit(@Query("q") String cityName, @Query("APPID") String appID);
}
