package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ThreeHourForecast {
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private List<ThreeHourWeather> mThreeHourWeatherArray;
    @SerializedName("city")
    private City mCity;

    public ThreeHourForecast() {
    }

    public String getCod() {
        return this.cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return this.message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return this.cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ThreeHourWeather> getThreeHourWeatherArray() {
        return this.mThreeHourWeatherArray;
    }

    public void setThreeHourWeatherArray(List<ThreeHourWeather> threeHourWeatherArray) {
        this.mThreeHourWeatherArray = threeHourWeatherArray;
    }

    public City getCity() {
        return this.mCity;
    }

    public void setCity(City city) {
        this.mCity = city;
    }
}
