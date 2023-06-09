package com.myapps.androidconcepts.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.myapps.androidconcepts.Models.SecndSqliteModel;

import java.util.ArrayList;
import java.util.List;

public class MySecndHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String databaseName = "MGD_CineData";
    private static final int databaseVersion = 1;

    private static final String table_Name = "MGD_CineTable";
    private static final String movie_Id = "_id";
    private static final String movie_Name = "movie";
    private static final String movie_Details = "movieDetails";
    private static final String movie_Imdb = "imdb";

    public MySecndHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Sql = "CREATE TABLE MGD_CineTable(_id INTEGER PRIMARY KEY AUTOINCREMENT, movie TEXT, movieDetails TEXT, imdb REAL)";
        db.execSQL(Sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Name);
        onCreate(db);
    }


    public Long InsertData(String movie, String movieDetails, double imdb) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(movie_Name, movie);
        contentValues.put(movie_Details, movieDetails);
        contentValues.put(movie_Imdb, imdb);

        try {
            result = database.insert(table_Name, null, contentValues);
        } catch (Exception e) {
            Log.e("ErrorInInsert", e.getLocalizedMessage());
        } finally {
            database.close();
        }
        return result;
    }


    public List<SecndSqliteModel> getAllData() {
        List<SecndSqliteModel> secndDataList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + table_Name, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(movie_Id));
                String movie = cursor.getString(cursor.getColumnIndexOrThrow(movie_Name));
                String movieDetails = cursor.getString(cursor.getColumnIndexOrThrow(movie_Details));
                double imdb = cursor.getDouble(cursor.getColumnIndexOrThrow(movie_Imdb));
                secndDataList.add(new SecndSqliteModel(id, movie, movieDetails, imdb));
            }
            while (cursor.moveToNext());
        }

        return secndDataList;
    }


    public Long deteleItems(int id) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            result = database.delete(table_Name, movie_Id + " =? ", new String[]{id + ""});
        } catch (Exception e) {
            Log.e("ErrorInDelete", e.getLocalizedMessage());
        } finally {
            database.close();
        }
        return result;
    }


    //1st process starts from adapter, 2nd in its activity by interface callbacks it'll popup & 3rd here in its sqlite_helperClass.
    //4th. Again from sqlite_helperClass returns to activity with result value,  5th. In activity by comparing
    //result value we refresh the list items by help of callbacks,  6th. shows in recyclerAdapter to the users.
    public Long UpdateItems(int id, String movie, String movieDetails, double imdb) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(movie_Id, id);
        contentValues.put(movie_Name, movie);
        contentValues.put(movie_Details, movieDetails);
        contentValues.put(movie_Imdb, imdb);

        try {
            result = database.update(table_Name, contentValues, movie_Id + " =? ", new String[]{id + ""});
        } catch (Exception e) {
            Log.e("ErrorInUpdate", e.getLocalizedMessage());
        } finally {
            database.close();
        }

        return result;
    }

}