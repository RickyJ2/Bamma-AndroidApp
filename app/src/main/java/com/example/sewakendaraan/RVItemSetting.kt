package com.example.sewakendaraan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.entity.SettingItem

class RVItemSetting(private val data:Array<SettingItem>) : RecyclerView.Adapter<RVItemSetting.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_setting, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.imgIcon.setImageResource(currentItem.iconId)
        holder.txtItem.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        val txtItem: TextView = itemView.findViewById(R.id.txtItem)
    }
}