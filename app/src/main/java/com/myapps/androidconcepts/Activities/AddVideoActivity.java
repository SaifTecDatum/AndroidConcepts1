package com.myapps.androidconcepts.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.VideoModel;
import com.myapps.androidconcepts.databinding.ActivityAddVideoBinding;

public class AddVideoActivity extends AppCompatActivity {
    private Uri videoUri;
    private MediaController mediaController;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, videoRef;
    private StorageReference VideoStorageRef;
    private String currentUserId;
    private ActivityAddVideoBinding addVideoBinding;

    private void InitializeFields() {
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        videoRef = userRef.child(currentUserId).child(Constants.videos);
        VideoStorageRef = FirebaseStorage.getInstance().getReference().child(Constants.myVideos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addVideoBinding = ActivityAddVideoBinding.inflate(getLayoutInflater());
        setContentView(addVideoBinding.getRoot());

        InitializeFields();

        mediaController = new MediaController(AddVideoActivity.this);
        addVideoBinding.videoView.setMediaController(mediaController);
        addVideoBinding.videoView.start();

        addVideoBinding.btnBrowseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddVideoActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddVideoActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    videoLauncher();
                }

                /*Dexter.withContext(AddVideoActivity.this)         //Dexter: 3rdPartyLibraryToImplementPermissionProcess.
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("video/*");
                                startActivityForResult(intent.createChooser(intent, ""), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();*/
            }
        });

        addVideoBinding.btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addVideoBinding.etVideoTitle.getText().toString().trim())) {
                    Toast.makeText(AddVideoActivity.this, "Please enter Videos Title..", Toast.LENGTH_SHORT).show();
                } else if (videoUri == null) {
                    Toast.makeText(AddVideoActivity.this, "Video has not Uploaded..!", Toast.LENGTH_SHORT).show();
                } else {
                    VideoUploadingProcess();
                }
            }
        });

    }

    private void videoLauncher() {
        Intent videoIntent = new Intent();
        videoIntent.setAction(Intent.ACTION_GET_CONTENT);
        videoIntent.setType("video/*");
        startActivityForResult(Intent.createChooser(videoIntent, ""), 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            videoLauncher();
                        } else {
                            Toast.makeText(this, "Need Storage Permissions..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            videoUri = data.getData();
            addVideoBinding.videoView.setVideoURI(videoUri);
        }
    }

    public String getExtensions() {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videoUri));
    }

    private void VideoUploadingProcess() {
        final ProgressDialog progressDialog = new ProgressDialog(AddVideoActivity.this);
        progressDialog.setTitle("Video Uploading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String msgPushId = videoRef.push().getKey();

        final StorageReference filePath = VideoStorageRef.child(msgPushId + "_" + System.currentTimeMillis() +
                "." + getExtensions());

        filePath.putFile(videoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        VideoModel videoModel = new VideoModel(addVideoBinding.etVideoTitle.getText().toString(), uri.toString());

                        userRef.child(currentUserId).child(Constants.videos).child(msgPushId).setValue(videoModel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(AddVideoActivity.this, MyVideosActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            Toast.makeText(AddVideoActivity.this, "Successfully Uploaded..", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddVideoActivity.this, "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percentage = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " + (int) percentage + "%");
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