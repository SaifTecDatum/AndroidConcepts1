package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.RetrofitAirlines_Model;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AirlinesRetroAdapter extends RecyclerView.Adapter<AirlinesRetroAdapter.AirlinesViewHolder> {
    private final Context context;
    private List<RetrofitAirlines_Model> airlinesModelList = new ArrayList<>();

    public AirlinesRetroAdapter(Context context, List<RetrofitAirlines_Model> airlinesModelList) {
        this.context = context;
        this.airlinesModelList = airlinesModelList;
    }

    @NotNull
    @Override
    public AirlinesRetroAdapter.AirlinesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_airlines, parent, false);
        AirlinesViewHolder viewHolder = new AirlinesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AirlinesRetroAdapter.AirlinesViewHolder holder, int position) {

        holder.tv_airline_Id.setText("ID: " + airlinesModelList.get(position).getId() + "");

        if (airlinesModelList.get(position).getLogo() != null && airlinesModelList.get(position).getLogo().length() > 0) {

            holder.tv_airline_Country.setText("Country: " + airlinesModelList.get(position).getCountry() + "");
            holder.tv_airline_Established.setText("Established: " + airlinesModelList.get(position).getEstablished() + "");
            holder.tv_airline_Name.setText("Name: " + airlinesModelList.get(position).getName() + "");
            holder.tv_airline_headQuarters.setText("HeadQuarters: " + airlinesModelList.get(position).getHead_quaters() + "");
            holder.tv_airline_Slogan.setText("Slogan: " + airlinesModelList.get(position).getSlogan() + "");
            holder.tv_airline_Website.setText("Website: " + airlinesModelList.get(position).getWebsite() + "");
            Picasso.get().load(airlinesModelList.get(position).getLogo()).placeholder(R.drawable.ic_payment)
                    .into(holder.iv_airlineImg);
        } else {
            holder.tv_airline_Country.setText("Country: " + airlinesModelList.get(position).getCountry() + "");
            holder.tv_airline_Established.setText("Established: " + airlinesModelList.get(position).getEstablished() + "");
            holder.tv_airline_Name.setText("Name: " + airlinesModelList.get(position).getName() + "");
            holder.tv_airline_headQuarters.setText("HeadQuarters: " + airlinesModelList.get(position).getHead_quaters() + "");
            holder.tv_airline_Slogan.setText("Slogan: " + airlinesModelList.get(position).getSlogan() + "");
            holder.tv_airline_Website.setText("Website: " + airlinesModelList.get(position).getWebsite() + "");
        }
    }

    @Override
    public int getItemCount() {
        return airlinesModelList.size();
    }

    public class AirlinesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_airline_Id;
        private final TextView tv_airline_Country;
        private final TextView tv_airline_Established;
        private final TextView tv_airline_Name;
        private final TextView tv_airline_headQuarters;
        private final TextView tv_airline_Slogan;
        private final TextView tv_airline_Website;
        private final AppCompatImageView iv_airlineImg;

        public AirlinesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_airline_Id = itemView.findViewById(R.id.tv_airline_Id);
            tv_airline_Country = itemView.findViewById(R.id.tv_airline_Country);
            tv_airline_Established = itemView.findViewById(R.id.tv_airline_Established);
            tv_airline_Name = itemView.findViewById(R.id.tv_airline_Name);
            tv_airline_headQuarters = itemView.findViewById(R.id.tv_airline_headQuarters);
            tv_airline_Slogan = itemView.findViewById(R.id.tv_airline_Slogan);
            tv_airline_Website = itemView.findViewById(R.id.tv_airline_Website);
            iv_airlineImg = itemView.findViewById(R.id.iv_airlineImg);
        }
    }
}
