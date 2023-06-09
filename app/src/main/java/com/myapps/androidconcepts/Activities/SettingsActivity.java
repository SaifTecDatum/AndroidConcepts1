package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private CircleImageView civ_profilePic, civ_camera;
    private EditText et_fullName, et_userContact, et_userStatus;
    private AppCompatButton btn_submitSettings;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference profilePicStorageRef;

    private String currentUserId, retrieveProfilePic, downloadUrl, deviceToken, authType, retrieveGoogleName,
            retrieveGooglePic, retrieveFacebookPic, retrieveFacebookName, userName, userContact, userStatus;
    private NestedScrollView mNestedScrollView;
    private Uri resultUri;

    public void initializeFields() {
        civ_camera = findViewById(R.id.civ_camera);
        civ_profilePic = findViewById(R.id.civ_profilePic);
        et_fullName = findViewById(R.id.et_fullName);
        et_userContact = findViewById(R.id.et_userContact);
        et_userStatus = findViewById(R.id.et_userStatus);
        btn_submitSettings = findViewById(R.id.btn_submitSettings);
        mNestedScrollView = findViewById(R.id.nestedScrollView);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        profilePicStorageRef = FirebaseStorage.getInstance().getReference().child(Constants.profilePic);
        currentUserId = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeFields();

        btn_submitSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = et_fullName.getText().toString().trim();
                userContact = et_userContact.getText().toString().trim();
                userStatus = et_userStatus.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    Snackbar snackbar = Snackbar.make(mNestedScrollView, "Please Enter your Full Name..", Snackbar.LENGTH_SHORT);
                    snackbar.setTextColor(Color.BLACK).setBackgroundTint(getResources().getColor(R.color.skyBlueColor)).show();
                } else if (TextUtils.isEmpty(userContact)) {
                    Snackbar snackbar = Snackbar.make(mNestedScrollView, "Please Enter your Mobile Number..", Snackbar.LENGTH_SHORT);
                    snackbar.setTextColor(Color.BLACK).setBackgroundTint(getResources().getColor(R.color.skyBlueColor)).show();
                } else if (userContact.length() < 10) {
                    et_userContact.setError("Number mismatch, must be at least 10 digits..");
                } else if (TextUtils.isEmpty(userStatus)) {
                    Snackbar snackbar = Snackbar.make(mNestedScrollView, "Please enter something about you..", Snackbar.LENGTH_SHORT);
                    snackbar.setTextColor(Color.BLACK).setBackgroundTint(getResources().getColor(R.color.skyBlueColor)).show();
                } else {
                    userRef.child(currentUserId).child(Constants.authType).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() && snapshot.getValue().equals(Constants.nrmlAuth)) {
                                authType = Constants.nrmlAuth;
                                dataEntryHashMap();
                            } else if (snapshot.exists() && snapshot.getValue().equals(Constants.phoneAuth)) {
                                authType = Constants.phoneAuth;
                                dataEntryHashMap();
                            } else if (snapshot.exists() && snapshot.getValue().equals(Constants.googleAuth)) {
                                authType = Constants.googleAuth;
                                dataEntryHashMap();
                            } else if (snapshot.exists() && snapshot.getValue().equals(Constants.facebookAuth)) {
                                authType = Constants.facebookAuth;
                                dataEntryHashMap();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });

        civ_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(SettingsActivity.this);
            }
        });

        RetrieveUserInfo();
    }

    public void dataEntryHashMap() {

        /*FirebaseMessaging.getInstance().getToken()    //we'reNotGettingDeviceTokenValueOutsideOfThisMethod.needToFindOut.
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<String> task) {
                        if (task.isSuccessful()) {
                            deviceToken = task.getResult();
                            Log.e(TAG, "onComplete: deviceToken: " + deviceToken);
                        }
                    }
                });*/

        deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "outside: deviceToken: " + deviceToken);

        HashMap<String, Object> profileMap = new HashMap<>();
        profileMap.put(Constants.uId, currentUserId);
        profileMap.put(Constants.authType, authType);
        profileMap.put(Constants.googlePic, retrieveGooglePic);
        profileMap.put(Constants.googleName, retrieveGoogleName);
        profileMap.put(Constants.facebookPic, retrieveFacebookPic);
        profileMap.put(Constants.facebookName, retrieveFacebookName);
        profileMap.put(Constants.device_token, deviceToken);
        profileMap.put(Constants.userFullName, userName);
        profileMap.put(Constants.userContact, userContact);
        profileMap.put(Constants.userStatus, userStatus);
        profileMap.put(Constants.profilePic, downloadUrl == null ? retrieveProfilePic : downloadUrl);

                    /*if (downloadUrl == null) {        //thisIfElseStatementWeAreDngInTernaryOperator.
                        profileMap.put(Constants.profilePic, retrieveProfilePic);
                    } else {
                        profileMap.put(Constants.profilePic, downloadUrl);
                    }*/


        userRef.child(currentUserId).updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SettingsActivity.this, "Thanks for Uploading Personal Info..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "ErrorInDB: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            resultUri = result.getUri();

            Log.e(TAG, "onActivityResult: resultUri: " + resultUri);

            final String imgPushId = userRef.child(currentUserId).child(Constants.profilePic).push().getKey();
            final StorageReference filePath = profilePicStorageRef.child(imgPushId + "_" + System.currentTimeMillis()
                    + "." + MimeTypeMap.getFileExtensionFromUrl(String.valueOf(resultUri)));

            final String apiFilePath = "profilePic" + System.currentTimeMillis() + "_" +  MimeTypeMap.getFileExtensionFromUrl(resultUri.toString());



            Log.e(TAG, "onActivityResult: filePath: " + filePath);

            /*   //notWorking:gettingNull @formatPlace So found another Process In Filepath.
            final StorageReference filePath = profilePicStorageRef.child(imgPushId + "_" + System.currentTimeMillis()
                    + "." + getExtensions());

            StorageReference filePath = profilePicStorageRef.child(currentUserId + ".jpg");*/

            filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SettingsActivity.this, "Pic Uploaded in Storage..", Toast.LENGTH_SHORT).show();

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri.toString();

                                Log.e(TAG, "onActivityResult: downloadUrl: " + downloadUrl);

                                userRef.child(currentUserId).child(Constants.profilePic).setValue(downloadUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                       if (task.isSuccessful()) {
                                                                           Toast.makeText(SettingsActivity.this, "Img saved in RTDB..!",
                                                                                   Toast.LENGTH_SHORT).show();
                                                                       }
                                                                       else {
                                                                           Toast.makeText(SettingsActivity.this, "ImgFailedToUploadInRTDB: "
                                                                                   + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                                       }
                                                                   }
                                                               }
                                        );
                            }
                        });
                    } else {
                        Toast.makeText(SettingsActivity.this, "ErrorInProfPicUpload :" +
                                task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //public String getExtensions() {       //notWorking:gettingNull@formatPlaceSoUsedAnotherProcessInFilepath.
    //MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
    //return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(resultUri));
    //}

    private void RetrieveUserInfo() {           //showing data in App after uploading values.
        userRef.child(currentUserId).child(Constants.authType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue().equals(Constants.googleAuth)) {
                    retrieveGoogleDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.phoneAuth)) {
                    retrievePhoneDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.nrmlAuth)) {
                    retrieveNrmlAuthDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.facebookAuth)) {
                    retrieveFacebookDB();
                } else {
                    Toast.makeText(SettingsActivity.this, "Error in Settings Activity DB..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public void retrieveGoogleDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userContact)
                        && !snapshot.hasChild(Constants.userStatus)) {
                    retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    et_fullName.setText(retrieveGoogleName);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else if (snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userContact)
                        && !snapshot.hasChild(Constants.userStatus)) {
                    retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_fullName.setText(retrieveGoogleName);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else if (!snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.googlePic)
                        && snapshot.hasChild(Constants.userFullName) && snapshot.hasChild(Constants.userStatus)
                        && snapshot.hasChild(Constants.userContact)) {

                    retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();  //thisLineIsForOnlyToKeepDataInDB

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else if (snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userStatus) && snapshot.hasChild(Constants.userContact)) {

                    retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();  //2
                    retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.googlePic) && snapshot.hasChild(Constants.googleName)
                        && snapshot.hasChild(Constants.userStatus) && snapshot.hasChild(Constants.userContact)) {

                    retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    et_fullName.setText(retrieveGoogleName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void retrievePhoneDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) &&
                        !snapshot.hasChild(Constants.userFullName) && !snapshot.hasChild(Constants.userStatus)) {

                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    et_userContact.setText(retrieveContact);
                } else if (!snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.userFullName) && !snapshot.hasChild(Constants.userStatus)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.profilePic)) {
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_userContact.setText(retrieveContact);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void retrieveNrmlAuthDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userStatus)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                } else if (!snapshot.hasChild(Constants.profilePic)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.userStatus)) {

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void retrieveFacebookDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookName) && !snapshot.hasChild(Constants.facebookPic)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();

                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    et_fullName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(civ_profilePic);
                    et_fullName.setText(retrieveFullName);
                    et_userContact.setText(retrieveContact);
                    et_userStatus.setText(retrieveStatus);
                } else {
                    Toast.makeText(SettingsActivity.this, "Something Went Wrong in Retrieving DB..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Toast.makeText(SettingsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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