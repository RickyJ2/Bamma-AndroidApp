package com.example.sewakendaraan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.entity.SettingItem

class RVItemSetting(private val data:Array<SettingItem>,
                    private val clickListener: (SettingItem) -> Unit) : RecyclerView.Adapter<RVItemSetting.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iv = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_setting, parent, false)){
            clickListener(data[it])
        }
        return iv
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.txtItem.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(itemView: View, clickAtPosition: (Int)-> Unit): RecyclerView.ViewHolder(itemView){
        val txtItem: TextView = itemView.findViewById(R.id.txtItem)

        init{
            itemView.setOnClickListener{
                clickAtPosition(adapterPosition)
            }
        }
    }
}