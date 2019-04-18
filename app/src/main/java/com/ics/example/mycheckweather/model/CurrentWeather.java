package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {
    @SerializedName("coord")
    private Coord mCoord;
    @SerializedName("weather")
    private List<Weather> mWeatherArray;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private Main mMain;
    @SerializedName("wind")
    private Wind mWind;
    @SerializedName("clouds")
    private Clouds mClouds;
    @SerializedName("dt")
    private Long dt;
    @SerializedName("sys")
    private Sys mSys;
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;

    public CurrentWeather() {
    }

    public Coord getCoord() {
        return this.mCoord;
    }

    public void setCoord(Coord coord) {
        this.mCoord = coord;
    }

    public List<Weather> getWeatherArray() {
        return this.mWeatherArray;
    }

    public void setWeatherArray(List<Weather> weatherArray) {
        this.mWeatherArray = weatherArray;
    }

    public String getBase() {
        return this.base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return this.mMain;
    }

    public void setMain(Main main) {
        this.mMain = main;
    }

    public Wind getWind() {
        return this.mWind;
    }

    public void setWind(Wind wind) {
        this.mWind = wind;
    }

    public Clouds getClouds() {
        return this.mClouds;
    }

    public void setClouds(Clouds clouds) {
        this.mClouds = clouds;
    }

    public Long getDt() {
        return this.dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return this.mSys;
    }

    public void setSys(Sys sys) {
        this.mSys = sys;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
