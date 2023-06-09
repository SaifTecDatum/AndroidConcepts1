package com.myapps.androidconcepts.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapps.androidconcepts.Interfaces.UserDao;
import com.myapps.androidconcepts.Models.Users;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}