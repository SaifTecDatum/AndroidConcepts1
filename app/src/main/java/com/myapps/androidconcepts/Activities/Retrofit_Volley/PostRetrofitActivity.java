package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.Request_Model;
import com.myapps.androidconcepts.Models.Response_Model;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRetrofitActivity extends AppCompatActivity {
    private static final String TAG = "PostRetrofitActivity";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Toolbar post_Toolbar;
    private SwipeRefreshLayout swipeRefreshLay;
    private TextView tv_name, tv_id, tv_email, tv_gender, tv_status, tv_fetchData;
    private FloatingActionButton fab_addUser;
    private EditText et_name, et_email;
    private RadioButton rdBtn_male, rdBtn_female, rdBtn_active, rdBtn_inActive;
    private AppCompatButton btn_Submit;
    private ProgressDialog progressDialog;
    private BottomSheetDialog bottomSheetDialog;
    private String genderValue, statusValue;

    private void initializeFields() {
        post_Toolbar = findViewById(R.id.post_Toolbar);
        setSupportActionBar(post_Toolbar);

        swipeRefreshLay = findViewById(R.id.swipeRefreshLay);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText("");

        tv_id = findViewById(R.id.tv_id);
        tv_id.setText("");

        tv_email = findViewById(R.id.tv_email);
        tv_email.setText("");

        tv_gender = findViewById(R.id.tv_gender);
        tv_gender.setText("");

        tv_status = findViewById(R.id.tv_status);
        tv_status.setText("");

        tv_fetchData = findViewById(R.id.tv_fetchData);
        tv_fetchData.setText("");

        fab_addUser = findViewById(R.id.fab_addUser);

        progressDialog = new ProgressDialog(PostRetrofitActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_retrofit);

        initializeFields();

        ProgressDialog();

        Single<List<Response_Model>> single = MainApplication.getPostRestClient().getMyApi().getAllPostData(1, 50);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Response_Model>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NotNull List<Response_Model> response_models) {
                        if (response_models != null) {

                            progressDialog.dismiss();

                            for (int i = 0; i < response_models.size(); i++) {
                                tv_fetchData.append(
                                        "Id: " + response_models.get(i).getId() +
                                                "\nName: " + response_models.get(i).getName() +
                                                "\nEmail Id: " + response_models.get(i).getEmail() +
                                                "\nGender: " + response_models.get(i).getGender() +
                                                "\nStatus: " + response_models.get(i).getStatus() +
                                                "\n\n");
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PostRetrofitActivity.this, "Something Went Wrong.., maybe this data already exists..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(PostRetrofitActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        fab_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(PostRetrofitActivity.this, R.style.BottomSheetStyle);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_retrofit);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                et_name = bottomSheetDialog.findViewById(R.id.et_name);
                et_email = bottomSheetDialog.findViewById(R.id.et_email);
                rdBtn_male = bottomSheetDialog.findViewById(R.id.rdBtn_male);
                rdBtn_female = bottomSheetDialog.findViewById(R.id.rdBtn_female);
                rdBtn_active = bottomSheetDialog.findViewById(R.id.rdBtn_active);
                rdBtn_inActive = bottomSheetDialog.findViewById(R.id.rdBtn_inActive);
                btn_Submit = bottomSheetDialog.findViewById(R.id.btn_Submit);

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                            Toast.makeText(PostRetrofitActivity.this, "Name should not be Empty..!",
                                    Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
                            Toast.makeText(PostRetrofitActivity.this, "Email Id should not be Empty..!",
                                    Toast.LENGTH_SHORT).show();
                        } else if (!rdBtn_male.isChecked() && !rdBtn_female.isChecked()) {
                            Toast.makeText(PostRetrofitActivity.this, "Gender should not be Empty..!", Toast.LENGTH_SHORT).show();
                        } else if (!rdBtn_active.isChecked() && !rdBtn_inActive.isChecked()) {
                            Toast.makeText(PostRetrofitActivity.this, "Status should not be Empty..!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (rdBtn_male.isChecked()) {
                                rdBtn_male.setChecked(true);
                                rdBtn_female.setChecked(false);
                                genderValue = rdBtn_male.getText().toString().trim();
                            } else if (rdBtn_female.isChecked()) {
                                rdBtn_female.setChecked(true);
                                rdBtn_male.setChecked(false);
                                genderValue = rdBtn_female.getText().toString().trim();
                            }

                            if (rdBtn_active.isChecked()) {
                                rdBtn_active.setChecked(true);
                                rdBtn_inActive.setChecked(false);
                                statusValue = rdBtn_active.getText().toString().trim();
                            } else if (rdBtn_inActive.isChecked()) {
                                rdBtn_inActive.setChecked(true);
                                rdBtn_active.setChecked(false);
                                statusValue = rdBtn_inActive.getText().toString().trim();
                            }

                            Request_Model request_model = new Request_Model(
                                    et_name.getText().toString().trim(),
                                    et_email.getText().toString().trim(),
                                    genderValue,
                                    statusValue);

                            //reactiveX with retrofit approach
                            Single<Response_Model> singlePost = MainApplication.getPostRestClient().getMyApi().postUserData1(request_model);
                            singlePost.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<Response_Model>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            compositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(Response_Model response_model) {
                                            if (response_model != null) {
                                                tv_id.setText(response_model.getId() + "");
                                                tv_name.setText(response_model.getName());
                                                tv_email.setText(response_model.getEmail());
                                                tv_gender.setText(response_model.getGender());
                                                tv_status.setText(response_model.getStatus());
                                            } else {
                                                Toast.makeText(PostRetrofitActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                            }
                                            bottomSheetDialog.dismiss();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            bottomSheetDialog.dismiss();
                                            Toast.makeText(PostRetrofitActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            /*  //only retrofit approach
                            Call<Response_Model> call = MainApplication.getPostRestClient().getMyApi().postUserData(request_model);

                            call.enqueue(new Callback<Response_Model>() {
                                @Override
                                public void onResponse(Call<Response_Model> call, Response<Response_Model> response) {
                                    if (response.isSuccessful()) {
                                        Response_Model response_model = response.body();

                                        tv_id.setText(response_model.getId() + "");
                                        tv_name.setText(response_model.getName());
                                        tv_email.setText(response_model.getEmail());
                                        tv_gender.setText(response_model.getGender());
                                        tv_status.setText(response_model.getStatus());
                                    } else {
                                        Toast.makeText(PostRetrofitActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                    }
                                    bottomSheetDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Response_Model> call, Throwable t) {
                                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                                    Toast.makeText(PostRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    bottomSheetDialog.dismiss();
                                }
                            });*/
                        }
                    }
                });

                bottomSheetDialog.show();
            }
        });

        /*
        //Fetching/Retrieving Only certain Data from Server wch i have entered by the help of Id's.
        listOfIds.add(919);
        listOfIds.add(921);
        listOfIds.add(922);
        listOfIds.add(923);
        listOfIds.add(926);
        listOfIds.add(930);
        listOfIds.add(931);
        listOfIds.add(933);
        listOfIds.add(936);
        listOfIds.add(937);
        listOfIds.add(938);
        listOfIds.add(939);
        listOfIds.add(947);

        for (int i=0; i<listOfIds.size(); i++) {

            Call<Response_Model> postCall = MainApplication.getPostRestClient().getMyApi().getNetworkDetails(listOfIds.get(i));

            postCall.enqueue(new Callback<Response_Model>() {
                @Override
                public void onResponse(Call<Response_Model> call, Response<Response_Model> response) {
                    if (response.isSuccessful()) {
                        Response_Model responseModel = response.body();

                        tv_fetchData.append("S.no: " + responseModel.getId() + "\nUsername: " +
                                responseModel.getUsername() + "\nEmail: " + responseModel.getEmail() +
                                "\nDate_Joined: " + responseModel.getDate_joined() + "\n\n");
                    }
                    else {
                        Toast.makeText(PostRetrofitActivity.this, "FetchingDataError", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_Model> call, Throwable t) {
                    Toast.makeText(PostRetrofitActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }*/

        swipeRefreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLay.setRefreshing(false);   //it is a inbuilt progressBar of swipeRefreshLayout for wch
                //we're turning off after refreshing.
                startActivity(new Intent(PostRetrofitActivity.this, PostRetrofitActivity.class));
                finish();
            }
        });
    }

    public void ProgressDialog() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("fetching data from backend..");
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