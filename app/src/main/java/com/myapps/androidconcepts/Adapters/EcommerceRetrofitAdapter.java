package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.RetrofitEcommerce_Model;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EcommerceRetrofitAdapter extends RecyclerView.Adapter<EcommerceRetrofitAdapter.EcommerceViewHolder> {
    private final Context context;
    private List<RetrofitEcommerce_Model> ecommerceModelList = new ArrayList<>();

    public EcommerceRetrofitAdapter(Context context, List<RetrofitEcommerce_Model> ecommerceModelList) {
        this.context = context;
        this.ecommerceModelList = ecommerceModelList;
    }

    @NotNull
    @Override
    public EcommerceRetrofitAdapter.EcommerceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_ecommerce, parent, false);
        EcommerceViewHolder viewHolder = new EcommerceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EcommerceRetrofitAdapter.EcommerceViewHolder holder, int position) {
        holder.imgBtn_ratingDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ratingChilds_LinLay.getVisibility() == View.GONE) {
                    holder.ratingChilds_LinLay.setVisibility(View.VISIBLE);
                } else {
                    holder.ratingChilds_LinLay.setVisibility(View.GONE);
                }
            }
        });

        holder.tv_retroList_Id.append(ecommerceModelList.get(position).getId() + "");
        holder.tv_retroList_Title.append(ecommerceModelList.get(position).getTitle());
        holder.tv_retroList_Description.append(ecommerceModelList.get(position).getDescription());
        holder.tv_retroList_Price.append(ecommerceModelList.get(position).getPrice() + "");
        holder.tv_retroList_Category.append(ecommerceModelList.get(position).getCategory());
        holder.tv_retroList_Rate.append(ecommerceModelList.get(position).getRating().getRate() + "");
        holder.tv_retroList_Count.append(ecommerceModelList.get(position).getRating().getCount() + "");

        Picasso.get().load(ecommerceModelList.get(position).getImage()).placeholder(R.drawable.cart).into(holder.imgVw_productImg);
    }

    @Override
    public int getItemCount() {
        return ecommerceModelList.size();
    }

    public class EcommerceViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_retroList_Id;
        private final TextView tv_retroList_Title;
        private final TextView tv_retroList_Description;
        private final TextView tv_retroList_Price;
        private final TextView tv_retroList_Category;
        private final TextView tv_retroList_Rate;
        private final TextView tv_retroList_Count;
        private final AppCompatImageView imgVw_productImg;
        private final AppCompatImageButton imgBtn_ratingDownArrow;
        private final LinearLayout ratingChilds_LinLay;

        public EcommerceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_retroList_Id = itemView.findViewById(R.id.tv_retroList_Id);
            tv_retroList_Title = itemView.findViewById(R.id.tv_retroList_Title);
            tv_retroList_Description = itemView.findViewById(R.id.tv_retroList_Description);
            tv_retroList_Price = itemView.findViewById(R.id.tv_retroList_Price);
            tv_retroList_Category = itemView.findViewById(R.id.tv_retroList_Category);
            tv_retroList_Rate = itemView.findViewById(R.id.tv_retroList_Rate);
            tv_retroList_Count = itemView.findViewById(R.id.tv_retroList_Count);
            imgVw_productImg = itemView.findViewById(R.id.iv_productImg);
            imgBtn_ratingDownArrow = itemView.findViewById(R.id.imgBtn_ratingDownArrow);
            ratingChilds_LinLay = itemView.findViewById(R.id.ratingChilds_LinLay);
        }
    }
}