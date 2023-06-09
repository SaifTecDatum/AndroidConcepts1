package com.myapps.androidconcepts.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapps.androidconcepts.Interfaces.PracticeDao;
import com.myapps.androidconcepts.Models.PracticeEntity;

@Database(entities = {PracticeEntity.class}, version = 1, exportSchema = false)
public abstract class PracticeDatabase extends RoomDatabase {

    public abstract PracticeDao practiceDao();
}