package com.myapps.androidconcepts.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.androidconcepts.Models.AllTypesRetrofit_Model;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllTypesRetroAdapter extends RecyclerView.Adapter<AllTypesRetroAdapter.AllTypeRetroViewHolder> {
    private final Context context;
    private List<AllTypesRetrofit_Model> modelList = new ArrayList<>();
    private TextView tv_ID_prsntData, tv_setID_prsntData, tv_Title_prsntData, tv_Body_prsntData;
    private EditText et_update_ID, et_update_SetID, et_update_Title, et_update_Body;
    private AppCompatButton btn_Delete_Dialog, btn_Update_Dialog, btn_Patch_Dialog, btn_Cancel_Dialog;

    public AllTypesRetroAdapter(Context context, List<AllTypesRetrofit_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NotNull
    @Override
    public AllTypesRetroAdapter.AllTypeRetroViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_alltype_retro, parent, false);
        AllTypeRetroViewHolder viewHolder = new AllTypeRetroViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllTypesRetroAdapter.AllTypeRetroViewHolder holder, int position) {

        holder.tv_allTypeRetro_Id.setText("Id: " + modelList.get(holder.getAdapterPosition()).getId() + "");
        holder.tv_allTypeRetro_SetId.setText("Set Id: " + modelList.get(holder.getAdapterPosition()).getUserId() + "");
        holder.tv_allTypeRetro_Title.setText("Title: " + modelList.get(holder.getAdapterPosition()).getTitle());
        holder.tv_allTypeRetro_Description.setText("Description: " + modelList.get(holder.getAdapterPosition()).getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.updatedialog_alltype_retro, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(view);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                tv_setID_prsntData = view.findViewById(R.id.tv_setID_prsntData);
                tv_setID_prsntData.setText("");
                tv_ID_prsntData = view.findViewById(R.id.tv_ID_prsntData);
                tv_ID_prsntData.setText("");
                tv_Title_prsntData = view.findViewById(R.id.tv_Title_prsntData);
                tv_Title_prsntData.setText("");
                tv_Body_prsntData = view.findViewById(R.id.tv_Body_prsntData);
                tv_Body_prsntData.setText("");
                et_update_ID = view.findViewById(R.id.et_update_ID);
                et_update_SetID = view.findViewById(R.id.et_update_SetID);
                et_update_Title = view.findViewById(R.id.et_update_Title);
                et_update_Body = view.findViewById(R.id.et_update_Body);
                btn_Delete_Dialog = view.findViewById(R.id.btn_Delete_Dialog);
                btn_Update_Dialog = view.findViewById(R.id.btn_Update_Dialog);
                btn_Patch_Dialog = view.findViewById(R.id.btn_Patch_Dialog);
                btn_Cancel_Dialog = view.findViewById(R.id.btn_Cancel_Dialog);

                tv_setID_prsntData.append(modelList.get(holder.getAdapterPosition()).getUserId() + "");
                tv_ID_prsntData.append(modelList.get(holder.getAdapterPosition()).getId() + "");
                tv_Title_prsntData.append(modelList.get(holder.getAdapterPosition()).getTitle());
                tv_Body_prsntData.append(modelList.get(holder.getAdapterPosition()).getBody());

                btn_Cancel_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_Update_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String setId = et_update_SetID.getText().toString().trim();
                        String Id = et_update_ID.getText().toString().trim();
                        String title = et_update_Title.getText().toString().trim();
                        String body = et_update_Body.getText().toString().trim();

                        if (TextUtils.isEmpty(setId)) {
                            et_update_SetID.setError("new SetId Required..!");
                        } else if (TextUtils.isEmpty(Id)) {
                            et_update_ID.setError("new Id Required..!");
                        } else if (TextUtils.isEmpty(title)) {
                            et_update_Title.setError("new Id Required..!");
                        } else if (TextUtils.isEmpty(body)) {
                            et_update_Body.setError("new Id Required..!");
                        } else {
                           /* AllTypesRetrofit_Model model = new AllTypesRetrofit_Model(Integer.parseInt(setId), title, body);
                            myApi.PUT_data(Integer.parseInt(Id), model);
                            Toast.makeText(context, "Updated, Check it Once..", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();*/
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class AllTypeRetroViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_allTypeRetro_Id;
        private final TextView tv_allTypeRetro_SetId;
        private final TextView tv_allTypeRetro_Title;
        private final TextView tv_allTypeRetro_Description;

        public AllTypeRetroViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_allTypeRetro_Id = itemView.findViewById(R.id.tv_allTypeRetro_Id);
            tv_allTypeRetro_SetId = itemView.findViewById(R.id.tv_allTypeRetro_SetId);
            tv_allTypeRetro_Title = itemView.findViewById(R.id.tv_allTypeRetro_Title);
            tv_allTypeRetro_Description = itemView.findViewById(R.id.tv_allTypeRetro_Description);
        }
    }
}
