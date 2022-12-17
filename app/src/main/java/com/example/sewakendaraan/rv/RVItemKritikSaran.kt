package com.example.sewakendaraan.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.KritikSaran

class RVItemKritikSaran(private val data:ArrayList<KritikSaran>, private val listener: OnAdapterListener) : RecyclerView.Adapter<RVItemKritikSaran.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iv = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_kritiksaran, parent, false), {
            listener.onUpdate(it)
        },{
            listener.onDelete(data[it])
        })
        return iv
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvContent.text = currentItem.content
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder( itemView: View, clickAtUpdate: (Int) -> Unit, clickAtDelete: (Int) -> Unit) : RecyclerView.ViewHolder(itemView){
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        init {
            itemView.findViewById<ImageView>(R.id.iconEdit).setOnClickListener{
                clickAtUpdate(adapterPosition)
            }
            itemView.findViewById<ImageView>(R.id.iconDelete).setOnClickListener{
                clickAtDelete(adapterPosition)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<KritikSaran>?){
        if(list == null){
            data.clear()
        }else{
            data.clear()
            data.addAll(list)
        }
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onUpdate(index: Int)
        fun onDelete(kritikSaran: KritikSaran)
    }
}