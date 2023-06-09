package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Database.PreferenceConnectors;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class SharedPrefrncActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton, fab_preferenceDelete;
    private BottomSheetDialog bottomSheetDialog;
    private EditText et_Name, et_Skill;
    private AppCompatButton btn_Submit;
    private TextView tv_playerName, tv_playerSkill;
    private String retrievePlayerName, retrievePlayerSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefrnc);

        tv_playerName = findViewById(R.id.tv_playerName);
        tv_playerSkill = findViewById(R.id.tv_playerSkill);
        floatingActionButton = findViewById(R.id.fab_preference);
        fab_preferenceDelete = findViewById(R.id.fab_preferenceDelete);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(SharedPrefrncActivity.this, R.style.BottomSheetStyle);
                bottomSheetDialog.setContentView(R.layout.btmsht_sharedpref);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                et_Name = bottomSheetDialog.findViewById(R.id.et_Name);
                et_Skill = bottomSheetDialog.findViewById(R.id.et_Skill);
                btn_Submit = bottomSheetDialog.findViewById(R.id.btn_Submit);

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(et_Name.getText().toString().trim())) {
                            et_Name.setError("Player Name Required..!");
                        } else if (TextUtils.isEmpty(et_Skill.getText().toString().trim())) {
                            et_Skill.setError("Player Skill Required..!");
                        }
                        else {
                            PreferenceConnectors.writeString(SharedPrefrncActivity.this,
                                    Constants.playerName, et_Name.getText().toString().trim());

                            PreferenceConnectors.writeString(SharedPrefrncActivity.this,
                                    Constants.playerSkill, et_Skill.getText().toString().trim());

                            startActivity(new Intent(SharedPrefrncActivity.this, SharedPrefrncActivity.class));
                            finish();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.show();
            }
        });



        retrievePlayerName = PreferenceConnectors.readString(SharedPrefrncActivity.this,
                Constants.playerName, "");
        retrievePlayerSkill = PreferenceConnectors.readString(SharedPrefrncActivity.this,
                Constants.playerSkill, "");

        if (retrievePlayerName != null && retrievePlayerSkill.length() > 0) {
            tv_playerName.setText("Player Name: " + retrievePlayerName);
            tv_playerSkill.setText("Player Skill: " + retrievePlayerSkill);
            Toast.makeText(SharedPrefrncActivity.this, retrievePlayerName + " is your new Player.", Toast.LENGTH_SHORT).show();
        }



        fab_preferenceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (retrievePlayerName != null && retrievePlayerName.length() > 0) {
                    PreferenceConnectors.remove(SharedPrefrncActivity.this, Constants.playerName);
                    PreferenceConnectors.remove(SharedPrefrncActivity.this, Constants.playerSkill);

                    tv_playerName.setText("Player Name: ");
                    tv_playerSkill.setText("Player Skill: ");
                }
                else {
                    Toast.makeText(SharedPrefrncActivity.this, "No Data exists to delete..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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