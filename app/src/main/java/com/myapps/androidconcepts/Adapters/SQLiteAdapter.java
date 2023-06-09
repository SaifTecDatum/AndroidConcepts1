package com.myapps.androidconcepts.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Interfaces.MyCallbacks;
import com.myapps.androidconcepts.Models.SQLiteModel;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter extends RecyclerView.Adapter<SQLiteAdapter.SQliteViewHolder> {
    private final Context context;
    private List<SQLiteModel> modelList = new ArrayList<>();
    private final MyCallbacks myCallbacks;
    private Animation animation;
    private TextView tv_country_prsntData, tv_descrptn_prsntData, tv_GDP_prsntData;
    private EditText et_updateCountry, et_updateDescription, et_updateGDP;
    private AppCompatButton btn_Delete_Dialog, btn_Update_Dialog, btn_Cancel_Dialog;

    public SQLiteAdapter(Context context, List<SQLiteModel> modelList, MyCallbacks myCallbacks) {
        this.context = context;
        this.modelList = modelList;
        this.myCallbacks = myCallbacks;
    }

    @NotNull
    @Override
    public SQLiteAdapter.SQliteViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        SQliteViewHolder viewHolder = new SQliteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull SQLiteAdapter.SQliteViewHolder holder, int position) {
        holder.tv_countryId.setText(modelList.get(position).get_id() + "");
        holder.tv_countryTitle.setText(modelList.get(position).getCountry());
        holder.tv_countryDescrptn.setText(modelList.get(position).getDescription());
        holder.tv_countryGDP.setText(modelList.get(position).getGdp() + "");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(view);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                tv_country_prsntData = view.findViewById(R.id.tv_country_prsntData);
                tv_descrptn_prsntData = view.findViewById(R.id.tv_descrptn_prsntData);
                tv_GDP_prsntData = view.findViewById(R.id.tv_GDP_prsntData);
                et_updateCountry = view.findViewById(R.id.et_updateCountry);
                et_updateDescription = view.findViewById(R.id.et_updateDescription);
                et_updateGDP = view.findViewById(R.id.et_updateGDP);
                btn_Delete_Dialog = view.findViewById(R.id.btn_Delete_Dialog);
                btn_Update_Dialog = view.findViewById(R.id.btn_Update_Dialog);
                btn_Cancel_Dialog = view.findViewById(R.id.btn_Cancel_Dialog);

                tv_country_prsntData.setText(modelList.get(position).getCountry());
                tv_descrptn_prsntData.setText(modelList.get(position).getDescription());
                tv_GDP_prsntData.setText(modelList.get(position).getGdp() + "");

                btn_Cancel_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_Delete_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myCallbacks.deleteItems(modelList.get(position).get_id());
                        Toast.makeText(context, modelList.get(position).getCountry() + " Successfully Deleted..",
                                Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

                btn_Update_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String countryName = et_updateCountry.getText().toString().trim();
                        String countryDescrptn = et_updateDescription.getText().toString().trim();
                        String countryGdp = et_updateGDP.getText().toString().trim();

                        if (TextUtils.isEmpty(countryName)) {
                            Toast.makeText(context, "Country name Required..", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(countryDescrptn)) {
                            Toast.makeText(context, "Short Info Required..", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(countryGdp)) {
                            Toast.makeText(context, "New GDP Required..", Toast.LENGTH_SHORT).show();
                        } else {
                            myCallbacks.updateItems(modelList.get(position).get_id(), countryName, countryDescrptn,
                                    Double.parseDouble(countryGdp));
                            Toast.makeText(context, modelList.get(position).getCountry() + " Updated to New details..",
                                    Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class SQliteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_countryId;
        private final TextView tv_countryTitle;
        private final TextView tv_countryDescrptn;
        private final TextView tv_countryGDP;
        private final LinearLayout parent_LinLay;

        public SQliteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_countryId = itemView.findViewById(R.id.tv_countryId);
            tv_countryTitle = itemView.findViewById(R.id.tv_countryTitle);
            tv_countryDescrptn = itemView.findViewById(R.id.tv_countryDescrptn);
            tv_countryGDP = itemView.findViewById(R.id.tv_countryGDP);
            parent_LinLay = itemView.findViewById(R.id.parent_LinLay);
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            parent_LinLay.setAnimation(animation);
        }
    }
}