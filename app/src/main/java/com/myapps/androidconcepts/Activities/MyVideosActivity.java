package com.myapps.androidconcepts.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.VideoModel;
import com.myapps.androidconcepts.R;

public class MyVideosActivity extends AppCompatActivity {
    private RecyclerView videoRecyclerView;
    private FloatingActionButton fab_uploadVideos;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, videoRef;
    private String currentUserId;
    private SimpleExoPlayer simpleExoPlayer;
    private long mLastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos);

        fab_uploadVideos = findViewById(R.id.fab_uploadVideos);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(MyVideosActivity.this));
        videoRecyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        videoRef = userRef.child(currentUserId).child(Constants.videos);
        //String msgPushId = videoRef.push().getKey();

        fab_uploadVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyVideosActivity.this, AddVideoActivity.class));
                Toast.makeText(MyVideosActivity.this, "Please Upload your Videos..", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseRecyclerOptions<VideoModel> options = new FirebaseRecyclerOptions.Builder<VideoModel>()
                .setQuery(videoRef, VideoModel.class).build();

        FirebaseRecyclerAdapter<VideoModel, VideoViewHolder> adapter = new FirebaseRecyclerAdapter<VideoModel, VideoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VideoViewHolder holder, int position, @NonNull VideoModel model) {

                try {
                    holder.tv_videoTitle.setText(model.getTitle());

                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);

                    Uri videoURI = Uri.parse(model.getvUrl());

                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                    holder.exoPlayerView.setPlayer(simpleExoPlayer);
                    simpleExoPlayer.prepare(mediaSource);
                    simpleExoPlayer.setRepeatMode(simpleExoPlayer.REPEAT_MODE_ONE);
                    simpleExoPlayer.setPlayWhenReady(false);
                } catch (Exception e) {
                    Log.e("ExoPlayer Crashed", e.getLocalizedMessage());
                }
            }

            @NonNull
            @Override
            public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item3, parent, false);
                VideoViewHolder videoViewHolder = new VideoViewHolder(view);
                return videoViewHolder;
            }
        };

        adapter.startListening();
        videoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            mLastPosition = simpleExoPlayer.getContentPosition();
        }

        Utilities.onPauseToUnRegister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLastPosition != 0 && simpleExoPlayer != null) {
            simpleExoPlayer.seekTo(mLastPosition);
        }

        Utilities.onResumeToRegister(this);
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_videoTitle;
        private final PlayerView exoPlayerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_videoTitle = itemView.findViewById(R.id.tv_videoTitle);
            exoPlayerView = itemView.findViewById(R.id.exoPlayerView);
        }

    }
}


/*
package com.myapps.androidconcepts.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.myapps.androidconcepts.Adapters.VideoAdapter;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Interfaces.VideoCallbacks;
import com.myapps.androidconcepts.Models.VideoModel;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyVideosActivity extends AppCompatActivity implements VideoCallbacks {
    private RecyclerView videoRecyclerView;
    private FloatingActionButton fab_uploadVideos;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, videoRef;
    private String currentUserId;
    private SimpleExoPlayer simpleExoPlayer;
    private long mLastPosition;
    private VideoAdapter adapter;
    private List<VideoModel> videoModelList = new ArrayList<>();
    private static final String TAG = "MyVideosActivity";
    private VideoCallbacks videoCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos);

        fab_uploadVideos = findViewById(R.id.fab_uploadVideos);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(MyVideosActivity.this));
        videoRecyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        videoRef = userRef.child(currentUserId).child(Constants.videos);

        getVideos();

        fab_uploadVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyVideosActivity.this, AddVideoActivity.class));
                Toast.makeText(MyVideosActivity.this, "Please Upload your Videos..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVideos() {
        videoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    videoModelList.add(dataSnapshot.getValue(VideoModel.class));

                    Log.e(TAG, "onDataChange: " + new Gson().toJson(dataSnapshot.getValue(VideoModel.class)));

                    try {
                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                        simpleExoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);

                        Uri videoUri = Uri.parse(videoModelList.get(dataSnapshot.hashCode()).getvUrl());

                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                        MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

                        simpleExoPlayer.prepare(mediaSource);
                        simpleExoPlayer.setRepeatMode(simpleExoPlayer.REPEAT_MODE_ONE);
                        simpleExoPlayer.setPlayWhenReady(false);

                    } catch (Exception e) {
                        Log.e(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
                        Toast.makeText(MyVideosActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                adapter = new VideoAdapter(MyVideosActivity.this, videoModelList, videoCallbacks);
                videoRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(MyVideosActivity.this, "Something Went Wrong..!" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            mLastPosition = simpleExoPlayer.getContentPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLastPosition != 0 && simpleExoPlayer != null) {
            simpleExoPlayer.seekTo(mLastPosition);
        }
    }


    @Override
    public PlayerView getPlayerView(PlayerView playerView) {
        playerView.setPlayer(simpleExoPlayer);
        return playerView;
    }
}
*/
