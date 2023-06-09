package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Adapters.EcommerceRetrofitAdapter;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.RetrofitEcommerce_Model;
import com.myapps.androidconcepts.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EcommerceRetrofitActivity extends AppCompatActivity {
    private RecyclerView eCommerceRecyclerView;
    private List<RetrofitEcommerce_Model> ecommerce_modelList;
    private EcommerceRetrofitAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecommerce_retrofit);

        eCommerceRecyclerView = findViewById(R.id.eCommerceRecyclerView);
        progressDialog = new ProgressDialog(EcommerceRetrofitActivity.this);
        setProgressDialog();

        eCommerceRecyclerView.setLayoutManager(new LinearLayoutManager(EcommerceRetrofitActivity.this));
        eCommerceRecyclerView.setHasFixedSize(true);
        eCommerceRecyclerView.addItemDecoration(new DividerItemDecoration(EcommerceRetrofitActivity.this,
                DividerItemDecoration.VERTICAL));


        Call<List<RetrofitEcommerce_Model>> call = MainApplication.geteCommerceRestClient().getMyApi().getEcommerceRetroModel();

        call.enqueue(new Callback<List<RetrofitEcommerce_Model>>() {
            @Override
            public void onResponse(Call<List<RetrofitEcommerce_Model>> call, Response<List<RetrofitEcommerce_Model>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    ecommerce_modelList = response.body();

                    adapter = new EcommerceRetrofitAdapter(EcommerceRetrofitActivity.this, ecommerce_modelList);
                    eCommerceRecyclerView.setAdapter(adapter);
                    eCommerceRecyclerView.smoothScrollToPosition(View.FOCUS_DOWN);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(EcommerceRetrofitActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitEcommerce_Model>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EcommerceRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProgressDialog() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("shortly opening E-Commerce Info..!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
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