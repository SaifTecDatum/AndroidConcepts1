package com.myapps.androidconcepts.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class NavigationComponent_Activity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_component);

       /* NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.FCV_navHostFragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Setup NavigationUI here
            NavigationUI.setupActionBarWithNavController(this, navController);
        }*/

        /*navController = Navigation.findNavController(this, R.id.FCV_navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController);*/

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