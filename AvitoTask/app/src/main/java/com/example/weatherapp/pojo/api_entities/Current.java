//-----------------------------------com.example.weatherapp.pojo.Current.java-----------------------------------
package com.example.weatherapp.pojo.api_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Current {
    @SerializedName("dt")
    @Expose
    private int dt;

    @SerializedName("sunrise")
    @Expose
    private int sunrise;

    @SerializedName("sunset")
    @Expose
    private int sunset;

    @SerializedName("temp")
    @Expose
    private double temp;

    @SerializedName("feels_like")
    @Expose
    private double feelsLike;

    @SerializedName("pressure")
    @Expose
    private int pressure;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("dew_point")
    @Expose
    private double dewPoint;

    @SerializedName("uvi")
    @Expose
    private double uvi;

    @SerializedName("clouds")
    @Expose
    private int clouds;

    @SerializedName("visibility")
    @Expose
    private int visibility;

    @SerializedName("wind_speed")
    @Expose
    private double windSpeed;

    @SerializedName("wind_deg")
    @Expose
    private int windDeg;

    @SerializedName("wind_gust")
    @Expose
    private double windGust;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Current() {
    }

    public Current(int dt, int sunrise, int sunset, double temp, double feelsLike, int pressure, int humidity, double dewPoint, double uvi, int clouds, int visibility, double windSpeed, int windDeg, double windGust, List<Weather> weather) {
        super();
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.weather = weather;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public double getWindGust() {
        return windGust;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
