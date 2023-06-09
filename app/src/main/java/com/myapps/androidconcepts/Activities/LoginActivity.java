package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText et_loginUsername, et_loginPassword, et_resetDialogEmail;
    private TextView tv_forgetPswrd, tv_goToRegistration;
    private AppCompatButton btn_login, btn_dialogYes, btn_dialogCancel;
    private CircleImageView civ_phoneLogin, civ_GoogleLogin, civ_FacebookLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String currentUserId, deviceToken;

    private void InitializeFields() {
        et_loginUsername = findViewById(R.id.et_loginUsername);
        et_loginPassword = findViewById(R.id.et_loginPassword);
        tv_forgetPswrd = findViewById(R.id.tv_forgetPswrd);
        tv_goToRegistration = findViewById(R.id.tv_goToRegister);
        btn_login = findViewById(R.id.btn_login);
        civ_phoneLogin = findViewById(R.id.civ_phoneLogin);
        civ_GoogleLogin = findViewById(R.id.civ_GoogleLogin);
        civ_FacebookLogin = findViewById(R.id.civ_FacebookLogin);
    }

    private void SendUserToRegistrationActivity() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);

        tv_goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegistrationActivity();
                Toast.makeText(LoginActivity.this, "Create an Account..", Toast.LENGTH_SHORT).show();
            }
        });

        civ_phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PhoneLoginActivity.class));
                Toast.makeText(LoginActivity.this, "Please Enter your Mobile Number..", Toast.LENGTH_SHORT).show();
            }
        });

        civ_GoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, GoogleLoginActivity.class));
            }
        });

        civ_FacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent facebookAuthIntent = new Intent(LoginActivity.this, FacebookAuthActivity.class);
                facebookAuthIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(facebookAuthIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_loginUsername.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Please enter your Email Address..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_loginPassword.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Please enter your Password..", Toast.LENGTH_SHORT).show();
                } else if (et_loginPassword.getText().toString().trim().length() < 6) {
                    et_loginPassword.setError("Password must be above 6 Chars..");
                } else {
                    mAuth.signInWithEmailAndPassword(et_loginUsername.getText().toString().trim(),
                            et_loginPassword.getText().toString().trim())
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
                                        Log.e(TAG, "LoginActivity's deviceToken: " + deviceToken);

                                        HashMap<String, Object> loginMap = new HashMap<>();
                                        loginMap.put(Constants.uId, currentUserId);
                                        loginMap.put(Constants.device_token, deviceToken);

                                        usersRef.child(currentUserId).updateChildren(loginMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            SendUserToMainActivity();
                                                            Toast.makeText(LoginActivity.this, "You have Successfully LoggedIn, Welcome to My Concepts...", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login Failed.. " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        tv_forgetPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View view = getLayoutInflater().inflate(R.layout.frgtpswd_dialog, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(view);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                et_resetDialogEmail = view.findViewById(R.id.et_resetDialogEmail);
                btn_dialogYes = view.findViewById(R.id.btn_dialogYes);
                btn_dialogCancel = view.findViewById(R.id.btn_dialogCancel);

                btn_dialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(et_resetDialogEmail.getText().toString().trim())) {
                            Toast.makeText(LoginActivity.this, "Please enter a Valid Email Id..", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(et_resetDialogEmail.getText().toString().trim())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginActivity.this, "We have Sent you a mail Please Check your Inbox.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "ErrorInFrgtPswd: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            alertDialog.dismiss();
                        }
                    }
                });

                btn_dialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
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