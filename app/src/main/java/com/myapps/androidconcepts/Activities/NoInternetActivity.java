package com.myapps.androidconcepts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilities.onPauseToUnRegister(this);
    }

}