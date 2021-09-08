package com.example.weatherapp.pojo.api_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Daily {
    @SerializedName("dt")
    @Expose
    private long dt;

    @SerializedName("sunrise")
    @Expose
    private int sunrise;

    @SerializedName("sunset")
    @Expose
    private int sunset;

    @SerializedName("moonrise")
    @Expose
    private int moonrise;

    @SerializedName("moonset")
    @Expose
    private int moonset;

    @SerializedName("moon_phase")
    @Expose
    private double moonPhase;

    @SerializedName("temp")
    @Expose
    private Temp temp;

    @SerializedName("feels_like")
    @Expose
    private FeelsLike feelsLike;

    @SerializedName("pressure")
    @Expose
    private int pressure;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("dew_point")
    @Expose
    private double dewPoint;

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
    private List<Weather__1> weather = null;

    @SerializedName("clouds")
    @Expose
    private int clouds;

    @SerializedName("pop")
    @Expose
    private double pop;

    @SerializedName("rain")
    @Expose
    private double rain;

    @SerializedName("uvi")
    @Expose
    private double uvi;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Daily() {
    }

    public Daily(long dt, int sunrise, int sunset, int moonrise, int moonset, double moonPhase, Temp temp, FeelsLike feelsLike, int pressure, int humidity, double dewPoint, double windSpeed, int windDeg, double windGust, List<Weather__1> weather, int clouds, double pop, double rain, double uvi) {
        super();
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moonPhase = moonPhase;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.weather = weather;
        this.clouds = clouds;
        this.pop = pop;
        this.rain = rain;
        this.uvi = uvi;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
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

    public int getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(int moonrise) {
        this.moonrise = moonrise;
    }

    public int getMoonset() {
        return moonset;
    }

    public void setMoonset(int moonset) {
        this.moonset = moonset;
    }

    public double getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(double moonPhase) {
        this.moonPhase = moonPhase;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
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

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public List<Weather__1> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather__1> weather) {
        this.weather = weather;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
