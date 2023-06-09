package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.myapps.androidconcepts.Interfaces.VideoCallbacks;
import com.myapps.androidconcepts.Models.VideoModel;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = "VideoAdapter";
    private final Context context;
    private final VideoCallbacks videoCallbacks;
    private List<VideoModel> videoModelList = new ArrayList<>();
    private SimpleExoPlayer simpleExoPlayer;

    public VideoAdapter(Context context, List<VideoModel> videoModelList, VideoCallbacks videoCallbacks) {
        this.context = context;
        this.videoModelList = videoModelList;
        this.videoCallbacks = videoCallbacks;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item3, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoAdapter.VideoViewHolder holder, int position) {

        holder.tv_videoTitle.setText(videoModelList.get(position).getTitle());
        videoCallbacks.getPlayerView(holder.exoPlayerView);
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_videoTitle;
        private final PlayerView exoPlayerView;

        public VideoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_videoTitle = itemView.findViewById(R.id.tv_videoTitle);
            exoPlayerView = itemView.findViewById(R.id.exoPlayerView);
        }
    }

}
