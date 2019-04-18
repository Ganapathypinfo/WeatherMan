package com.ics.example.mycheckweather.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private Coord mCoord;
    @SerializedName("country")
    private String country;
    @SerializedName("population")
    private long population;

    public City() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return this.mCoord;
    }

    public void setCoord(Coord coord) {
        this.mCoord = coord;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPopulation() {
        return this.population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
