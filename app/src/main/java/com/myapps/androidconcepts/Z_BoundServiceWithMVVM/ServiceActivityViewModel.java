package com.myapps.androidconcepts.Z_BoundServiceWithMVVM;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServiceActivityViewModel extends ViewModel {
    private static final String TAG = "ServiceActivityViewMode";

    private final MutableLiveData<Boolean> mIsProgressUpdating = new MutableLiveData<>();
    private final MutableLiveData<BoundService.MyBinder> mBinder = new MutableLiveData<>();

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: connected to service");
            // We've bound to BoundService, cast the IBinder and get MyBinder instance
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            mBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: disconnected from service.");
            mBinder.postValue(null);
        }
    };

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public LiveData<BoundService.MyBinder> getMyBinder() {
        return mBinder;
    }

    public LiveData<Boolean> getIsProgressUpdating() {
        return mIsProgressUpdating;
    }

    public void setIsProgressUpdating(Boolean isUpdating) {
        mIsProgressUpdating.postValue(isUpdating);
    }

}
