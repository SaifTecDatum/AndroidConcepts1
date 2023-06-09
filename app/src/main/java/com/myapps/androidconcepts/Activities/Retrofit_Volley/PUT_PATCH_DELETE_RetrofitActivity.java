package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.AllTypesRetrofit_Model;
import com.myapps.androidconcepts.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PUT_PATCH_DELETE_RetrofitActivity extends AppCompatActivity {
    private TextView tv_setId, tv_ID, tv_Title, tv_Body;
    private EditText et_setId, et_Id, et_title, et_body;
    private AppCompatButton btn_Submit;

    private void initializeFields() {
        tv_setId = findViewById(R.id.tv_setId);
        tv_setId.setText("");
        tv_ID = findViewById(R.id.tv_ID);
        tv_ID.setText("");
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("");
        tv_Body = findViewById(R.id.tv_Body);
        tv_Body.setText("");
        et_setId = findViewById(R.id.et_setId);
        et_Id = findViewById(R.id.et_Id);
        et_title = findViewById(R.id.et_title);
        et_body = findViewById(R.id.et_body);
        btn_Submit = findViewById(R.id.btn_Submit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_patch_delete_retrofit);

        initializeFields();

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setId = et_setId.getText().toString().trim();
                String id = et_Id.getText().toString().trim();
                String title = et_title.getText().toString().trim();
                String body = et_body.getText().toString().trim();

                if (TextUtils.isEmpty(setId) || TextUtils.isEmpty(id) || TextUtils.isEmpty(title)) {
                    et_setId.setError("Set Id Required..!");
                    et_Id.setError("Id Required..!");
                    et_title.setError("Title Required..!");
                } else if (TextUtils.isEmpty(body)) {
                    getPATCHMethod(Integer.parseInt(setId), Integer.parseInt(id), title);           //reCorrectOnlyCertainItems-PATCH
                } else {
                    getPUTMethod(Integer.parseInt(setId), Integer.parseInt(id), title, body);       //updateAll-PUT
                }
            }
        });

    }

    private void getPUTMethod(int userId, int id, String title, String body) {
        AllTypesRetrofit_Model model = new AllTypesRetrofit_Model(userId, title, body);

        Call<AllTypesRetrofit_Model> call = MainApplication.getRestClient().getMyApi().PUT_data(id, model);
        call.enqueue(new Callback<AllTypesRetrofit_Model>() {
            @Override
            public void onResponse(Call<AllTypesRetrofit_Model> call, Response<AllTypesRetrofit_Model> response) {
                tv_setId.setText("");
                tv_ID.setText("");
                tv_Title.setText("");
                tv_Body.setText("");

                if (response.isSuccessful()) {
                    Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, "Data Updated using PUT request as: " +
                            response.body().getTitle(), Toast.LENGTH_SHORT).show();
                    tv_setId.append("Set ID: " + response.body().getUserId() + "\n\n");
                    tv_ID.append("ID: " + response.body().getId() + "\n");
                    tv_Title.append("Title: " + response.body().getTitle() + "\n");
                    tv_setId.append("Body: " + response.body().getBody() + "\n");
                } else {
                    Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }

                et_setId.setText("");
                et_Id.setText("");
                et_title.setText("");
                et_body.setText("");
            }

            @Override
            public void onFailure(Call<AllTypesRetrofit_Model> call, Throwable t) {
                Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPATCHMethod(int userId, int id, String title) {
        AllTypesRetrofit_Model model = new AllTypesRetrofit_Model(userId, title, null);

        Call<AllTypesRetrofit_Model> call = MainApplication.getRestClient().getMyApi().PATCH_data(id, model);
        call.enqueue(new Callback<AllTypesRetrofit_Model>() {
            @Override
            public void onResponse(Call<AllTypesRetrofit_Model> call, Response<AllTypesRetrofit_Model> response) {

                tv_setId.setText("");
                tv_ID.setText("");
                tv_Title.setText("");
                tv_Body.setText("");

                if (response.isSuccessful()) {
                    Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, "Data Re-Corrected using PATCH request as: " +
                            response.body().getTitle(), Toast.LENGTH_SHORT).show();
                    tv_setId.append("Set ID: " + response.body().getUserId() + "\n\n");
                    tv_ID.append("ID: " + response.body().getId() + "\n");
                    tv_Title.append("Title: " + response.body().getTitle() + "\n");
                    tv_setId.append("Body: " + response.body().getBody() + "\n");


                } else {
                    Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }

                et_setId.setText("");
                et_Id.setText("");
                et_title.setText("");
                et_body.setText("");
            }

            @Override
            public void onFailure(Call<AllTypesRetrofit_Model> call, Throwable t) {
                Toast.makeText(PUT_PATCH_DELETE_RetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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