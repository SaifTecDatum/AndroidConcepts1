package com.myapps.androidconcepts.y_MVVM_DesignPattern.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.adapters.MvvMAdapter;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.models.NicePlaces;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.viewModels.MvvmOriginalViewModel;

import java.util.ArrayList;
import java.util.List;

public class Original_MVVM_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private MvvMAdapter myAdapter;
    private List<NicePlaces> modelList = new ArrayList<>();
    private MvvmOriginalViewModel viewModel;

    private void initializeFields() {
        toolbar = findViewById(R.id.mvvmOriginal_Toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        floatingActionButton = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MVVM Original");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(Original_MVVM_Activity.this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_mvvm);

        initializeFields();

        viewModel = ViewModelProviders.of(Original_MVVM_Activity.this).get(MvvmOriginalViewModel.class);

        viewModel.inIt();

        viewModel.getLiveData().observe(Original_MVVM_Activity.this, new Observer<List<NicePlaces>>() {
            @Override
            public void onChanged(List<NicePlaces> nicePlaces) {
                myAdapter.notifyDataSetChanged();
            }
        });

        modelList = viewModel.getLiveData().getValue();

        viewModel.getIsUpdating().observe(Original_MVVM_Activity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                    recyclerView.smoothScrollToPosition(modelList.size() - 1);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MvvmOriginalViewModel.addNewData(new NicePlaces("https://media-exp1.licdn.com/dms/image/C5103AQFWl8vuTyudUg/profile-displayphoto-shrink_800_800/0/" +
                        "1533841710463?e=2147483647&v=beta&t=dJW3BRdKOxbzSszkPUzBuWwt4Pw-09uvk0s_QJZ_azs", "Saifuddin Mohammed - LinkedIn"));
            }
        });

        myAdapter = new MvvMAdapter(Original_MVVM_Activity.this, modelList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(Original_MVVM_Activity.this, DividerItemDecoration.VERTICAL));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}