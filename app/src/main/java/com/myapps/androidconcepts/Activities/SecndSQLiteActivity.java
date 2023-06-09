package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Adapters.SecndSqliteAdapter;
import com.myapps.androidconcepts.Database.MySecndHelper;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Interfaces.MyMviCallbacks;
import com.myapps.androidconcepts.Models.SecndSqliteModel;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class SecndSQLiteActivity extends AppCompatActivity implements MyMviCallbacks {
    private static final String TAG = "SecndSQLiteActivity";
    private RecyclerView mvi_recyclerView;
    private FloatingActionButton fab_openMviBtmSht;
    private EditText et_movieName, et_movieDetails, et_movieIMDB;
    private AppCompatButton btn_Submit;
    private SecndSqliteAdapter secndSqliteAdapter;
    private MySecndHelper mySecndHelper;
    private List<SecndSqliteModel> secndDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secnd_s_q_lite);

        mvi_recyclerView = findViewById(R.id.mvi_recyclerList);
        fab_openMviBtmSht = findViewById(R.id.fab_openMviBtmSht);

        mySecndHelper = new MySecndHelper(SecndSQLiteActivity.this);
        RefreshListItems();

        fab_openMviBtmSht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SecndSQLiteActivity.this, R.style.BottomSheetStyle);
                bottomSheetDialog.setContentView(R.layout.btmsheet_dialog1_sqlite);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                //bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))
                //HereWeDon'tHaveToMentionLikeAboveLineForBckgrndTransparentInsteadOfThatWeHaveToFollow R.style.BtmShtStyle process.
                //AndAlsoMustToTake setContentView(); InsteadOf getLayoutInflater(); forAttachingLayout.

                et_movieName = bottomSheetDialog.findViewById(R.id.et_movieName);
                et_movieDetails = bottomSheetDialog.findViewById(R.id.et_movieDetails);
                et_movieIMDB = bottomSheetDialog.findViewById(R.id.et_movieIMDB);
                btn_Submit = bottomSheetDialog.findViewById(R.id.btn_MviSubmit);

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(et_movieName.getText().toString().trim())) {
                            et_movieName.setError("Movie name Didn't mentioned..!");
                        } else if (TextUtils.isEmpty(et_movieDetails.getText().toString().trim())) {
                            et_movieDetails.setError("Movie Details Didn't mentioned..!");
                        } else if (TextUtils.isEmpty(et_movieIMDB.getText().toString().trim())) {
                            et_movieIMDB.setError("Movie IMDB Didn't mentioned..!");
                        } else {
                            long result = mySecndHelper.InsertData(et_movieName.getText().toString().trim(),
                                    et_movieDetails.getText().toString().trim(),
                                    Double.parseDouble(et_movieIMDB.getText().toString().trim()));

                            Log.e(TAG, "onClick: onInsertDataResult: " + result);
                            /* Here result value -1 is returned if error occurred. InsertData(...) returns the row id of the
                            new inserted record. For that need to keep _id field in your db. */

                            RefreshListItems();

                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });

    }

    private void RefreshListItems() {
        secndDataList = mySecndHelper.getAllData();
        secndSqliteAdapter = new SecndSqliteAdapter(secndDataList, SecndSQLiteActivity.this, this);
        mvi_recyclerView.setAdapter(secndSqliteAdapter);
        mvi_recyclerView.setLayoutManager(new LinearLayoutManager(SecndSQLiteActivity.this));
        mvi_recyclerView.setHasFixedSize(true);
        mvi_recyclerView.addItemDecoration(new DividerItemDecoration(SecndSQLiteActivity.this, DividerItemDecoration.VERTICAL));
        // mvi_recyclerView.smoothScrollToPosition(View.FOCUS_DOWN);
    }


    @Override
    public void deleteMovies(int id) {
        long result = mySecndHelper.deteleItems(id);

        Log.e(TAG, "deleteMoviesResult: " + result);

        if (result == 0 || result == 1) {
            RefreshListItems();
        }
    }

    //1st process starts from adapter, 2nd(here by interface callbacks it'll popup) & 3rd in its sqlite_helperClass.
    //4th. Again from sqlite_helperClass returns to activity with result value,  5th. In activity by comparing
    //result value we refresh the list items by help of callbacks,  6th. shows in recyclerAdapter to the users.
    @Override
    public void updateMovies(int id, String movie, String movieDetails, double imdb) {
        long result = mySecndHelper.UpdateItems(id, movie, movieDetails, imdb);

        Log.e(TAG, "updateMoviesResult: " + result);

        if (result == 0 || result == 1) {
            RefreshListItems();
        }
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