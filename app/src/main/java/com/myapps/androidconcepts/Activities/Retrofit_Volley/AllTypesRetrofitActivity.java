package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Adapters.AllTypesRetroAdapter;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.AllTypesRetrofit_Model;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTypesRetrofitActivity extends AppCompatActivity {
    private RecyclerView allTypeRecyclerView;
    private List<AllTypesRetrofit_Model> modelList = new ArrayList<>();
    private AllTypesRetroAdapter adapter;
    private ProgressDialog progressDialog;
    private FloatingActionButton fab_allTypeRetro;
    private BottomSheetDialog bottomSheetDialog;
    private EditText et_Id, et_Title, et_Description;
    private AppCompatButton btn_Submit;
    private SwipeRefreshLayout swipeRefreshLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_types_retrofit);

        allTypeRecyclerView = findViewById(R.id.allTypeRecyclerView);
        fab_allTypeRetro = findViewById(R.id.fab_allTypeRetro);
        swipeRefreshLay = findViewById(R.id.swipeRefreshLay);
        progressDialog = new ProgressDialog(AllTypesRetrofitActivity.this);

        allTypeRecyclerView.setLayoutManager(new LinearLayoutManager(AllTypesRetrofitActivity.this));
        allTypeRecyclerView.setHasFixedSize(true);
        allTypeRecyclerView.addItemDecoration(new DividerItemDecoration(AllTypesRetrofitActivity.this, DividerItemDecoration.VERTICAL));

        setProgressDialog();

        retrofit_GET_Data();

        //retrofit_POST_Data();
        //retrofit_PUT_Data();

        swipeRefreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLay.setRefreshing(false);
                Collections.shuffle(modelList, new Random(System.currentTimeMillis()));
                adapter = new AllTypesRetroAdapter(AllTypesRetrofitActivity.this, modelList);
                allTypeRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void retrofit_GET_Data() {
        Call<List<AllTypesRetrofit_Model>> call_GET = MainApplication.getRestClient().getMyApi().GET_data();
        call_GET.enqueue(new Callback<List<AllTypesRetrofit_Model>>() {
            @Override
            public void onResponse(Call<List<AllTypesRetrofit_Model>> call, Response<List<AllTypesRetrofit_Model>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    modelList = response.body();

                    adapter = new AllTypesRetroAdapter(AllTypesRetrofitActivity.this, modelList);
                    allTypeRecyclerView.setAdapter(adapter);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AllTypesRetrofitActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AllTypesRetrofit_Model>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllTypesRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setProgressDialog() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("shortly opening Users Info..!");
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

    /*
    private void retrofit_PUT_Data() {
    }

    private void retrofit_POST_Data() {
        fab_allTypeRetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(AllTypesRetrofitActivity.this, R.style.BottomSheetStyle);
                bottomSheetDialog.setContentView(R.layout.btmsht_all_types_retro);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                et_Id = bottomSheetDialog.findViewById(R.id.et_Id);
                et_Title = bottomSheetDialog.findViewById(R.id.et_Title);
                et_Description = bottomSheetDialog.findViewById(R.id.et_Description);
                btn_Submit = bottomSheetDialog.findViewById(R.id.btn_Submit);

                String Id = et_Id.getText().toString().trim();
                String title = et_Title.getText().toString().trim();
                String description = et_Description.getText().toString().trim();

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (TextUtils.isEmpty(Id)) {
                            et_Id.setError("UserId Required..!");
                        }
                        else if (TextUtils.isEmpty(title)) {
                            et_Title.setError("Title Required..!");
                        }
                        else if (TextUtils.isEmpty(description)) {
                            et_Description.setError("Description Required..!");
                        }
                        else {

                            AllTypesRetrofit_Model model = new AllTypesRetrofit_Model(11, Integer.parseInt(Id), title, description);

                            Call<AllTypesRetrofit_Model> call = MainApplication.getRestClient().getMyApi().POST_data(model);

                            call.enqueue(new Callback<AllTypesRetrofit_Model>() {
                                @Override
                                public void onResponse(Call<AllTypesRetrofit_Model> call, Response<AllTypesRetrofit_Model> response) {
                                    if (response.isSuccessful()) {
                                        bottomSheetDialog.dismiss();
                                        startActivity(new Intent(AllTypesRetrofitActivity.this, AllTypesRetrofitActivity.class));
                                        finish();
                                        allTypeRecyclerView.smoothScrollToPosition(View.FOCUS_DOWN);
                                        Toast.makeText(AllTypesRetrofitActivity.this,
                                                title + " & " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(AllTypesRetrofitActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<AllTypesRetrofit_Model> call, Throwable t) {
                                    bottomSheetDialog.dismiss();
                                }
                            });
                        }
                    }
                });


                bottomSheetDialog.show();

            }
        });

    }*/
}