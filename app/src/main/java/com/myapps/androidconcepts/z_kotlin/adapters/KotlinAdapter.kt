package com.myapps.androidconcepts.z_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.myapps.androidconcepts.R
import com.myapps.androidconcepts.z_kotlin.models.KotlinModel

class KotlinAdapter(private val modelList: List<KotlinModel>, private val context: Context) : RecyclerView.Adapter<KotlinAdapter.KotlinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotlinViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rowitem_alltype_retro, parent, false)
        val kotlinViewHolder: KotlinViewHolder = KotlinViewHolder(view)
        return kotlinViewHolder
    }

    override fun onBindViewHolder(holder: KotlinViewHolder, position: Int) {

        //not working both below 2 lines to change background color. Getting errors.
        //holder.parentLay.setBackgroundColor(Color.parseColor(R.color.skyBlueColor.toString()))
        //holder.parentLay.setBackgroundColor(ContextCompat.getColor(context, R.color.skyBlueColor)

        holder.tvId.text = modelList.get(holder.adapterPosition).id.toString()
        //holder.tvId.text = "${modelList[position].id}"
        holder.tvSetId.text = "${modelList[position].userId}"
        holder.tvTitle.text = modelList[position].title
        holder.tvBody.text = modelList[position].body
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    class KotlinViewHolder(itemView: View) : ViewHolder(itemView) {
        val parentLay: RelativeLayout = itemView.findViewById(R.id.rowItem_ParentLay)
        val tvId: TextView = itemView.findViewById(R.id.tv_allTypeRetro_Id)
        val tvSetId: TextView = itemView.findViewById(R.id.tv_allTypeRetro_SetId)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_allTypeRetro_Title)
        val tvBody: TextView = itemView.findViewById(R.id.tv_allTypeRetro_Description)

    }

}