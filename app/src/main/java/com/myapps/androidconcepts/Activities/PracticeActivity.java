package com.myapps.androidconcepts.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myapps.androidconcepts.Adapters.PracticeAdapter;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Database.PracticeDatabase;
import com.myapps.androidconcepts.Interfaces.PracticeDao;
import com.myapps.androidconcepts.Models.CurrencyModel;
import com.myapps.androidconcepts.Models.PracticeEntity;
import com.myapps.androidconcepts.Models.PracticeModel;
import com.myapps.androidconcepts.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PracticeActivity extends AppCompatActivity {
    private static final String TAG = "PracticeActivity";
    private Toolbar practice_Toolbar;
    private RecyclerView practice_RecyclerView;
    private ProgressBar practice_ProgressBar;
    private PracticeAdapter practiceAdapter;
    private PracticeDao practiceDao;
    private List<PracticeModel.Properties.Period> periodList = new ArrayList<>();

    private void initializeFields() {
        practice_Toolbar = findViewById(R.id.weatherToolbar_practice);
        practice_RecyclerView = findViewById(R.id.weatherRecyclerview_practice);
        practice_ProgressBar = findViewById(R.id.progressBar_practice);

        setSupportActionBar(practice_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_backspace_black_24dp);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        getSupportActionBar().setTitle("Practice Place");
        practice_Toolbar.setTitleTextColor(Color.WHITE);

        practice_ProgressBar.setVisibility(View.VISIBLE);

        practice_RecyclerView.setLayoutManager(new LinearLayoutManager(PracticeActivity.this));
        practice_RecyclerView.setHasFixedSize(true);


        PracticeDatabase database = Room.databaseBuilder(getApplicationContext(), PracticeDatabase.class,
                Constants.practice_room_db_name).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        practiceDao = database.practiceDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        initializeFields();

        /* //tested the coinLayer api call without UI & its working fine.
        Call<CurrencyModel> currencyCall = MainApplication.getPracticeRestClient().getMyApi().getCurrencyModel(Constants.coinLayerApi_accessKey);
        currencyCall.enqueue(new Callback<CurrencyModel>() {
            @Override
            public void onResponse(Call<CurrencyModel> call, Response<CurrencyModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "onResponse: " + response.body().getRates() + " \n"
                            + "ABC: " + response.body().getRates().getAbc() + "\n"
                            + "ACP: " + response.body().getRates().getAcp() + "\n"
                            + "ACT: " + response.body().getRates().getAct() + "\n"
                            + "ACT*: " + response.body().getRates().getAct1() + "\n"
                            + "ADA: " + response.body().getRates().getAda() + "\n"
                    );
                }
            }

            @Override
            public void onFailure(Call<CurrencyModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(PracticeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        if (isConnected()) {
            Call<PracticeModel> call = MainApplication.getPracticeRestClient().getPracticeApi().getPracticeModel();
            call.enqueue(new Callback<PracticeModel>() {
                @Override
                public void onResponse(Call<PracticeModel> call, Response<PracticeModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        periodList = response.body().getProperties().getPeriods();

                        populateData();

                        boolean isExists = practiceDao.isExists(periodList.get(0).getStartTime());

                        if (isExists) {
                            Toast.makeText(PracticeActivity.this, "Data Already Exists..!", Toast.LENGTH_SHORT).show();
                        } else {

                            practiceDao.deleteAllPracticeData();

                            for (int i = 0; i < periodList.size(); i++) {
                                practiceDao.insertPracticeData(new PracticeEntity(
                                        periodList.get(i).getNumber(),
                                        periodList.get(i).getName(),
                                        periodList.get(i).getStartTime(),
                                        periodList.get(i).getEndTime(),
                                        periodList.get(i).getTemperature(),
                                        periodList.get(i).getTemperatureUnit(),
                                        periodList.get(i).getWindSpeed(),
                                        periodList.get(i).getWindDirection(),
                                        periodList.get(i).getIcon(),
                                        periodList.get(i).getShortForecast(),
                                        periodList.get(i).getDetailedForecast()));
                            }

                            Toast.makeText(PracticeActivity.this, "New Data Updated in the DB..!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PracticeActivity.this, "Something went wrong.. ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PracticeModel> call, Throwable t) {
                    practice_ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(PracticeActivity.this, "Something went wrong.. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            List<PracticeEntity> practiceEntityList = new ArrayList<>();
            practiceEntityList = practiceDao.getAllPracticeData();

            periodList.clear();

            if (practiceEntityList != null) {
                for (int i = 0; i < practiceEntityList.size(); i++) {

                    PracticeModel.Properties.Period periodItem = new PracticeModel.Properties.Period();

                    periodItem.setNumber(practiceEntityList.get(i).getNumber());
                    periodItem.setName(practiceEntityList.get(i).getName());
                    periodItem.setStartTime(practiceEntityList.get(i).getStartTime());
                    periodItem.setEndTime(practiceEntityList.get(i).getEndTime());
                    periodItem.setTemperature(practiceEntityList.get(i).getTemperature());
                    periodItem.setTemperatureUnit(practiceEntityList.get(i).getTemperatureUnit());
                    periodItem.setWindSpeed(practiceEntityList.get(i).getWindSpeed());
                    periodItem.setWindDirection(practiceEntityList.get(i).getWindDirection());
                    periodItem.setIcon(practiceEntityList.get(i).getIcon());
                    periodItem.setShortForecast(practiceEntityList.get(i).getShortForecast());
                    periodItem.setDetailedForecast(practiceEntityList.get(i).getDetailedForecast());
                    periodList.add(periodItem);
                }
                Toast.makeText(this, "No Internet, Showing Offline Data..!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No Internet, please check your connection..!", Toast.LENGTH_SHORT).show();
            }
            populateData();
        }

    }

    private void populateData() {
        practiceAdapter = new PracticeAdapter(PracticeActivity.this, periodList);
        practice_RecyclerView.setAdapter(practiceAdapter);
        practice_ProgressBar.setVisibility(View.GONE);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}