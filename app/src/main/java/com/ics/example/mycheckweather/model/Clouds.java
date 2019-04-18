package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;

public class Clouds {
    @SerializedName("all")
    private double all;

    public Clouds() {
    }

    public double getAll() {
        return this.all;
    }

    public void setAll(double all) {
        this.all = all;
    }
}