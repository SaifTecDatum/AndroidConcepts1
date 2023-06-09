package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CollapsingToolbarActivity extends AppCompatActivity {
    private TextView tv_jsonVolleyData;
    private FloatingActionButton fab_Cart;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar);

        tv_jsonVolleyData = findViewById(R.id.tv_jsonVolleyData);
        tv_jsonVolleyData.setText("");
        fab_Cart = findViewById(R.id.fab_Cart);

        volleyJsonGETData();

        fab_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(CollapsingToolbarActivity.this, CollapsingToolbarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(CollapsingToolbarActivity.this, "Opened Same Activity..", Toast.LENGTH_SHORT).show();*/


                //for those who don't want to see that blink after recreate() activity, simply use below 4 lines.
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    private void volleyJsonGETData() {
        requestQueue = Volley.newRequestQueue(CollapsingToolbarActivity.this);

        String baseUrl = "https://jsonplaceholder.typicode.com/comments";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    int Id = jsonObject.getInt("id");
                                    String Name = jsonObject.getString("name");
                                    String Email = jsonObject.getString("email");
                                    String Body = jsonObject.getString("body");

                                    tv_jsonVolleyData.append(
                                            "ID: " + Id + "\n" +
                                                    "Name: " + Name + "\n" +
                                                    "Email: " + Email + "\n" +
                                                    "Body: " + Body + "\n\n");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(CollapsingToolbarActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CollapsingToolbarActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
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