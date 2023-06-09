package com.myapps.androidconcepts.Z_BoundServiceWithMVVM;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BoundService extends Service {
    private static final String TAG = "BoundService";
    private final IBinder mBinder = new MyBinder();
    private Handler mHandler;
    private int mProgress, mMaxValue;
    private Boolean mIsPaused;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mProgress = 0;
        mMaxValue = 5000;
        mIsPaused = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Boolean getIsPaused() {
        return mIsPaused;
    }

    public int getProgress() {
        return mProgress;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void pausePretendingLongRunningTask() {
        mIsPaused = true;
    }

    public void unPausePretendingLongRunningTask() {
        mIsPaused = false;
        startPretendingLongRunningTask();
    }

    public void startPretendingLongRunningTask() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mProgress >= mMaxValue || mIsPaused) {
                    Log.d(TAG, "run: removing Callbacks.");
                    mHandler.removeCallbacks(this);
                    pausePretendingLongRunningTask();
                } else {
                    Log.d(TAG, "run: progress: " + mProgress);
                    mProgress += 100;
                    mHandler.postDelayed(this, 100);
                }
            }
        };
        mHandler.postDelayed(runnable, 100);
    }

    public void resetTask() {
        mProgress = 0;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved: called.");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
    }

    public class MyBinder extends Binder {
        public BoundService getService() {

            return BoundService.this;
        }
    }
}
