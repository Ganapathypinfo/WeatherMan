package com.ics.example.mycheckweather.remote;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by ganapathyp on 5/15/2018.
 */

public class RetroFitClientClass {
    private static final String BASE_URL = "http://api.openweathermap.org";
    private static Retrofit retrofit = null;

    public RetroFitClientClass() {
    }

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = (new Retrofit.Builder()).baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
