package com.myapps.androidconcepts.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class PracticeEntity {

    @PrimaryKey(autoGenerate = false)
    private int number;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "startTime")
    private String startTime;

    @ColumnInfo(name = "endTime")
    private String endTime;

    @ColumnInfo(name = "temp")
    private int temperature;

    @ColumnInfo(name = "tempUnit")
    private String temperatureUnit;

    @ColumnInfo(name = "windSpeed")
    private String windSpeed;

    @ColumnInfo(name = "windDirection")
    private String windDirection;

    @ColumnInfo(name = "icon")
    private String icon;

    @ColumnInfo(name = "shortForecast")
    private String shortForecast;

    @ColumnInfo(name = "detailedForecast")
    private String detailedForecast;


    public PracticeEntity(int number, String name, String startTime, String endTime, int temperature, String temperatureUnit,
                          String windSpeed, String windDirection, String icon, String shortForecast, String detailedForecast) {
        this.number = number;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.icon = icon;
        this.shortForecast = shortForecast;
        this.detailedForecast = detailedForecast;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public String getDetailedForecast() {
        return detailedForecast;
    }

    public void setDetailedForecast(String detailedForecast) {
        this.detailedForecast = detailedForecast;
    }
}