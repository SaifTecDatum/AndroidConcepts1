package com.myapps.androidconcepts.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.myapps.androidconcepts.Models.SQLiteModel;

import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String db_Name = "MyDatabase";
    private static final int db_Version = 1;

    private static final String table_Name = "GlobalInfo";
    private static final String column_Id = "_id";
    private static final String column_Name = "country";
    private static final String column_Descrptn = "description";
    private static final String column_Gdp = "gdp";

    public MyHelper(@Nullable Context context) {
        super(context, db_Name, null, db_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE GlobalInfo (_id INTEGER PRIMARY KEY AUTOINCREMENT, country TEXT, description TEXT, gdp REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Name);
        onCreate(db);
    }

    public Long insertData(String insertCountry, String insertDescrptn, double insertGdp) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_Name, insertCountry);
        contentValues.put(column_Descrptn, insertDescrptn);
        contentValues.put(column_Gdp, insertGdp);

        try {
            result = database.insert(table_Name, null, contentValues);
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong.." + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }

        return result;
    }

    public List<SQLiteModel> getAllData() {
        List<SQLiteModel> modelList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + table_Name + " ORDER BY " + column_Gdp + " DESC",
                null);    //ASC or DESC

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(column_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(column_Name));
                String descrptn = cursor.getString(cursor.getColumnIndexOrThrow(column_Descrptn));
                double gdp = cursor.getDouble(cursor.getColumnIndexOrThrow(column_Gdp));
                modelList.add(new SQLiteModel(id, name, descrptn, gdp));
            }
            while (cursor.moveToNext());
        }

        return modelList;
    }

    public Long deleteItems(int deleteId) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            result = database.delete(table_Name, column_Id + " = ?", new String[]{deleteId + ""});
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }

        return result;
    }

    public Long updateItems(int updateId, String updateName, String updateDescrptn, double updateGdp) {
        long result = -1;
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_Id, updateId);
        contentValues.put(column_Name, updateName);
        contentValues.put(column_Descrptn, updateDescrptn);
        contentValues.put(column_Gdp, updateGdp);

        try {
            result = database.update(table_Name, contentValues, column_Id + " = ? ", new String[]{updateId + ""});
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong.. " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }
        return result;
    }
}