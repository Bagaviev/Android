package com.example.weatherapp.pojo.api_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("timezone_offset")
    @Expose
    private int timezoneOffset;

    @SerializedName("current")
    @Expose
    private Current current;

    @SerializedName("daily")
    @Expose
    private List<Daily> daily = null;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Request() {
    }

    public Request(double lat, double lon, String timezone, int timezoneOffset, Current current, List<Daily> daily) {
        super();
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.daily = daily;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
