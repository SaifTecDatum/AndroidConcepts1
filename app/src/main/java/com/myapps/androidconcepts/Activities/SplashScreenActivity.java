package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class SplashScreenActivity extends AppCompatActivity {
    /*private AppCompatImageView iv_SplhScrn_appLogo;
    private TextView tv_Heading;
    private LinearLayout linLay_NoInternet;*/
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*iv_SplhScrn_appLogo = findViewById(R.id.iv_SplhScrn_appLogo);
        tv_Heading = findViewById(R.id.tv_Heading);
        linLay_NoInternet = findViewById(R.id.linLay_NoInternet);*/

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        validateUser();
    }

    public void validateUser() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*

                //Old process for NoInternetConnection, inThisWeAreOnlyCheckingInSplashScreenActivityButNotInRemainingActivities.
                //New process handling in all activities.


                if (!isConnected()) {
                    linLay_NoInternet.setVisibility(View.VISIBLE);
                    iv_SplhScrn_appLogo.setVisibility(View.GONE);
                    tv_Heading.setVisibility(View.GONE);
                    Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.no_internet_access_please_check_your_internet_connection), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000 * 7);
                } else if (mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.welcome_to_android_concepts), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    verifyUserExistence();

                }*/


                if (mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.welcome_to_android_concepts), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    verifyUserExistence();
                }

            }
        }, 2000);
    }

    private void verifyUserExistence() {
        currentUserId = mAuth.getCurrentUser().getUid();
        rootRef.child(Constants.users).child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {        //hereExistMeansItChecksWhetherTheUserLogoutOrNot,ifNotThenItMeansExists.
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.welcome_back), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /*@SuppressLint("MissingPermission")
    private boolean isConnected() {     //NoInternetConnectionMethod.
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }*/

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