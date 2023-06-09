package com.myapps.androidconcepts.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GoogleLoginActivity extends LoginActivity {                        //codeToRemember: 1         easy
    private static final String TAG = "GoogleLoginActivity";
    private GoogleSignInClient mGoogleSignInClient;                             //codeToRemember: 2         easy
    private final int rqstCode_SignIn = 101;                                    //codeToRemember: 3         easy
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private ProgressDialog progressDialog;
    private String currentUserId, googleName, deviceToken;
    private Uri googlePhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(GoogleLoginActivity.this);
        progressDialog.setTitle("Google SignIn");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIcon(R.drawable.google_logo);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()                                                 //codeToRemember: 4     easy
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);        //codeToRemember: 5     easy

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();            //codeToRemember: 6     easy
        startActivityForResult(signInIntent, rqstCode_SignIn);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == rqstCode_SignIn) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);           //codeToRemember: 7
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);                       //codeToRemember: 8
                signInWithGoogleAuthCredentials(account.getIdToken());
                googleName = account.getDisplayName();
                googlePhotoUrl = account.getPhotoUrl();
            } catch (ApiException e) {
                progressDialog.dismiss();
                Toast.makeText(GoogleLoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void signInWithGoogleAuthCredentials(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);        //codeToRemember: 9
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserId = mAuth.getCurrentUser().getUid();
                            deviceToken = FirebaseInstanceId.getInstance().getToken();
                            Log.e(TAG, "outside: deviceToken: " + deviceToken);
                            /*FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<String> task) {
                                            if (task.isSuccessful()) {
                                                deviceToken = task.getResult();
                                                Log.e(TAG, "GoogleLoginActivity's deviceToken: " + deviceToken);
                                            }
                                        }
                                    });*/

                            userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists() && snapshot.hasChild(Constants.profilePic)
                                            || snapshot.hasChild(Constants.userFullName) || snapshot.hasChild(Constants.userContact)
                                            || snapshot.hasChild(Constants.userStatus)) {
                                        sendUserToMainActivity();
                                    } else {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put(Constants.authType, Constants.googleAuth);
                                        hashMap.put(Constants.device_token, deviceToken);
                                        hashMap.put(Constants.uId, currentUserId);
                                        hashMap.put(Constants.googleName, googleName);
                                        hashMap.put(Constants.googlePic, String.valueOf(googlePhotoUrl));

                                        userRef.child(currentUserId).setValue(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            sendUserToMainActivity();
                                                            progressDialog.dismiss();
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
                            Toast.makeText(GoogleLoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(GoogleLoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}