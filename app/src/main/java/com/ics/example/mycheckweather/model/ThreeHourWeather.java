package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreeHourWeather {
    @SerializedName("dt")
    private Long dt;
    @SerializedName("main")
    private Main mMain;
    @SerializedName("weather")
    private List<Weather> mWeatherArray;
    @SerializedName("clouds")
    private Clouds mClouds;
    @SerializedName("wind")
    private Wind mWind;
    @SerializedName("sys")
    private Sys mSys;
    @SerializedName("dt_txt")
    private String dtTxt;

    public ThreeHourWeather() {
    }

    public Long getDt() {
        return this.dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return this.mMain;
    }

    public void setMain(Main main) {
        this.mMain = main;
    }

    public List<Weather> getWeatherArray() {
        return this.mWeatherArray;
    }

    public void setWeatherArray(List<Weather> weatherArray) {
        this.mWeatherArray = weatherArray;
    }

    public Clouds getClouds() {
        return this.mClouds;
    }

    public void setClouds(Clouds clouds) {
        this.mClouds = clouds;
    }

    public Wind getWind() {
        return this.mWind;
    }

    public void setWind(Wind wind) {
        this.mWind = wind;
    }

    public Sys getSys() {
        return this.mSys;
    }

    public void setSys(Sys sys) {
        this.mSys = sys;
    }

    public String getDtTxt() {
        return this.dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
