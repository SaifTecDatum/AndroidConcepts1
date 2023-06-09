package com.myapps.androidconcepts.y_MVVM_DesignPattern.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.models.NicePlaces;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MvvMAdapter extends RecyclerView.Adapter<MvvMAdapter.MvvmViewHolder> {
    private Context context;
    private List<NicePlaces> nicePlacesList = new ArrayList<>();

    public MvvMAdapter(Context context, List<NicePlaces> nicePlacesList) {
        this.context = context;
        this.nicePlacesList = nicePlacesList;
    }

    @NonNull
    @Override
    public MvvMAdapter.MvvmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowitem_mvvm, parent, false);
        MvvmViewHolder viewHolder = new MvvmViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MvvMAdapter.MvvmViewHolder holder, int position) {
        if (nicePlacesList != null) {
            holder.textView.setText(nicePlacesList.get(holder.getAdapterPosition()).getTitle());
            Picasso.get().load(nicePlacesList.get(holder.getAdapterPosition()).getImageUrl())
                    .placeholder(R.drawable.ic_add_videos).error(R.drawable.ic_add_videos).into(holder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return nicePlacesList.size();
    }

    public class MvvmViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView textView;

        public MvvmViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.civ_img);
            textView = itemView.findViewById(R.id.tv_title);

        }
    }
}
