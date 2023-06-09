package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonVolleyActivity extends AppCompatActivity {
    private TextView tv_jsonVolleyGETData, tv_jsonVolleyPOSTData;
    private AppCompatButton btn_jsonVolleyGETData, btn_Submit;
    private RequestQueue requestQueue;
    private TextInputLayout TIL_1, TIL_2;
    private EditText et_name, et_job;
    private NestedScrollView nestedScrollView;

    private void InitializeFields() {
        tv_jsonVolleyGETData = findViewById(R.id.tv_jsonVolleyGETData);
        tv_jsonVolleyGETData.setText("");
        tv_jsonVolleyPOSTData = findViewById(R.id.tv_jsonVolleyPOSTData);
        tv_jsonVolleyPOSTData.setText("");
        btn_jsonVolleyGETData = findViewById(R.id.btn_jsonVolleyGETData);
        btn_Submit = findViewById(R.id.btn_Submit);
        TIL_1 = findViewById(R.id.TIL_1);
        TIL_2 = findViewById(R.id.TIL_2);
        et_name = findViewById(R.id.et_name);
        et_job = findViewById(R.id.et_job);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        tv_jsonVolleyGETData.setVisibility(View.GONE);
        tv_jsonVolleyPOSTData.setVisibility(View.VISIBLE);
        TIL_1.setVisibility(View.VISIBLE);
        TIL_2.setVisibility(View.VISIBLE);
        btn_Submit.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_volley);

        InitializeFields();

        requestQueue = Volley.newRequestQueue(JsonVolleyActivity.this);

        btn_jsonVolleyGETData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_jsonVolleyGETData.getVisibility() == View.GONE) {
                    tv_jsonVolleyGETData.setVisibility(View.VISIBLE);
                    tv_jsonVolleyPOSTData.setVisibility(View.GONE);
                    TIL_1.setVisibility(View.GONE);
                    TIL_2.setVisibility(View.GONE);
                    btn_Submit.setVisibility(View.GONE);
                } else {
                    tv_jsonVolleyGETData.setVisibility(View.GONE);
                    tv_jsonVolleyPOSTData.setVisibility(View.VISIBLE);
                    TIL_1.setVisibility(View.VISIBLE);
                    TIL_2.setVisibility(View.VISIBLE);
                    btn_Submit.setVisibility(View.VISIBLE);
                }

                jsonParsingGET_viaVolley();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParsingPOST_viaVolley();
            }
        });
    }

    private void jsonParsingGET_viaVolley() {
        String url = "https://jsonplaceholder.typicode.com/comments";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null && response.length() > 0) {

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    int id = jsonObject.getInt("id");
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String body = jsonObject.getString("body");

                                    tv_jsonVolleyGETData.append(
                                            "ID: " + id + "\n" +
                                                    "Name: " + name + "\n" +
                                                    "Email: " + email + "\n" +
                                                    "Description: " + body + "\n\n");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(JsonVolleyActivity.this, "Response Error..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JsonVolleyActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void jsonParsingPOST_viaVolley() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(JsonVolleyActivity.this);

        String postUrl = "https://reqres.in/api/users";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        et_name.setText("");
                        et_job.setText("");
                        Toast.makeText(JsonVolleyActivity.this, "Data added to the API..!", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String name = jsonObject.getString("name");
                            String job = jsonObject.getString("job");

                            tv_jsonVolleyPOSTData.setText("Name: " + name + "\nJob: " + job);
                        }
                        catch (JSONException e) {
                            Toast.makeText(JsonVolleyActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JsonVolleyActivity.this, "Something went Wrong.. " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();

                Map<String, String> postMap = new HashMap<>();
                postMap.put("name", et_name.getText().toString().trim());
                postMap.put("job", et_job.getText().toString().trim());

                return postMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();

                Map<String, String> postMap = new HashMap<>();
                postMap.put("Accept", "application/json");
                postMap.put("Accept-Language", "en-gb");
                //postMap.put("Content-Type", "application/json");  //we don't have this header in api. so not using.

                /*postMap.put("Connection", "keep-alive");
                postMap.put("Cache-Control", "no-cache");
                postMap.put("User-Agent", "PostmanRuntime/7.29.2");
                postMap.put("Content-Length", "");*/    //these are the some default headers from this api & working fine if we uncomment here.
                                                        // But unnecessary to mention default headers.
                return postMap;
            }
        };

        requestQueue1.add(stringRequest);
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
