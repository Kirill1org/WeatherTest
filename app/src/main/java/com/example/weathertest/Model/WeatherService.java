package com.example.weathertest.Model;

import com.example.weathertest.ResponseDAO.WeatherObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/group")
    Observable<WeatherObject> getWeather(@Query("id") String cityIDs, @Query("UNITS") String units, @Query("APPID") String appID);
}
