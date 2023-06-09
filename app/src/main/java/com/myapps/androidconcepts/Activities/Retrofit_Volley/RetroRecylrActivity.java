package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myapps.androidconcepts.Adapters.Retro_RecylrAdapter;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.RecyclerRetrofit_Model;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRecylrActivity extends AppCompatActivity {
    private static final String TAG = "RetroRecylrActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView retroRcylrView;
    private List<RecyclerRetrofit_Model> modelList = new ArrayList<>();
    private Retro_RecylrAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_recylr);

        retroRcylrView = findViewById(R.id.retroRcylrView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLay);

        retroRcylrView.setLayoutManager(new LinearLayoutManager(RetroRecylrActivity.this));
        retroRcylrView.addItemDecoration(new DividerItemDecoration(RetroRecylrActivity.this, DividerItemDecoration.VERTICAL));//it'llAddDividerInRecyclerList.
        retroRcylrView.setHasFixedSize(true);

        Call<List<RecyclerRetrofit_Model>> call = MainApplication.getRecylrRestClient().getMyApi().getRecyclerModel();

        call.enqueue(new Callback<List<RecyclerRetrofit_Model>>() {
            @Override
            public void onResponse(Call<List<RecyclerRetrofit_Model>> call, Response<List<RecyclerRetrofit_Model>> response) {
                if (response.isSuccessful()) {
                    modelList = response.body();

                    adapter = new Retro_RecylrAdapter(RetroRecylrActivity.this, modelList);
                    retroRcylrView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(RetroRecylrActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecyclerRetrofit_Model>> call, Throwable t) {
                Toast.makeText(RetroRecylrActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Collections.shuffle(modelList, new Random(System.currentTimeMillis()));
                adapter = new Retro_RecylrAdapter(RetroRecylrActivity.this, modelList);
                retroRcylrView.setAdapter(adapter);
            }
        });

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