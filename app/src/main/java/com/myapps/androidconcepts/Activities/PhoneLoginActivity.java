package com.myapps.androidconcepts.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
    private static final String TAG = "PhoneLoginActivity";
    private EditText et_phoneNumberInput, et_verificationCode;
    private TextView tv_mobileNo, tv_OTP;
    private AppCompatButton btn_sendVerfctnCode, btn_Verification;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private TextInputLayout TIL_verificationCode;
    private ProgressDialog progressDialog;
    private String mVerificationId, currentUserId, phoneNumber, number, deviceToken;
    private LinearLayout LinLay_phoneNoInput;
    private CountryCodePicker countryCodePicker;

    private void InitializeFields() {
        LinLay_phoneNoInput = findViewById(R.id.LinLay_phoneNoInput);
        TIL_verificationCode = findViewById(R.id.TIL_verificationCode);
        et_phoneNumberInput = findViewById(R.id.et_phoneNumberInput);
        et_verificationCode = findViewById(R.id.et_verificationCode);
        btn_sendVerfctnCode = findViewById(R.id.btn_sendVerfctnCode);
        btn_Verification = findViewById(R.id.btn_Verification);
        tv_mobileNo = findViewById(R.id.tv_mobileNo);
        tv_OTP = findViewById(R.id.tv_OTP);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        progressDialog = new ProgressDialog(PhoneLoginActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        InitializeFields();

        countryCodePicker.registerCarrierNumberEditText(et_phoneNumberInput);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);

        btn_sendVerfctnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = et_phoneNumberInput.getText().toString().trim();

                number = countryCodePicker.getFullNumberWithPlus();

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(PhoneLoginActivity.this, "Please Enter your Phone Number..", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Phone Verification");
                    progressDialog.setMessage("Please wait, while we are Authenticating your Phone.");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progressDialog in Dialog.
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    /*PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(number)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(PhoneLoginActivity.this)
                            .setCallbacks(callbacks)
                            .build();               //latest way to verify phoneNumber.

                    PhoneAuthProvider.verifyPhoneNumber(options);*/

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            number,
                            90,
                            TimeUnit.SECONDS,
                            PhoneLoginActivity.this,
                            callbacks);
                }
            }
        });

        btn_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this onClick not at all useful bcoz everything is happening from onVerificationCompleted callback itself.

                String verificationCode = et_verificationCode.getText().toString().trim();
                if (TextUtils.isEmpty(verificationCode)) {
                    Toast.makeText(PhoneLoginActivity.this, "Please Enter Verification Code..", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Verification Code");
                    progressDialog.setMessage("Please wait, while we are verifying verification Code.");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredentials(credential);
                }
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();

                Log.e("VerfctnFailed", e.getLocalizedMessage());

                LinLay_phoneNoInput.setVisibility(View.VISIBLE);
                btn_sendVerfctnCode.setVisibility(View.VISIBLE);
                tv_mobileNo.setVisibility(View.VISIBLE);
                TIL_verificationCode.setVisibility(View.INVISIBLE);
                btn_Verification.setVisibility(View.INVISIBLE);
                tv_OTP.setVisibility(View.INVISIBLE);

                Toast.makeText(PhoneLoginActivity.this, "Invalid Phone Number, Please Enter Correct Phone Number with your Country Code..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                progressDialog.dismiss();
                Toast.makeText(PhoneLoginActivity.this, "Code has been Sent, Please Check and Verify..", Toast.LENGTH_SHORT).show();

                tv_mobileNo.setVisibility(View.INVISIBLE);
                LinLay_phoneNoInput.setVisibility(View.INVISIBLE);
                btn_sendVerfctnCode.setVisibility(View.INVISIBLE);

                tv_OTP.setVisibility(View.VISIBLE);
                TIL_verificationCode.setVisibility(View.VISIBLE);
                btn_Verification.setVisibility(View.VISIBLE);
            }
        };

    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                    Log.e(TAG, "PhoneLoginActivity's deviceToken: " + deviceToken);


                    usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists() && snapshot.hasChild(Constants.profilePic)
                                    || snapshot.hasChild(Constants.userFullName) || snapshot.hasChild(Constants.userContact)
                                    || snapshot.hasChild(Constants.userStatus)) {
                                sendUserToMainActivity();
                            } else {
                                HashMap<String, String> phoneMap = new HashMap<>();
                                phoneMap.put(Constants.authType, Constants.phoneAuth);
                                phoneMap.put(Constants.device_token, deviceToken);
                                phoneMap.put(Constants.uId, currentUserId);
                                phoneMap.put(Constants.userContact, phoneNumber);

                                usersRef.child(currentUserId).setValue(phoneMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    sendUserToMainActivity();
                                                    Toast.makeText(PhoneLoginActivity.this, "Congrats, LogIn Success..", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(PhoneLoginActivity.this, "LogIn Failed.." + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToMainActivity() {
        Intent phoneIntent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        phoneIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(phoneIntent);
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