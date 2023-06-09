package com.myapps.androidconcepts.Helpers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GarbageClass extends AppCompatActivity {
    private DatabaseReference usersRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*-KItqNxLqzQoLnUCb9sJaddclose
        time: "Thu Apr 28 17:12:05 PDT 2016"
        timestamp: 1461888725444*/

        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);

        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);

        Query oldItems = usersRef.orderByChild("timestamp").endAt(cutoff);

        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }


    /*Call<List<Pagination_Model>> call = MainApplication.getPaginationRestClient().getMyApi().getPaginationModel(page, limit);

        call.enqueue(new Callback<List<Pagination_Model>>() {       //triedToGetUsingArrayList&ModelButNotGettingInThisWay.
            @Override
            public void onResponse(Call<List<Pagination_Model>> call, Response<List<Pagination_Model>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    for (int i = 0; i < response.body().size(); i++) {

                        Pagination_Model model = new Pagination_Model();
                        model.setId(response.body().get(i).getId());
                        model.setAuthor(response.body().get(i).getAuthor());
                        model.setDownload_url(response.body().get(i).getDownload_url());

                        modelList.add(model);

                        adapter = new PaginationAdapter(PaginationRetroRcyclrActivity.this, modelList);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(PaginationRetroRcyclrActivity.this, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pagination_Model>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PaginationRetroRcyclrActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

}

/*
public class GarbageClass extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.video_record_activity);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.CameraView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);
    }

    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        recorder.setOutputFile("/sdcard/videocapture_example.mp4");
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public void onClick(View v) {
        if (recording) {
            recorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
            recorder.start();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }
}*/
