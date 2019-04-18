package com.ics.example.mycheckweather.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ganapathyp on 5/15/2018.
 */

public class WeatherDetailsOfHPW extends BaseObservable{

    private String todayHumidity;
    private String todayWind;
    private String todayPressure;

    @Bindable
    public String getTodayHumidity() {
        return todayHumidity;
    }

    public void setTodayHumidity(String todayHumidity) {
        this.todayHumidity = todayHumidity;
    }
    @Bindable
    public String getTodayWind() {
        return todayWind;
    }

    public void setTodayWind(String todayWind) {
        this.todayWind = todayWind;
    }
    @Bindable
    public String getTodayPressure() {
        return todayPressure;
    }

    public void setTodayPressure(String todayPressure) {
        this.todayPressure = todayPressure;
    }
}
