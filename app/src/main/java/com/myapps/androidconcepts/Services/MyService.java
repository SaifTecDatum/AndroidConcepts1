package com.myapps.androidconcepts.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private MediaPlayer player;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {      //thisMethodWillHitWhen"StartService_Intent"Called.
        player = MediaPlayer.create(MyService.this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setVolume(0.8f, 0.8f);  //0.0f to 1.0f we need to give
        player.setLooping(true);
        player.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {       //thisMethodWillHitWhen"StopService_Intent"Called.
        super.onDestroy();

        player.stop();
    }
}