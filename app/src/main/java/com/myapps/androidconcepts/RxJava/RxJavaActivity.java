package com.myapps.androidconcepts.RxJava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.myapps.androidconcepts.Activities.MainActivity;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";
    private final String greeting = "Hello from RxJava & RxAndroid..!";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Toolbar rxJava_Toolbar;
    private TextView tv_RxJava;
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        rxJava_Toolbar = findViewById(R.id.rxJava_Toolbar);
        setSupportActionBar(rxJava_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_RxJava = findViewById(R.id.tv_RxJava);
        tv_RxJava.setText("");

        /*__________________________________________________________________________________________________________________*/

        myObservable = Observable.just(greeting);
        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NotNull String s) {
                Log.d(TAG, "onNext: Invoked..!");
                tv_RxJava.setText(s);
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

        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver));

        /*___________________________________________________________________________________________________________________*/

        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(@NotNull String s) {
                Log.d(TAG, "onNext: Invoked..!");
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
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
        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver2));

        /*___________________________________________________________________________________________________________________*/

        setListView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }

    private void setListView() {
        String[] listOfData = {"Apple", "Ball", "Cat", "Dog", "Elephant", "Frog", "Genius", "Helicopter", "Ice Cream", "Jam", "Kite",
                "Lemon", "Monkey", "Nimboo", "Oscar", "Parrot", "QRSTUVWXYZ"};
        ListView listView;

        Observable<String[]> observable;
        Observer<String[]> observer;
        listView = findViewById(R.id.listView);

        observable = Observable.just(listOfData);
        observer = new Observer<String[]>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull String[] strings) {
                listView.setAdapter(new ArrayAdapter<>(RxJavaActivity.this, android.R.layout.simple_list_item_1, strings));
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.rxjava_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.rxJavaOperators) {
            startActivity(new Intent(RxJavaActivity.this, RxJava_Operators_Activity.class));
        } else if (item.getItemId() == R.id.rxJava_FromArray) {
            startActivity(new Intent(RxJavaActivity.this, FromArrayDemoActivity.class));
        } else {
            Intent intent = new Intent(RxJavaActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            //finish();
        }

        return true;
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