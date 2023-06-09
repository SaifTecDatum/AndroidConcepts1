package com.myapps.androidconcepts.Adapters;

import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.myapps.androidconcepts.Database.AppDatabase;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Interfaces.UserDao;
import com.myapps.androidconcepts.Models.Users;
import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomDB_Adapter extends RecyclerView.Adapter<RoomDB_Adapter.RoomViewHolder> {
    private final Context context;
    private List<Users> usersList = new ArrayList<>();
    private TextView tv_ID_prsntData, tv_firstName_prsntData, tv_lastName_prsntData;
    private EditText et_update_FName, et_update_LName;
    private AppCompatButton btn_Delete_roomDB_Dialog, btn_Update_roomDB_Dialog, btn_Cancel_roomDB_Dialog;
    private Animation animation;

    public RoomDB_Adapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public RoomDB_Adapter.RoomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_roomdb, parent, false);
        RoomViewHolder viewHolder = new RoomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomDB_Adapter.RoomViewHolder holder, int position) {

        holder.tv_Id.setText(usersList.get(holder.getAdapterPosition()).getUid() + "");
        holder.tv_firstName.setText(usersList.get(holder.getAdapterPosition()).getFirstName());
        holder.tv_lastName.setText(usersList.get(holder.getAdapterPosition()).getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.updatedialog_roomdb, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(view);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                tv_ID_prsntData = view.findViewById(R.id.tv_ID_prsntData);
                tv_firstName_prsntData = view.findViewById(R.id.tv_firstName_prsntData);
                tv_lastName_prsntData = view.findViewById(R.id.tv_lastName_prsntData);
                et_update_FName = view.findViewById(R.id.et_update_FName);
                et_update_LName = view.findViewById(R.id.et_update_LName);
                btn_Update_roomDB_Dialog = view.findViewById(R.id.btn_Update_roomDB_Dialog);
                btn_Delete_roomDB_Dialog = view.findViewById(R.id.btn_Delete_roomDB_Dialog);
                btn_Cancel_roomDB_Dialog = view.findViewById(R.id.btn_Cancel_roomDB_Dialog);

                tv_ID_prsntData.setText(usersList.get(holder.getAdapterPosition()).getUid() + "");
                tv_firstName_prsntData.setText(usersList.get(holder.getAdapterPosition()).getFirstName());
                tv_lastName_prsntData.setText(usersList.get(holder.getAdapterPosition()).getLastName());

                btn_Cancel_roomDB_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                btn_Delete_roomDB_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class,
                                Constants.room_db_name).allowMainThreadQueries().build();
                        UserDao userDao = database.userDao();

                        Toast.makeText(context, usersList.get(holder.getAdapterPosition()).getFirstName() + " deleted Successfully..!", Toast.LENGTH_SHORT).show();
                        userDao.deleteById(usersList.get(holder.getAdapterPosition()).getUid());
                        usersList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        //notifyDataSetChanged();     //update the fresh list of ArrayList Data to retrieve.
                        alertDialog.dismiss();
                    }
                });

                btn_Update_roomDB_Dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(et_update_FName.getText().toString().trim())) {
                            et_update_FName.setError("First Name Required..!");
                        } else if (TextUtils.isEmpty(et_update_LName.getText().toString().trim())) {
                            et_update_LName.setError("Last Name Required..!");
                        } else {
                            AppDatabase database = Room.databaseBuilder(context, AppDatabase.class,
                                    Constants.room_db_name).allowMainThreadQueries().build();
                            UserDao userDao = database.userDao();

                            userDao.updateById(usersList.get(holder.getAdapterPosition()).getUid(),
                                    et_update_FName.getText().toString().trim(),
                                    et_update_LName.getText().toString().trim());
                            Toast.makeText(context, usersList.get(holder.getAdapterPosition()).getFirstName() +
                                    " Updated to " + et_update_FName.getText().toString(), Toast.LENGTH_SHORT).show();
                            usersList.clear();
                            usersList.addAll(userDao.getAllUsers());
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_Id, tv_firstName, tv_lastName;
        private final RelativeLayout parentLayout;

        public RoomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_Id = itemView.findViewById(R.id.tv_Id);
            tv_firstName = itemView.findViewById(R.id.tv_firstName);
            tv_lastName = itemView.findViewById(R.id.tv_lastName);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            parentLayout.setAnimation(animation);

        }
    }
}
