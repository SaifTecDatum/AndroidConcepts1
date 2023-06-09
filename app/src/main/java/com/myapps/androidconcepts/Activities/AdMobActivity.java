package com.myapps.androidconcepts.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.myapps.androidconcepts.Helpers.Sample_SingleTon;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.databinding.ActivityAdMobBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdMobActivity extends AppCompatActivity {
    private static final String TAG = "AdMobActivity";
    private final String imgPath = "https://techvidvan.com/tutorials/wp-content/uploads/sites/2/2020/06/Executor-Service-in-java-tv.jpg";
    private Bitmap image = null;
    private ActivityResultLauncher<String> galleryLauncher;
    private ActivityAdMobBinding adMobBinding;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adMobBinding = ActivityAdMobBinding.inflate(getLayoutInflater());
        setContentView(adMobBinding.getRoot());

        loadInterstitialAd();

        adMobBinding.btnAdOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobAdMethod();
                /*Original ads not working bcoz of unPublished app, whereAs in LetsChat app its working bcoz of published app.*/

            }
        });


        fetchingABC_DataFromSingleTon();    //justToCheckResultInLogcat.


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adMobBinding.progressBar.setVisibility(View.GONE);
                adMobBinding.ivRandomImg.setImageDrawable(getResources().getDrawable(R.drawable.camera));
                Toast.makeText(AdMobActivity.this, "Click on Get_Image Button to upload a Pic..!", Toast.LENGTH_SHORT).show();
            }
        }, 2000);

        //galleryLauncherMethod();      //this Line To Use When Using (ActivityResultLauncher) NewProcess Of Fetching Image From Gallery.

        adMobBinding.btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //usingTwoConcepts in SingleBtnHere, either ExecutorService or ImageGalleryLauncher.


                //asyncTask Has Been Deprecated In APILevel30 & Replaced To This below ExecutorServiceProcess.
                getExecutorService();


                //startActivityForResult Has Been Deprecated & Replaced to this ActivityResultLauncher.
                //galleryLauncher.launch("image/*");

                //galleryLauncherMethod(); //this Line To Use When Using (startActivityForResult) OldProcess Of Fetching Image From Gallery.
            }
        });


        adMobBinding.btnDialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:")));
            }
        });


        adMobBinding.btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:17.4169,78.4387,13?z=9")));
            }
        });


        adMobBinding.btnOpenWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://console.firebase.google.com/project/lets-chat-f2069/authentication/users")));
            }
        });

    }

    @SuppressLint("MissingPermission")
    public void mobAdMethod() {

        MobileAds.initialize(AdMobActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        /*Original ads not working bcoz of unPublished app, whereAs in LetsChat app its working bcoz of published app.*/

        AdRequest adRequest = new AdRequest.Builder().build();
        adMobBinding.adView1.loadAd(adRequest);
        adMobBinding.adView2.loadAd(adRequest);

        if (adRequest == null) {
            adMobBinding.adView1.setVisibility(View.VISIBLE);
            adMobBinding.adView2.setVisibility(View.VISIBLE);
        }

    }

    private void loadInterstitialAd() {
        /*Sample Interstitial AdUnitId : ca-app-pub-3940256099942544/1033173712*/
        /*interstitialAd = new InterstitialAd(AdMobActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());*/

        String intrstlAdId = "ca-app-pub-3940256099942544/1033173712";
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(AdMobActivity.this, intrstlAdId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);

                        mInterstitialAd = interstitialAd;
                        Log.e(TAG, "onAdLoaded: " + mInterstitialAd.getResponseInfo());
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }


    private void fetchingABC_DataFromSingleTon() {  //justToCheckResultInLogcat.
        Sample_SingleTon sample_singleTon = Sample_SingleTon.getInstance();
        Log.e(TAG, "fetchingABC_DataFromSingleTon: FirstABCResult " + sample_singleTon.ABC);

        sample_singleTon.ABC = 543;
        Log.e(TAG, "fetchingABC_DataFromSingleTon: SecondABCResult " + sample_singleTon.ABC);
        Log.e(TAG, "fetchingABC_DataFromSingleTon: " + sample_singleTon.hashCode() + "\n");

        Sample_SingleTon sample_singleTon1 = Sample_SingleTon.getInstance();
        Log.e(TAG, "fetchingABC_DataFromSingleTon: ThirdABCResult " + sample_singleTon1.ABC);

        sample_singleTon1.ABC = 786;
        Log.e(TAG, "fetchingABC_DataFromSingleTon: FourthABCResult " + sample_singleTon1.ABC);
        Log.e(TAG, "fetchingABC_DataFromSingleTon: " + sample_singleTon1.hashCode() + "\n");

        Sample_SingleTon sample_singleTon2 = Sample_SingleTon.getInstance();
        Log.e(TAG, "fetchingABC_DataFromSingleTon: FifthABCResult " + sample_singleTon2.ABC);
        Log.e(TAG, "fetchingABC_DataFromSingleTon: " + sample_singleTon2.hashCode() + "\n");

        if (sample_singleTon == sample_singleTon1 && sample_singleTon1 == sample_singleTon2) {
            Log.e(TAG, "fetchingABC_DataFromSingleTon: Three objects point to the same memory location on the heap i.e, to the same object");
        } else {
            Log.e(TAG, "fetchingABC_DataFromSingleTon: Three objects DO NOT point to the same memory location on the heap");
        }

    }


    private void getExecutorService() {
        //inPlaceOf 'AsyncTask' method now WeHave 'ExecutorService' wch is veryEasy.
        ExecutorService eService = Executors.newSingleThreadExecutor();     //codeToRemember-1
        eService.execute(new Runnable() {                                   //codeToRemember-2
            @Override
            public void run() {

                //below RunOnUIThread ProcessWillWorkAs onPreExecuteMethod ofAsyncTask.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //adMobBinding.progressBar.setVisibility(View.VISIBLE);
                        Log.e(TAG, "run: onPreExecute");
                    }
                });


                //this TryCatch ProcessWillWorkAs doInBackground()Method ofAsyncTask.
                try {
                    URL url = new URL(imgPath);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    image = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    Log.e(TAG, "run: doInBackground : " + image);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //below RunOnUIThread ProcessWillWorkAs onPostExecuteMethod ofAsyncTask.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //adMobBinding.progressBar.setVisibility(View.GONE);
                        adMobBinding.ivRandomImg.setImageBitmap(image);
                        Log.e(TAG, "run: onPostExecute");
                    }
                });
            }
        });
    }

    private void galleryLauncherMethod() {      //inPlaceOf 'StartActivityForResult' method now WeHave 'RegisterForActivityResult'.
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        adMobBinding.ivRandomImg.setImageURI(uri);
                        adMobBinding.progressBar.setVisibility(View.GONE);
                    }
                });

        /*Intent intent = new Intent(); //popular & OldProcess to fetch the image from gallery. part1
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, ""), 101);*/
    }

    /*@Override     //popular & OldProcess to fetch the image from gallery. part2
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null) {

            Uri uri = data.getData();
            adMobBinding.ivRandomImg.setImageURI(uri);
            adMobBinding.progressBar.setVisibility(View.GONE);
        }
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

        /*if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        else {
            Log.e(TAG, "onClick: The interstitialAd wasn't loaded yet..!");
        }*/

        if (mInterstitialAd != null) {
            mInterstitialAd.show(AdMobActivity.this);
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.");
        }
    }

}