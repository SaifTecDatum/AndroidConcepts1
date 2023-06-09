package com.myapps.androidconcepts.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.myapps.androidconcepts.Helpers.Constants;

public class PreferenceConnectors {
    private SharedPreferences preferences;
    public static final int MODE = Context.MODE_PRIVATE;

    public PreferenceConnectors(Context context) {
        String APP_PREF_NAME = Constants.PREF_NAME;
        int PRIVATE_MODE = 0;
        preferences = context.getSharedPreferences(APP_PREF_NAME, PRIVATE_MODE);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void remove(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    public static void clearData(Context context) {
        getEditor(context).clear().commit();
    }



    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

}
