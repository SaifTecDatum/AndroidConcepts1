package com.myapps.androidconcepts.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListViewActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private final String[] dataArrayItems = {"AndroidStudio", "Boolean", "Context", "Database", "Executor", "Finance", "Graphics",
            "Hilton", "Intel", "Java", "Kotlin", "Loading", "Master", "NavigationView", "OOPs", "Planning", "Query",
            "Retrofit", "SlideView", "Text to Speech", "Url", "Visual Studio", "Windows", "Xamarin", "Y-length", "Zinc"};
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLay);
        listView = findViewById(R.id.listView);

        populateData(dataArrayItems);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "You have Clicked: " +
                        listView.getItemAtPosition(position), Snackbar.LENGTH_INDEFINITE).setTextColor(Color.GREEN)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Undo Successful..", Snackbar.LENGTH_SHORT).setTextColor(Color.YELLOW);
                                snackbar1.show();
                            }
                        }).setActionTextColor(Color.RED);
                snackbar.show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);    //this is a inbuilt progressBar, so we're setting false to close the progress after refreshing.
                Collections.shuffle(Arrays.asList(dataArrayItems), new Random(System.currentTimeMillis()));
                populateData(dataArrayItems);
            }
        });
    }

    public void populateData(String[] arrayItems) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this, android.R.layout.simple_list_item_1, arrayItems);
        listView.setAdapter(adapter);
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