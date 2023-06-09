package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.Pagination_Model;
import com.myapps.androidconcepts.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.PaginationViewHolder> {
    private final Context context;
    private List<Pagination_Model> modelList = new ArrayList<>();

    public PaginationAdapter(Context context, List<Pagination_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NotNull
    @Override
    public PaginationAdapter.PaginationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_pagination, parent, false);
        PaginationViewHolder viewHolder = new PaginationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PaginationAdapter.PaginationViewHolder holder, int position) {

        if (modelList.get(holder.getAdapterPosition()).getDownload_url() != null && modelList.get(holder.getAdapterPosition()).getDownload_url().length() > 0) {
            holder.tv_pagination_Id.setText("Id: " + modelList.get(holder.getAdapterPosition()).getId());
            holder.tv_pagination_Author.setText("Author: " + modelList.get(holder.getAdapterPosition()).getAuthor());
            Picasso.get().load(modelList.get(holder.getAdapterPosition()).getDownload_url())
                    .resize(200, 150).placeholder(R.drawable.cart).into(holder.iv_paginationImg);

            /*Uri imgUri = Uri.parse(modelList.get(holder.getAdapterPosition()).getDownload_url());
            holder.iv_paginationImg.setImageURI(imgUri);*/

            //Tried to fetch img using Uri & Bitmap, both process are not working.

            /*Bitmap image  = BitmapFactory.decodeFile(modelList.get(holder.getAdapterPosition()).getDownload_url());
            holder.iv_paginationImg.setImageBitmap(image);*/

        } else {
            holder.tv_pagination_Id.setText("Id: " + modelList.get(holder.getAdapterPosition()).getId());
            holder.tv_pagination_Author.setText("Author: " + modelList.get(holder.getAdapterPosition()).getAuthor());
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class PaginationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_pagination_Id;
        private final TextView tv_pagination_Author;
        private final AppCompatImageView iv_paginationImg;

        public PaginationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_pagination_Id = itemView.findViewById(R.id.tv_pagination_Id);
            tv_pagination_Id.setText("");
            tv_pagination_Author = itemView.findViewById(R.id.tv_pagination_Author);
            tv_pagination_Author.setText("");
            iv_paginationImg = itemView.findViewById(R.id.iv_paginationImg);
        }
    }
}
