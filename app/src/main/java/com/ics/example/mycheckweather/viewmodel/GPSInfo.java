package com.ics.example.mycheckweather.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ganapathyp on 5/15/2018.
 */

public class GPSInfo extends BaseObservable{
    public String lat;
    public String lon;
    public String City;
    public String degreeToday;
    public String mFirstDay;
    public String degreeFirstDay;
    public String mSecDay;
    public String degreeSecDay;
    public String mThirdDay;
    public String degreeThirdDay;

    public void setLat(String lat) {
        this.lat = lat;
    }
    @Bindable
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


    @Bindable
    public String getLat() {
        return lat;
    }
    @Bindable
    public String getDegreeToday() {
        return degreeToday;
    }

    public void setDegreeToday(String degreeToday) {
        this.degreeToday = degreeToday;
    }
    @Bindable
    public String getDegreeFirstDay() {
        return degreeFirstDay;
    }

    public void setDegreeFirstDay(String degreeFirstDay) {
        this.degreeFirstDay = degreeFirstDay;
    }
    @Bindable
    public String getDegreeSecDay() {
        return degreeSecDay;
    }

    public void setDegreeSecDay(String degreeSecDay) {
        this.degreeSecDay = degreeSecDay;
    }
    @Bindable
    public String getDegreeThirdDay() {
        return degreeThirdDay;
    }

    public void setDegreeThirdDay(String degreeThirdDay) {
        this.degreeThirdDay = degreeThirdDay;
    }
    @Bindable
    public String getmFirstDay() {
        return mFirstDay;
    }

    public void setmFirstDay(String mFirstDay) {
        this.mFirstDay = mFirstDay;
    }
    @Bindable
    public String getmSecDay() {
        return mSecDay;
    }

    public void setmSecDay(String mSecDay) {
        this.mSecDay = mSecDay;
    }
    @Bindable
    public String getmThirdDay() {
        return mThirdDay;
    }

    public void setmThirdDay(String mThirdDay) {
        this.mThirdDay = mThirdDay;
    }

    @Bindable
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
