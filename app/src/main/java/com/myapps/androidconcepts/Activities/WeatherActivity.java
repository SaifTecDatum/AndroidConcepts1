package com.myapps.androidconcepts.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.gson.Gson;
import com.myapps.androidconcepts.Adapters.WeatherAdapter;
import com.myapps.androidconcepts.Database.WeatherDatabase;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Interfaces.WeatherDao;
import com.myapps.androidconcepts.Models.WeatherEntity;
import com.myapps.androidconcepts.Models.WeatherModel;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private WeatherAdapter weatherAdapter;
    private List<WeatherModel.Properties.Period> periodList = new ArrayList<>();

    private void initializeFields() {
        toolbar = findViewById(R.id.weatherToolbar);
        recyclerView = findViewById(R.id.weatherRecyclerview);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
        recyclerView.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Weather Report");
        toolbar.setTitleTextColor(Color.BLACK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initializeFields();

        WeatherDatabase database = Room.databaseBuilder(getApplicationContext(), WeatherDatabase.class,
                Constants.weather_room_db_name).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        WeatherDao weatherDao = database.weatherDao();


        if (isConnected()) {
            Call<WeatherModel> call = MainApplication.getWeatherRestClient().getWeatherApi().getModel();

            call.enqueue(new Callback<WeatherModel>() {
                @Override
                public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        periodList = response.body().getProperties().getPeriods();

                        populateData();

                        Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));

                        Boolean exists = weatherDao.isExists(periodList.get(0).getStartTime());

                        if (exists) {
                            Toast.makeText(WeatherActivity.this, "Database Already Exists..!", Toast.LENGTH_SHORT).show();
                        } else {
                            weatherDao.delete();

                            for (int i = 0; i < periodList.size(); i++) {
                                weatherDao.insertWeatherData(new WeatherEntity(
                                        periodList.get(i).getNumber(),
                                        periodList.get(i).getName(),
                                        periodList.get(i).getTemperature(),
                                        periodList.get(i).getTemperatureUnit(),
                                        periodList.get(i).getIcon(),
                                        periodList.get(i).getStartTime(),
                                        periodList.get(i).getEndTime()));
                            }
                            Toast.makeText(WeatherActivity.this, "Database list Updated..!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(WeatherActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(WeatherActivity.this, "Something Went Wrong..! " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            List<WeatherEntity> entityList = new ArrayList<>();
            entityList = weatherDao.getAllWeatherData();

            periodList.clear();
            if (entityList != null) {
                for (int i = 0; i < entityList.size(); i++) {
                    WeatherModel.Properties.Period periodItem = new WeatherModel.Properties.Period();
                    periodItem.setNumber(entityList.get(i).getId());
                    periodItem.setName(entityList.get(i).getPeriodsName());
                    periodItem.setTemperature(entityList.get(i).getPeriodsTemp());
                    periodItem.setTemperatureUnit(entityList.get(i).getPeriodsTempUnit());
                    periodItem.setIcon(entityList.get(i).getPeriodsIcon());
                    periodItem.setStartTime(entityList.get(i).getPeriodsStartTime());
                    periodItem.setEndTime(entityList.get(i).getPeriodsEndTime());
                    periodList.add(periodItem);
                }
                Toast.makeText(this, "No Internet Connection, Loading previous Data..!", Toast.LENGTH_SHORT).show();
            }
            populateData();
        }
    }

    private void populateData() {
        weatherAdapter = new WeatherAdapter(WeatherActivity.this, periodList);
        recyclerView.setAdapter(weatherAdapter);
        progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("MissingPermission")
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /*@Override
    protected void onResume() {
        super.onResume();           //NoInternet facility kept in comment bcoz we have offline db facility here.

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Utilities.onPauseToUnRegister(this);
    }*/
}