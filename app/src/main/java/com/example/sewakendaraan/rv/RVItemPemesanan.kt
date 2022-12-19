package com.example.sewakendaraan.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.Pemesanan

class RVItemPemesanan (private val data:ArrayList<Pemesanan>, private val listener: OnAdapterListener) : RecyclerView.Adapter<RVItemPemesanan.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iv = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_pemesanan, parent, false), {
            listener.onUpdate(it)
        },{
            listener.onDelete(data[it])
        })
        return iv
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvId.text = "#" + currentItem.id
        holder.tvTanggal.text = currentItem.tgl_peminjaman + "-" + currentItem.tgl_pengembalian
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(itemView: View, clickAtUpdate: (Int) -> Unit, clickAtDelete: (Int) -> Unit) : RecyclerView.ViewHolder(itemView){
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
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
    fun setData(list: List<Pemesanan>?){
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
        fun onDelete(pemesanan: Pemesanan)
    }
}