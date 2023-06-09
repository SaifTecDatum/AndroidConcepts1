package com.myapps.androidconcepts.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private final List<String> permissionsList = new ArrayList<>();
    private AppCompatImageView iv_viewCamera;
    private AppCompatButton btn_openCamera;
    private String currentPhotoPath;                                    //codeToRemember. 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        iv_viewCamera = findViewById(R.id.iv_viewCamera);
        btn_openCamera = findViewById(R.id.btn_openCamera);

        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });
    }

    private void checkAndRequestPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA);
        int readExtStorage = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExtStorage = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (readExtStorage != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (writeExtStorage != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionsList.isEmpty()) {
            ActivityCompat.requestPermissions(CameraActivity.this, permissionsList.toArray(new String[]{permissionsList.size() + ""}), REQUEST_IMAGE_CAPTURE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.CAMERA)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            dispatchTakePictureIntent();
                        } else {
                            Toast.makeText(CameraActivity.this, "Camera & Storage Permissions Required..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            Toast.makeText(CameraActivity.this, "Camera & Storage Permissions Required..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm").format(new Date()); //in Android 12 devices this line code
        //"colon = :" wont work.So replace with similar(. or - or _) or remove it.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_hhmm a").format(new Date());

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imgFile = File.createTempFile("IMG-" + timeStamp + "_", ".jpg", storageDir);

        currentPhotoPath = imgFile.getAbsolutePath();

        return imgFile;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {       //codeToRemember. 2

            File photoFile = null;
            try {
                photoFile = createImageFile();              //codeToRemember. 3
            } catch (IOException ex) {
                Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "dispatchTakePictureIntent: " + ex.getLocalizedMessage());
            }

            if (photoFile != null) {                            //codeToRemember. 4 : belowTwoLines
                Uri photoURI = FileProvider.getUriForFile(this, "com.myapps.androidconcepts.fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(imgFile);
            iv_viewCamera.setImageURI(contentUri);

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);          //codeToRemember. 5
            intent.setData(contentUri);     //Broadcast Action: Request the media scanner to scan a file and add it to the media database.
            CameraActivity.this.sendBroadcast(intent);
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