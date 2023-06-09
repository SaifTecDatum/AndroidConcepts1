package com.myapps.androidconcepts.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "periods_name")
    private String periodsName;

    @ColumnInfo(name = "periods_temp")
    private int periodsTemp;

    @ColumnInfo(name = "periods_temp_unit")
    private String periodsTempUnit;

    @ColumnInfo(name = "periods_icon")
    private String periodsIcon;

    @ColumnInfo(name = "periods_start_time")
    private String periodsStartTime;

    @ColumnInfo(name = "periods_end_time")
    private String periodsEndTime;

    public WeatherEntity(int id, String periodsName, int periodsTemp, String periodsTempUnit,
                         String periodsIcon, String periodsStartTime, String periodsEndTime) {
        this.id = id;
        this.periodsName = periodsName;
        this.periodsTemp = periodsTemp;
        this.periodsTempUnit = periodsTempUnit;
        this.periodsIcon = periodsIcon;
        this.periodsStartTime = periodsStartTime;
        this.periodsEndTime = periodsEndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriodsName() {
        return periodsName;
    }

    public void setPeriodsName(String periodsName) {
        this.periodsName = periodsName;
    }

    public int getPeriodsTemp() {
        return periodsTemp;
    }

    public void setPeriodsTemp(int periodsTemp) {
        this.periodsTemp = periodsTemp;
    }

    public String getPeriodsTempUnit() {
        return periodsTempUnit;
    }

    public void setPeriodsTempUnit(String periodsTempUnit) {
        this.periodsTempUnit = periodsTempUnit;
    }

    public String getPeriodsIcon() {
        return periodsIcon;
    }

    public void setPeriodsIcon(String periodsIcon) {
        this.periodsIcon = periodsIcon;
    }

    public String getPeriodsStartTime() {
        return periodsStartTime;
    }

    public void setPeriodsStartTime(String periodsStartTime) {
        this.periodsStartTime = periodsStartTime;
    }

    public String getPeriodsEndTime() {
        return periodsEndTime;
    }

    public void setPeriodsEndTime(String periodsEndTime) {
        this.periodsEndTime = periodsEndTime;
    }
}