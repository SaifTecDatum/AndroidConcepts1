package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Adapters.SQLiteAdapter;
import com.myapps.androidconcepts.Database.MyHelper;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Interfaces.MyCallbacks;
import com.myapps.androidconcepts.Models.SQLiteModel;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity implements MyCallbacks {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<SQLiteModel> modelList = new ArrayList<>();
    private MyHelper myHelper;
    private SQLiteAdapter sqLiteAdapter;
    private EditText et_countryName, et_countryDescrptn, et_countryGDP;
    private AppCompatButton btn_Submit;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite);

        recyclerView = findViewById(R.id.recyclerList);
        floatingActionButton = findViewById(R.id.fab_openBtmSht);

        myHelper = new MyHelper(SQLiteActivity.this);
        RefreshListItems();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(SQLiteActivity.this, R.style.BottomSheetStyle);
                bottomSheetDialog.setContentView(R.layout.bottomsheet_dialog_sqlite);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                et_countryName = bottomSheetDialog.findViewById(R.id.et_countryName);
                et_countryDescrptn = bottomSheetDialog.findViewById(R.id.et_countryDescrptn);
                et_countryGDP = bottomSheetDialog.findViewById(R.id.et_countryGDP);
                btn_Submit = bottomSheetDialog.findViewById(R.id.btn_Submit);

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = et_countryName.getText().toString().trim();
                        String descrptn = et_countryDescrptn.getText().toString().trim();
                        String gdp = et_countryGDP.getText().toString().trim();

                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(SQLiteActivity.this, "Country Name Required..", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(descrptn)) {
                            Toast.makeText(SQLiteActivity.this, "Short Info Required..", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(gdp)) {
                            Toast.makeText(SQLiteActivity.this, "Country GDP Required..", Toast.LENGTH_SHORT).show();
                        } else {
                            myHelper.insertData(name, descrptn, Double.parseDouble(gdp));
                            RefreshListItems();
                            Toast.makeText(SQLiteActivity.this, name + " data added Successfully..",
                                    Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });

    }

    private void RefreshListItems() {
        modelList = myHelper.getAllData();      //needToRememberThisLine.
        sqLiteAdapter = new SQLiteAdapter(SQLiteActivity.this, modelList, this);
        recyclerView.setAdapter(sqLiteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SQLiteActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(View.FOCUS_DOWN);
    }

    @Override
    public void deleteItems(int id) {
        long result = myHelper.deleteItems(id);
        if (result == 0 || result == 1) {                   //needToRememberTheseTwoMethodCodes.
            RefreshListItems();
        }
    }

    @Override
    public void updateItems(int id, String name, String description, double gdp) {
        long result = myHelper.updateItems(id, name, description, gdp);
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