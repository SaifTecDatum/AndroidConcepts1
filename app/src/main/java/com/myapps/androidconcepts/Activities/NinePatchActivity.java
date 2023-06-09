package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class NinePatchActivity extends AppCompatActivity {
    private static final String TAG = "CheckingLifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_patch);
        Log.d(TAG, "onCreate: Event Called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Event Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Event Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Event Called");

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Event Called");

        Utilities.onPauseToUnRegister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Event Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Event Called");
    }

}