package com.myapps.androidconcepts.RxJava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FromArrayDemoActivity extends AppCompatActivity {
    private static final String TAG = "FromArrayDemoActivity";
    private final Integer[] nums = {1, 2, 3, 4, 5, 6};
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Observable<Integer> myObservable;
    private DisposableObserver<Integer> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array_demo);

        myObservable = Observable.range(1, 20);     //rangeOperator
        //myObservable = Observable.fromArray(nums);            //fromArrayOperator

        compositeDisposable.add(myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }

    private DisposableObserver<Integer> getObserver() {
        myObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NotNull Integer s) {
                Log.d(TAG, "onNext: Invoked..!" + s);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.d(TAG, "onError: Invoked..!");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Invoked..!");
            }
        };

        return myObserver;
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