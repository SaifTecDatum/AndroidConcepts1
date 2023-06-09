package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Adapters.AirlinesRetroAdapter;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.RetrofitAirlines_Model;
import com.myapps.androidconcepts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirlinesRetrofitActivity extends AppCompatActivity {
    private final List<RetrofitAirlines_Model> airlinesModelList = new ArrayList<>();
    private final int limit = 10;
    private RecyclerView airlines_RecyclerView;
    private AirlinesRetroAdapter adapter;
    private ProgressDialog progressDialog;
    private NestedScrollView mNestedScrollView;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airlines_retrofit);

        airlines_RecyclerView = findViewById(R.id.airlines_RecyclerView);
        mNestedScrollView = findViewById(R.id.nestedScrollView);
        progressDialog = new ProgressDialog(AirlinesRetrofitActivity.this);

        airlines_RecyclerView.setLayoutManager(new LinearLayoutManager(AirlinesRetrofitActivity.this));
        airlines_RecyclerView.setHasFixedSize(true);

        setProgressDialog();

        //hereInBelowPaginationMethod,PaginationIsNotWorking.bCozServerDoesn'tProvidePaginationQueries(page,limit)
        //butGettingCertainStartingLimitOfDataAgain&AgainWhileScrolling.
        GET_Pagination_AirlinesData(page, limit);

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    setProgressDialog();
                    page++;
                    GET_Pagination_AirlinesData(page, limit);
                }
            }
        });

        //GET_AirlinesData();
    }

    private void GET_Pagination_AirlinesData(int page, int limit) {

        Call<String> call = MainApplication.getAirlinesRestClient().getMyApi().STRING_CALL1(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();

                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            RetrofitAirlines_Model airlinesModel = new RetrofitAirlines_Model();

                            airlinesModel.setId(jsonObject.getString("id"));
                            airlinesModel.setName(jsonObject.getString("name"));
                            airlinesModel.setCountry(jsonObject.getString("country"));
                            airlinesModel.setLogo(jsonObject.getString("logo"));
                            airlinesModel.setSlogan(jsonObject.getString("slogan"));
                            airlinesModel.setHead_quaters(jsonObject.getString("head_quaters"));
                            airlinesModel.setWebsite(jsonObject.getString("website"));
                            airlinesModel.setEstablished(jsonObject.getString("established"));

                            airlinesModelList.add(airlinesModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter = new AirlinesRetroAdapter(AirlinesRetrofitActivity.this, airlinesModelList);
                    airlines_RecyclerView.setAdapter(adapter);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AirlinesRetrofitActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AirlinesRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void GET_AirlinesData() {
        Call<List<RetrofitAirlines_Model>> call = MainApplication.getAirlinesRestClient().getMyApi().getAirlinesData();
        call.enqueue(new Callback<List<RetrofitAirlines_Model>>() {
            @Override
            public void onResponse(Call<List<RetrofitAirlines_Model>> call, Response<List<RetrofitAirlines_Model>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    airlinesModelList = response.body();

                    airlines_RecyclerView.setLayoutManager(new LinearLayoutManager(AirlinesRetrofitActivity.this));
                    airlines_RecyclerView.setHasFixedSize(true);
                    airlines_RecyclerView.addItemDecoration(new DividerItemDecoration(AirlinesRetrofitActivity.this,
                            DividerItemDecoration.VERTICAL));
                    adapter = new AirlinesRetroAdapter(AirlinesRetrofitActivity.this, airlinesModelList);
                    airlines_RecyclerView.setAdapter(adapter);
                    airlines_RecyclerView.smoothScrollToPosition(View.FOCUS_DOWN);

                } else {
                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AirlinesRetrofitActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitAirlines_Model>> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AirlinesRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void setProgressDialog() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("shortly opening Airlines Info..!");
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