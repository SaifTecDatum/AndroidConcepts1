package com.myapps.androidconcepts.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxJava_Operators_Activity extends AppCompatActivity {
    private static final String TAG = "RxJava_Operators";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView tv_RxJava_Operators;
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operators);

        tv_RxJava_Operators = findViewById(R.id.tv_RxJava_Operators);
        tv_RxJava_Operators.setText("");

        myObservable = Observable.just("Hello A", "Hello B", "Hello C");

        compositeDisposable.add(myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }

    private DisposableObserver<String> getObserver() {
        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NotNull String s) {
                tv_RxJava_Operators.append(s + " ");
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