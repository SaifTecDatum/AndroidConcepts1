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

import com.myapps.androidconcepts.Interfaces.MyMviCallbacks;
import com.myapps.androidconcepts.Models.SecndSqliteModel;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class SecndSqliteAdapter extends RecyclerView.Adapter<SecndSqliteAdapter.SecndDbViewHolder> {
    private List<SecndSqliteModel> secndList = new ArrayList<>();
    private final Context context;
    private final MyMviCallbacks mviCallbacks;
    private Animation transSecndAnim;
    private TextView tv_movie_prsntData, tv_details_prsntData, tv_IMDB_prsntData;
    private EditText et_updateMovie, et_updateDetails, et_updateIMDB;
    private AppCompatButton btn_DeleteMvi_Dialog, btn_UpdateMvi_Dialog, btn_CancelMvi_Dialog;

    public SecndSqliteAdapter(List<SecndSqliteModel> secndList, Context context, MyMviCallbacks mviCallbacks) {
        this.secndList = secndList;
        this.context = context;
        this.mviCallbacks = mviCallbacks;
    }

    @NonNull
    @Override
    public SecndSqliteAdapter.SecndDbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item2, parent, false);
        SecndDbViewHolder secndDbViewHolder = new SecndDbViewHolder(view);
        return secndDbViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SecndSqliteAdapter.SecndDbViewHolder holder, int position) {

        if (secndList != null && secndList.size() > 0) {
            holder.tv_movieId.setText(secndList.get(holder.getAdapterPosition()).get_id() + "");
            holder.tv_movieTitle.setText(secndList.get(holder.getAdapterPosition()).getMovie());
            holder.tv_movieDetails.setText(secndList.get(holder.getAdapterPosition()).getMovieDetails());
            holder.tv_movieImdb.setText(secndList.get(holder.getAdapterPosition()).getImdb() + "");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.upgrade_dialog1, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.setView(view);
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();

                    tv_movie_prsntData = view.findViewById(R.id.tv_movie_prsntData);
                    tv_details_prsntData = view.findViewById(R.id.tv_details_prsntData);
                    tv_IMDB_prsntData = view.findViewById(R.id.tv_IMDB_prsntData);
                    et_updateMovie = view.findViewById(R.id.et_updateMovie);
                    et_updateDetails = view.findViewById(R.id.et_updateDetails);
                    et_updateIMDB = view.findViewById(R.id.et_updateIMDB);
                    btn_DeleteMvi_Dialog = view.findViewById(R.id.btn_DeleteMvi_Dialog);
                    btn_CancelMvi_Dialog = view.findViewById(R.id.btn_CancelMvi_Dialog);
                    btn_UpdateMvi_Dialog = view.findViewById(R.id.btn_UpdateMvi_Dialog);

                    tv_movie_prsntData.setText(secndList.get(holder.getAdapterPosition()).getMovie());
                    tv_details_prsntData.setText(secndList.get(holder.getAdapterPosition()).getMovieDetails());
                    tv_IMDB_prsntData.setText(secndList.get(holder.getAdapterPosition()).getImdb() + "");

                    btn_CancelMvi_Dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    btn_DeleteMvi_Dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, secndList.get(holder.getAdapterPosition()).getMovie() +
                                    ": deleted successfully..", Toast.LENGTH_SHORT).show();
                            mviCallbacks.deleteMovies(secndList.get(holder.getAdapterPosition()).get_id());
                            alertDialog.dismiss();
                        }
                    });

                    //1st. Process starts from here,  2nd. In its activity &  3rd. In its sqlite_helperClass.
                    //4th. Again from sqlite_helperClass returns to activity with result value,  5th. In activity by comparing
                    //result value we refresh the list items by help of callbacks,  6th. shows in recyclerAdapter to the users.
                    btn_UpdateMvi_Dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String mviName_input = et_updateMovie.getText().toString().trim();
                            String mviDetails_input = et_updateDetails.getText().toString().trim();
                            String mviImdb_input = et_updateIMDB.getText().toString().trim();

                            if (TextUtils.isEmpty(mviName_input) && TextUtils.isEmpty(mviDetails_input) &&
                                    TextUtils.isEmpty(mviImdb_input)) {
                                et_updateMovie.setError("Fields should not be empty..!");
                                et_updateDetails.setError("Fields should not be empty..!");
                                et_updateIMDB.setError("Fields should not be empty..!");
                                Toast.makeText(context, "At least one input value is required to update..! ", Toast.LENGTH_SHORT).show();
                            } else if (!TextUtils.isEmpty(mviName_input) && TextUtils.isEmpty(mviDetails_input) &&
                                    TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        mviName_input,
                                        secndList.get(holder.getAdapterPosition()).getMovieDetails(),
                                        secndList.get(holder.getAdapterPosition()).getImdb());

                                Toast.makeText(context, "Movie Name Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (TextUtils.isEmpty(mviName_input) && !TextUtils.isEmpty(mviDetails_input) &&
                                    TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        secndList.get(holder.getAdapterPosition()).getMovie(),
                                        mviDetails_input,
                                        secndList.get(holder.getAdapterPosition()).getImdb());

                                Toast.makeText(context, "Movie Details Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (TextUtils.isEmpty(mviName_input) && TextUtils.isEmpty(mviDetails_input) &&
                                    !TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        secndList.get(holder.getAdapterPosition()).getMovie(),
                                        secndList.get(holder.getAdapterPosition()).getMovieDetails(),
                                        Double.parseDouble(mviImdb_input));

                                Toast.makeText(context, "Movie Imdb Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (!TextUtils.isEmpty(mviName_input) && !TextUtils.isEmpty(mviDetails_input) &&
                                    TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        mviName_input,
                                        mviDetails_input,
                                        secndList.get(holder.getAdapterPosition()).getImdb());

                                Toast.makeText(context, "Movie Name & Details Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (!TextUtils.isEmpty(mviName_input) && TextUtils.isEmpty(mviDetails_input) &&
                                    !TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        mviName_input,
                                        secndList.get(holder.getAdapterPosition()).getMovieDetails(),
                                        Double.parseDouble(mviImdb_input));

                                Toast.makeText(context, "Movie Name & Imdb Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (TextUtils.isEmpty(mviName_input) && !TextUtils.isEmpty(mviDetails_input) &&
                                    !TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        secndList.get(holder.getAdapterPosition()).getMovie(),
                                        mviDetails_input,
                                        Double.parseDouble(mviImdb_input));

                                Toast.makeText(context, "Movie Details & Imdb Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else if (!TextUtils.isEmpty(mviName_input) && !TextUtils.isEmpty(mviDetails_input) &&
                                    !TextUtils.isEmpty(mviImdb_input)) {

                                mviCallbacks.updateMovies(
                                        secndList.get(holder.getAdapterPosition()).get_id(),
                                        mviName_input,
                                        mviDetails_input,
                                        Double.parseDouble(mviImdb_input));

                                Toast.makeText(context, "All values Re-correction Success..!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(context, "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else {
            Toast.makeText(context, "No data in the Database..!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return secndList.size();
    }

    public class SecndDbViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout parent_LinLay;
        private final TextView tv_movieId;
        private final TextView tv_movieTitle;
        private final TextView tv_movieDetails;
        private final TextView tv_movieImdb;

        public SecndDbViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_LinLay = itemView.findViewById(R.id.parent_LinLay);
            tv_movieId = itemView.findViewById(R.id.tv_movieId);
            tv_movieTitle = itemView.findViewById(R.id.tv_movieTitle);
            tv_movieDetails = itemView.findViewById(R.id.tv_movieDetails);
            tv_movieImdb = itemView.findViewById(R.id.tv_movieImdb);

            transSecndAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            parent_LinLay.setAnimation(transSecndAnim);

            //parent_LinLay.setAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_anim));
        }
    }
}