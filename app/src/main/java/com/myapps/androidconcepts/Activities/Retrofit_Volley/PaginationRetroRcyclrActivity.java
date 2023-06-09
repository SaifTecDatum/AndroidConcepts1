package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Adapters.PaginationAdapter;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.Pagination_Model;
import com.myapps.androidconcepts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaginationRetroRcyclrActivity extends AppCompatActivity {
    private static final String TAG = "PaginationRetroRcyclrAc";
    private NestedScrollView mNestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final List<Pagination_Model> modelList = new ArrayList<>();
    private PaginationAdapter adapter;
    private int page = 1;
    private final int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination_retro_rcyclr);

        mNestedScrollView = findViewById(R.id.nestedScrollView);
        recyclerView = findViewById(R.id.pagination_RecyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(PaginationRetroRcyclrActivity.this));
        recyclerView.setHasFixedSize(true);

        getData(page, limit);

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {  // end of the scroll view

                    Log.e(TAG, "startingItemToLastVisibleItemsScreenHeightSize: " + v.getChildAt(0).getMeasuredHeight());   //also popularly known as totalItemCount
                    Log.e(TAG, "deviceScreenHeightSize: " + v.getMeasuredHeight());   //deviceScreenHeight
                    Log.e(TAG, "scroll_Y: " + scrollY);  //currentVisibleLastItemPosition (or) LastVisibleItemPosition

                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });


       /*mNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) mNestedScrollView.getChildAt(mNestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (mNestedScrollView.getHeight() + mNestedScrollView.getScrollY()));

                if (diff == 0) {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });*/   //Another approach for Pagination process in NestedScrollView - workingFine.

    }

    private void getData(int page, int limit) {

        Call<String> call = MainApplication.getPaginationRestClient().getMyApi().STRING_CALL(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Pagination_Model model = new Pagination_Model();

                            model.setId(jsonObject.getString(model.getId()));
                            model.setAuthor(jsonObject.getString(model.getAuthor()));
                            model.setDownload_url(jsonObject.getString(model.getDownload_url()));

                            modelList.add(model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter = new PaginationAdapter(PaginationRetroRcyclrActivity.this, modelList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(PaginationRetroRcyclrActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PaginationRetroRcyclrActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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