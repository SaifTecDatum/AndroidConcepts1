package com.myapps.androidconcepts.Models;

public class SQLiteModel {
    private int _id;
    private String country;
    private String description;
    private double gdp;

    public SQLiteModel(int _id, String country, String description, double gdp) {
        this._id = _id;
        this.country = country;
        this.description = description;
        this.gdp = gdp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGdp() {
        return gdp;
    }

    public void setGdp(double gdp) {
        this.gdp = gdp;
    }
}
