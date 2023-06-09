package com.myapps.androidconcepts.Z_MVP_DesignPattern;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class MVP_Activity extends AppCompatActivity implements MVP_Contract.View {
    MVP_Contract.Presenter presenter;
    private EditText et_email, et_password;
    private AppCompatButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        presenter = new MVP_Presenter(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    onFailure("Fields Required..!");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    onFailure("Invalid Email address..!");
                }
                else if (password.length() < 6) {
                    onFailure("Password Should be at least 6 Chars..!");
                }
                else {
                    presenter.doLogin(email, password);
                }
            }
        });

    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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