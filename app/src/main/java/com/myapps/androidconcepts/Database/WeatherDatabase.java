package com.myapps.androidconcepts.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapps.androidconcepts.Interfaces.WeatherDao;
import com.myapps.androidconcepts.Models.WeatherEntity;

/*@Database(entities = {WeatherEntity.class, PracticeEntity.class, CurrencyModel.class}, version = 1, exportSchema = false)
- ex to realise how multiple entities/tables to insert in single database class*/
@Database(entities = {WeatherEntity.class}, version = 3, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

}


//changing db versions accordingly because of changing the dao Queries & updating the entityList with new dataTypes will lead to errors.