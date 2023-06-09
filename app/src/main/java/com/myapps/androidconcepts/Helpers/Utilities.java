package com.myapps.androidconcepts.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.myapps.androidconcepts.Activities.NoInternetActivity;
import com.myapps.androidconcepts.BroadcastReceivers.NetworkChangeReceiver;

public class Utilities {
    private static final String TAG = "Utilities";
    public static Activity currentActivity;
    public static NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    public static void showNoInternet(boolean isConnected) {
        try {
            if (isConnected && currentActivity.getLocalClassName().endsWith(Constants.noInternetActivity)) {
                currentActivity.finish();
            }
            else if (!isConnected && !currentActivity.getLocalClassName().endsWith(Constants.noInternetActivity)) {
                Intent intent = new Intent(currentActivity, NoInternetActivity.class);
                currentActivity.startActivity(intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "showNoInternet: " + e.getLocalizedMessage());
        }
    }


    public static void onResumeToRegister(Activity activity) {
        currentActivity = activity;
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static void onPauseToUnRegister(Activity activity) {
        currentActivity = activity;
        activity.unregisterReceiver(networkChangeReceiver);
    }

}