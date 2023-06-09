package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    private EditText et_rgstrFullName, et_rgstrMobileNo, et_rgstrEmail, et_rgstrPassword;
    private AppCompatButton btn_register;
    private TextView tv_goToLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String currentUserId, deviceToken;

    public void InitializeFields() {
        et_rgstrFullName = findViewById(R.id.et_rgstrFullName);
        et_rgstrMobileNo = findViewById(R.id.et_rgstrMobileNo);
        et_rgstrEmail = findViewById(R.id.et_rgstrEmail);
        et_rgstrPassword = findViewById(R.id.et_rgstrPassword);
        btn_register = findViewById(R.id.btn_register);
        tv_goToLogin = findViewById(R.id.tv_goToLogin);
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToLoginActivity() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);

        tv_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
                Toast.makeText(RegistrationActivity.this, "Please Login Here..", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_rgstrFullName.getText().toString().trim())) {
                    Toast.makeText(RegistrationActivity.this, "Please enter FullName..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_rgstrMobileNo.getText().toString().trim())) {
                    Toast.makeText(RegistrationActivity.this, "Please enter Mobile number..", Toast.LENGTH_SHORT).show();
                } else if (et_rgstrMobileNo.getText().toString().trim().length() < 10) {
                    et_rgstrMobileNo.setError("Number mismatch, must be at least 10 digits..");
                } else if (TextUtils.isEmpty(et_rgstrEmail.getText().toString().trim())) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your Email address..", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(et_rgstrEmail.getText().toString().trim()).matches()) {
                    et_rgstrEmail.setError("Please enter valid email address..");
                } else if (TextUtils.isEmpty(et_rgstrPassword.getText().toString().trim())) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your Password..", Toast.LENGTH_SHORT).show();
                } else if (et_rgstrPassword.getText().toString().trim().length() < 6) {
                    et_rgstrPassword.setError("Password must be above 6 Chars..");
                } else {
                    mAuth.createUserWithEmailAndPassword(et_rgstrEmail.getText().toString().trim(),
                            et_rgstrPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        currentUserId = mAuth.getCurrentUser().getUid();
                                        deviceToken = FirebaseInstanceId.getInstance().getToken();
                                        /*FirebaseMessaging.getInstance().getToken()
                                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<String> task) {
                                                if (task.isSuccessful()) {
                                                    deviceToken = task.getResult();
                                                }
                                            }
                                        });*/
                                        Log.e(TAG, "RegistrationActivity's deviceToken: " + deviceToken);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put(Constants.authType, Constants.nrmlAuth);
                                        hashMap.put(Constants.device_token, deviceToken);
                                        hashMap.put(Constants.uId, currentUserId);
                                        hashMap.put(Constants.userFullName, et_rgstrFullName.getText().toString().trim());
                                        hashMap.put(Constants.userContact, et_rgstrMobileNo.getText().toString().trim());

                                        usersRef.child(currentUserId).setValue(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            sendUserToMainActivity();
                                                            Toast.makeText(RegistrationActivity.this, "Congrats, you have Successfully Created an Account..", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Registration Failed..! " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                }
                            });
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