package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class FacebookAuthActivity extends LoginActivity {
    private static final String TAG = "FacebookAuthActivity";
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String currentUserId, fbName, fbMailId, deviceToken;
    private Uri fbPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(FacebookAuthActivity.this, Arrays.asList("email", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        signInWithFacebookAuthCredentials(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookAuthActivity.this, "Something Went Wrong..! :"
                                + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void signInWithFacebookAuthCredentials(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserId = mAuth.getCurrentUser().getUid();
                            fbName = mAuth.getCurrentUser().getDisplayName();
                            fbMailId = mAuth.getCurrentUser().getEmail();
                            fbPhotoUrl = mAuth.getCurrentUser().getPhotoUrl();
                            deviceToken = FirebaseInstanceId.getInstance().getToken();

                            Log.e(TAG, "onComplete: " + fbName);
                            Log.e(TAG, "onComplete: " + fbMailId);
                            Log.e(TAG, "onComplete: " + fbPhotoUrl);

                            usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists() && snapshot.hasChild(Constants.profilePic)
                                            || snapshot.hasChild(Constants.userFullName) || snapshot.hasChild(Constants.userContact)
                                            || snapshot.hasChild(Constants.userStatus)) {
                                        sendUserToMainActivity();
                                    } else {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put(Constants.authType, Constants.facebookAuth);
                                        hashMap.put(Constants.device_token, deviceToken);
                                        hashMap.put(Constants.uId, currentUserId);
                                        hashMap.put(Constants.facebookName, fbName);
                                        hashMap.put(Constants.facebookPic, String.valueOf(fbPhotoUrl));

                                        usersRef.child(currentUserId).setValue(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            sendUserToMainActivity();
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
                            Toast.makeText(FacebookAuthActivity.this, "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(FacebookAuthActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}