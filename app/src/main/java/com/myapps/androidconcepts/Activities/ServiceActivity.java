package com.myapps.androidconcepts.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.FcmPostModel;
import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.Services.FcmNotificationsSender;
import com.myapps.androidconcepts.Services.MyService;
import com.myapps.androidconcepts.Z_BoundServiceWithMVVM.BoundService;
import com.myapps.androidconcepts.Z_BoundServiceWithMVVM.ServiceActivityViewModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";
    private AppCompatButton btn_serviceOn, btn_serviceOff, btn_multiAction;
    private String currentUserName, momsMobile_deviceToken, currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private BoundService boundService;
    private ServiceActivityViewModel viewModel;
    private ProgressBar mProgressBar;
    private TextView tv_percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btn_serviceOn = findViewById(R.id.btn_serviceOn);
        btn_serviceOff = findViewById(R.id.btn_serviceOff);

        btn_multiAction = findViewById(R.id.btn_multiAction);
        mProgressBar = findViewById(R.id.progressBar);
        tv_percentage = findViewById(R.id.tv_percentage);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        currentUserId = mAuth.getCurrentUser().getUid();



        btn_serviceOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(new Intent(ServiceActivity.this, MyService.class));
            }
        });

        btn_serviceOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopService(new Intent(ServiceActivity.this, MyService.class));
            }
        });



        usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild(Constants.userFullName)) {
                    currentUserName = snapshot.child(Constants.userFullName).getValue().toString().trim();
                } else {
                    Toast.makeText(ServiceActivity.this, "current User Name Didn't found..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        usersRef.child(Constants.momsDeviceUniqueId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild(Constants.device_token)) {
                    momsMobile_deviceToken = snapshot.child(Constants.device_token).getValue().toString();
                } else {
                    Toast.makeText(ServiceActivity.this, "Something went wrong to fetch the mom's mobile deviceToken..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        viewModel = ViewModelProviders.of(ServiceActivity.this).get(ServiceActivityViewModel.class);
        setObservers();

        btn_multiAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleUpdates();
            }
        });

    }

    public void Notification_Btn(View view) {
        String title = "Hi Ammijaan's Mobile,";   //normallyWeDoFetchHereReceiverUserName&hisDeviceTokenFromFirebaseDB-LetsChat.
        String msg = currentUserName + " wants to be friend with you on Android_Concepts. ";  //Note:weGetThisNotificationOnlyInAmmijaan'sMobileBcozWeGaveThatDeviceTokenOnly.

        //process using Volley
        FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender(momsMobile_deviceToken,
                title, msg, getApplicationContext(), ServiceActivity.this);
        fcmNotificationsSender.sendNotification();


        /*//process using Retrofit

        FcmPostModel fcmPostModel = new FcmPostModel();
        fcmPostModel.setTo(momsMobile_deviceToken);
        FcmPostModel.Notification notification = new FcmPostModel.Notification();
        notification.setTitle(title);
        notification.setBody(msg);
        notification.setIcon("app_logo");
        fcmPostModel.setNotification(notification);

        Call<FcmPostModel> call = MainApplication.getFcmRestClient().getFcmPostApi().postFcmData(fcmPostModel);
        call.enqueue(new Callback<FcmPostModel>() {
            @Override
            public void onResponse(Call<FcmPostModel> call, Response<FcmPostModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ServiceActivity.this, "Notification Successfully Sent in Ammijaan's Mobile.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FcmPostModel> call, Throwable t) {
                Toast.makeText(ServiceActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }



    private void setObservers() {
        viewModel.getMyBinder().observe(this, new Observer<BoundService.MyBinder>() {
            @Override
            public void onChanged(@Nullable BoundService.MyBinder myBinder) {
                if (myBinder == null) {
                    Log.d(TAG, "onChanged: unbound from service");
                } else {
                    Log.d(TAG, "onChanged: bound to service.");
                    boundService = myBinder.getService();
                }
            }
        });


        viewModel.getIsProgressUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (viewModel.getIsProgressUpdating().getValue()) {
                            if (viewModel.getMyBinder().getValue() != null) {
                                if (boundService.getProgress() == boundService.getMaxValue()) {
                                    viewModel.setIsProgressUpdating(false);
                                }
                                mProgressBar.setProgress(boundService.getProgress());
                                mProgressBar.setMax(boundService.getMaxValue());
                                String progress = 100 * boundService.getProgress() / boundService.getMaxValue() + "%";
                                tv_percentage.setText(progress);
                            }
                            handler.postDelayed(this, 100);
                        } else {
                            handler.removeCallbacks(this);
                        }
                    }
                };

                if (aBoolean) {
                    btn_multiAction.setText("Pause");
                    handler.postDelayed(runnable, 100);
                } else {
                    if (boundService.getProgress() == boundService.getMaxValue()) {
                        btn_multiAction.setText("Restart");

                    } else {
                        btn_multiAction.setText("Start");
                    }
                }
            }
        });

    }

    private void toggleUpdates() {
        if (boundService != null) {
            if (boundService.getProgress() == boundService.getMaxValue()) {
                boundService.resetTask();
                btn_multiAction.setText("Start");
            } else {
                if (boundService.getIsPaused()) {
                    boundService.unPausePretendingLongRunningTask();
                    viewModel.setIsProgressUpdating(true);
                } else {
                    boundService.pausePretendingLongRunningTask();
                    viewModel.setIsProgressUpdating(false);
                }

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startService();

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (viewModel.getMyBinder() != null) {
            unbindService(viewModel.getServiceConnection());
        }
    }

    private void startService() {
        Intent serviceIntent = new Intent(ServiceActivity.this, BoundService.class);
        startService(serviceIntent);

        bindService();
    }

    private void bindService() {
        Intent serviceIntent = new Intent(ServiceActivity.this, BoundService.class);
        bindService(serviceIntent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Utilities.onPauseToUnRegister(this);
    }

}