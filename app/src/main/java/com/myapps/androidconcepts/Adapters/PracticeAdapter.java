package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.PracticeModel;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder> {
    private Context context;
    private List<PracticeModel.Properties.Period> periodList = new ArrayList<>();

    public PracticeAdapter(Context context, List<PracticeModel.Properties.Period> periodList) {
        this.context = context;
        this.periodList = periodList;
    }

    @NonNull
    @NotNull
    @Override
    public PracticeAdapter.PracticeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_rowitem, parent, false);
        PracticeViewHolder viewHolder = new PracticeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PracticeAdapter.PracticeViewHolder holder, int position) {

        if (periodList != null && periodList.size() > 0) {
            holder.tv_periodsNumber.setText("Number: " + periodList.get(position).getNumber());
            holder.tv_periodsName.setText("Name: " + periodList.get(position).getName());
            holder.tv_periodsTemperature.setText("Temp: " + periodList.get(position).getTemperature());
            holder.tv_periodsTemperatureUnit.setText("Temp Unit: " + periodList.get(position).getTemperatureUnit());
            holder.tv_periodsWindSpeed.setText("Wind Speed: " + periodList.get(position).getWindSpeed());
            holder.tv_periodsWindDirection.setText("Wind Direction: " + periodList.get(position).getWindDirection());
            holder.tv_periodsStartTime.setText("Start Time: " + periodList.get(position).getStartTime());
            holder.tv_periodsEndTime.setText("End Time: " + periodList.get(position).getEndTime());
            holder.tv_periodsShortCast.setText("Short Forecast: " + periodList.get(position).getShortForecast());
            holder.tv_periodsDetailedCast.setText("Detailed Forecast: " + periodList.get(position).getDetailedForecast());

            Picasso.get().load(periodList.get(position).getIcon()).placeholder(R.drawable.add_location).into(holder.civ_weatherIcon);
        }
    }

    @Override
    public int getItemCount() {
        return periodList.size();
    }

    public class PracticeViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_periodsNumber, tv_periodsName, tv_periodsTemperature, tv_periodsTemperatureUnit, tv_periodsWindSpeed,
                tv_periodsWindDirection, tv_periodsStartTime, tv_periodsEndTime, tv_periodsShortCast, tv_periodsDetailedCast;
        private CircleImageView civ_weatherIcon;

        public PracticeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_periodsNumber = itemView.findViewById(R.id.tv_periodsNumber);
            tv_periodsName = itemView.findViewById(R.id.tv_periodsName);
            tv_periodsTemperature = itemView.findViewById(R.id.tv_periodsTemperature);
            tv_periodsTemperatureUnit = itemView.findViewById(R.id.tv_periodsTemperatureUnit);
            tv_periodsWindSpeed = itemView.findViewById(R.id.tv_periodsWindSpeed);
            tv_periodsWindDirection = itemView.findViewById(R.id.tv_periodsWindDirection);
            tv_periodsStartTime = itemView.findViewById(R.id.tv_periodsStartTime);
            tv_periodsEndTime = itemView.findViewById(R.id.tv_periodsEndTime);
            tv_periodsShortCast = itemView.findViewById(R.id.tv_periodsShortCast);
            tv_periodsDetailedCast = itemView.findViewById(R.id.tv_periodsDetailedCast);
            civ_weatherIcon = itemView.findViewById(R.id.civ_weatherIcon);

        }

    }
}
