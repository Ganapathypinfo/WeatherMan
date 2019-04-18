package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;

    public Coord() {
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
