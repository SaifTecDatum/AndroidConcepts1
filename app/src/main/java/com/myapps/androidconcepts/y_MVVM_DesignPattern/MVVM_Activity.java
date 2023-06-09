package com.myapps.androidconcepts.y_MVVM_DesignPattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.databinding.ActivityMvvmBinding;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.activities.Original_MVVM_Activity;

public class MVVM_Activity extends AppCompatActivity {
    private ViewModel viewModel;
    private ActivityMvvmBinding mvvmBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvvmBinding = DataBindingUtil.setContentView(MVVM_Activity.this, R.layout.activity_mvvm);

        viewModel = ViewModelProviders.of(MVVM_Activity.this).get(ViewModel.class);
        mvvmBinding.setProductModel(viewModel.getProductModel());

        setSupportActionBar(mvvmBinding.mvvmToolbar);

        if (getSupportActionBar() != null) {    //thisBelowFunctionalityNotWorking,soFoundAnotherWayBy "onOptionsItemSelected".
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mvvm_menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.originalMvvm) {
            startActivity(new Intent(MVVM_Activity.this, Original_MVVM_Activity.class));
        }
        else if (item.getItemId() == android.R.id.home) {   //itsWorking
            onBackPressed();
        }

        return true;
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