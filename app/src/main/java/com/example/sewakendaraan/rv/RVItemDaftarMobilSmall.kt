package com.example.sewakendaraan.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sewakendaraan.R
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.data.DaftarMobil

class RVItemDaftarMobilSmall(private val data: ArrayList<DaftarMobil>,
                             private val clickListener: (DaftarMobil) -> Unit): RecyclerView.Adapter<RVItemDaftarMobilSmall.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iv = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_kendaraan_small, parent, false)){
            clickListener(data[it])
        }
        return iv
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvModel.text = currentItem.model
        "${currentItem.tipe}-${currentItem.kapasitas}".also { holder.tvTipeKapasitas.text = it }
        "Rp ${currentItem.harga}".also { holder.tvHarga.text = it }
        Glide.with(holder.gambarMobil)
            .load(RClient.imageBaseUrl() + currentItem.image)
            .into(holder.gambarMobil)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder( itemView: View, clickAtPosition: (Int)-> Unit) : RecyclerView.ViewHolder(itemView){
        val gambarMobil : ImageView = itemView.findViewById(R.id.gambarMobil)
        val tvModel: TextView = itemView.findViewById(R.id.tvModel)
        val tvTipeKapasitas : TextView = itemView.findViewById(R.id.tvTipeKapasitas)
        val tvHarga : TextView = itemView.findViewById(R.id.tvHarga)
        init {
            itemView.setOnClickListener{
                clickAtPosition(adapterPosition)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DaftarMobil>?){
        if(list == null){
            data.clear()
        }else{
            data.clear()
            data.addAll(list)
        }
        notifyDataSetChanged()
    }
}