package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Adapters.RoomDB_Adapter;
import com.myapps.androidconcepts.Database.AppDatabase;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Interfaces.UserDao;
import com.myapps.androidconcepts.Models.Users;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class RoomDatabaseActivity extends AppCompatActivity {
    private RecyclerView roomDB_recyclerView;
    private FloatingActionButton fab_roomDb_BtmSht;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tv_result;
    private EditText et_recordId, et_firstName, et_lastName;
    private AppCompatButton btn_SaveToRoomDB;
    private String recordId, firstName, lastName;
    private RoomDB_Adapter adapter;
    private List<Users> usersList = new ArrayList<>();

    private void InitializeFields() {
        roomDB_recyclerView = findViewById(R.id.roomDB_recyclerView);
        fab_roomDb_BtmSht = findViewById(R.id.fab_roomDb_BtmSht);

        bottomSheetDialog = new BottomSheetDialog(RoomDatabaseActivity.this, R.style.BottomSheetStyle);
        bottomSheetDialog.setContentView(R.layout.btmsht_roomdb_sqlite);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        tv_result = bottomSheetDialog.findViewById(R.id.tv_result);
        tv_result.setText("");
        et_recordId = bottomSheetDialog.findViewById(R.id.et_recordId);
        et_firstName = bottomSheetDialog.findViewById(R.id.et_firstName);
        et_lastName = bottomSheetDialog.findViewById(R.id.et_lastName);
        btn_SaveToRoomDB = bottomSheetDialog.findViewById(R.id.btn_SaveToRoomDB);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_database);

        InitializeFields();

        getRoomData();

        fab_roomDb_BtmSht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_result.setText("");

                btn_SaveToRoomDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordId = et_recordId.getText().toString().trim();
                        firstName = et_firstName.getText().toString().trim();
                        lastName = et_lastName.getText().toString().trim();

                        if (TextUtils.isEmpty(recordId)) {
                            et_recordId.setError("S.no Required..!");
                        } else if (TextUtils.isEmpty(firstName)) {
                            et_firstName.setError("First Name Required..!");
                        } else if (TextUtils.isEmpty(lastName)) {
                            et_lastName.setError("Last Name Required..!");
                        } else {
                            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                                    Constants.room_db_name).allowMainThreadQueries().build();

                            UserDao userDao = db.userDao();
                            Boolean check = userDao.is_exists(Integer.parseInt(recordId));

                            if (!check) {
                                userDao.insertrecord(new Users(Integer.parseInt(recordId),
                                        firstName, lastName));

                                et_recordId.setText("");
                                et_firstName.setText("");
                                et_lastName.setText("");
                                tv_result.setText("Inserted Successfully..!");
                                getRoomData();
                                adapter.notifyDataSetChanged();
                            } else {
                                tv_result.setText("S.no: " + recordId + " Already Exists..!");
                                et_recordId.setText("");
                                et_firstName.setText("");
                                et_lastName.setText("");
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bottomSheetDialog.dismiss();
                                }
                            }, 2000);
                        }
                    }
                });

                bottomSheetDialog.show();
            }
        });
    }

    private void getRoomData() {
        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                Constants.room_db_name).allowMainThreadQueries().build();

        UserDao userDao = database.userDao();
        usersList = userDao.getAllUsers();

        roomDB_recyclerView.setLayoutManager(new LinearLayoutManager(RoomDatabaseActivity.this));
        roomDB_recyclerView.setHasFixedSize(true);
        roomDB_recyclerView.addItemDecoration(new DividerItemDecoration(RoomDatabaseActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new RoomDB_Adapter(RoomDatabaseActivity.this, usersList);
        roomDB_recyclerView.setAdapter(adapter);
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