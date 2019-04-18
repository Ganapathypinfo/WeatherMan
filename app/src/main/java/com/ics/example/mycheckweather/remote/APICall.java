package com.ics.example.mycheckweather.remote;

import com.ics.example.mycheckweather.model.CurrentWeather;
import com.ics.example.mycheckweather.model.ThreeHourForecast;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ganapathyp on 5/15/2018.
 */

public interface APICall {
    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeatherByCityName(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeatherByCityID(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeatherByGeoCoordinates(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeatherByZipCode(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/forecast")
    Call<ThreeHourForecast> getThreeHourForecastByCityName(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/forecast")
    Call<ThreeHourForecast> getThreeHourForecastByCityID(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/forecast")
    Call<ThreeHourForecast> getThreeHourForecastByGeoCoordinates(@QueryMap Map<String, String> var1);

    @GET("/data/2.5/forecast")
    Call<ThreeHourForecast> getThreeHourForecastByZipCode(@QueryMap Map<String, String> var1);
}
