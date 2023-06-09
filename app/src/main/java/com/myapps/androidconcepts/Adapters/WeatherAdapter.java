package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.WeatherModel;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private final Context context;
    private List<WeatherModel.Properties.Period> modelList = new ArrayList<>();

    public WeatherAdapter(Context context, List<WeatherModel.Properties.Period> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @NotNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_rowitem, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WeatherViewHolder holder, int position) {

        holder.tv_periodsNumber.setText("Number: " + modelList.get(holder.getAdapterPosition()).getNumber());
        holder.tv_periodsName.setText("Name: " + modelList.get(holder.getAdapterPosition()).getName());
        holder.tv_periodsTemperature.setText("Temp: " + modelList.get(holder.getAdapterPosition()).getTemperature());
        holder.tv_periodsTemperatureUnit.setText("Temp Unit: " + modelList.get(holder.getAdapterPosition()).getTemperatureUnit());
        holder.tv_periodsStartTime.setText("Start Time: " + modelList.get(holder.getAdapterPosition()).getStartTime());
        holder.tv_periodsEndTime.setText("End Time: " + modelList.get(holder.getAdapterPosition()).getEndTime());

        Picasso.get().load(modelList.get(holder.getAdapterPosition()).getIcon()).placeholder(R.drawable.add_location)
                .into(holder.civ_weatherIcon);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_periodsNumber;
        private final TextView tv_periodsName;
        private final TextView tv_periodsTemperature;
        private final TextView tv_periodsTemperatureUnit;
        private final TextView tv_periodsStartTime;
        private final TextView tv_periodsEndTime;
        private final CircleImageView civ_weatherIcon;

        public WeatherViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_periodsNumber = itemView.findViewById(R.id.tv_periodsNumber);
            tv_periodsName = itemView.findViewById(R.id.tv_periodsName);
            tv_periodsTemperature = itemView.findViewById(R.id.tv_periodsTemperature);
            tv_periodsTemperatureUnit = itemView.findViewById(R.id.tv_periodsTemperatureUnit);
            tv_periodsStartTime = itemView.findViewById(R.id.tv_periodsStartTime);
            tv_periodsEndTime = itemView.findViewById(R.id.tv_periodsEndTime);
            civ_weatherIcon = itemView.findViewById(R.id.civ_weatherIcon);
        }
    }
}
