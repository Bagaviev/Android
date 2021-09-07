package com.example.weatherapp.pojo.api_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Temp {
    @SerializedName("day")
    @Expose
    private double day;

    @SerializedName("min")
    @Expose
    private double min;

    @SerializedName("max")
    @Expose
    private double max;

    @SerializedName("night")
    @Expose
    private double night;

    @SerializedName("eve")
    @Expose
    private double eve;

    @SerializedName("morn")
    @Expose
    private double morn;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Temp() {
    }

    public Temp(double day, double min, double max, double night, double eve, double morn) {
        super();
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
