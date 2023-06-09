package com.myapps.androidconcepts.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.myapps.androidconcepts.Models.WeatherEntity;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert
    void insertWeatherData(WeatherEntity weatherEntity);


    @Query("SELECT EXISTS(SELECT * FROM WeatherEntity WHERE periods_start_time =:periodsStartTime)")
    Boolean isExists(String periodsStartTime);


    @Query("SELECT * FROM WeatherEntity")
    List<WeatherEntity> getAllWeatherData();


    /*@Delete
    void delete(WeatherEntity weatherEntity);*/

    @Query("DELETE FROM WeatherEntity")
    void delete();       //Deleting all Data in the Database.

}