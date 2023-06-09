package com.myapps.androidconcepts.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.myapps.androidconcepts.BroadcastReceivers.MyReceiver;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

public class BroadcastRcvrsActivity extends AppCompatActivity {
    private static final String TAG = "BroadcastRcvrsActivity";
    TextView tv_batteryPercentage, tv_flightModeResult;
    AppCompatButton btn_customMsgRcvr;
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_rcvrs);

        tv_batteryPercentage = findViewById(R.id.tv_batteryPercentage);
        tv_batteryPercentage.setText("");
        tv_flightModeResult = findViewById(R.id.tv_flightModeResult);
        btn_customMsgRcvr = findViewById(R.id.btn_customMsgRcvr);

        if (ContextCompat.checkSelfPermission(BroadcastRcvrsActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BroadcastRcvrsActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }

        btn_customMsgRcvr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Constants.ACTION_CUSTOM_BRDCSTRCVR);
                sendBroadcast(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions,
                                           @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0) {
                if (permissions[0].equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Now you will get Broadcast Notifications for Calls..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "You have Rejected Permission..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);

        intentFilter.addAction(Constants.ACTION_CUSTOM_BRDCSTRCVR);

        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        myReceiver = new MyReceiver(tv_batteryPercentage, tv_flightModeResult);

        this.registerReceiver(myReceiver, intentFilter);

        /*HandlerThread handlerThread = new HandlerThread("BroadcastRegisterThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();      //ifHaveMany BrdcstRcvrs inOnReceive()MethodWchWasRunningInMainUIThread,
        Handler handler = new Handler(looper);*/        //thatMayCauseErrors.So,ToRunInTheSeparateThreadWeUseThisCode.
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.unregisterReceiver(myReceiver);
    }

    /*@Override         //to work with this activity we need to keep in comment for noInternet code.
    protected void onResume() {
        super.onResume();
        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilities.onPauseToUnRegister(this);
    }*/
}