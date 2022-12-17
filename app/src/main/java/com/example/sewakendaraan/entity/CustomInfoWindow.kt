package com.example.sewakendaraan.entity

import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.Cabang
import kotlinx.android.synthetic.main.layout_tooltip.view.*
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(mapView: MapView?): InfoWindow(R.layout.layout_tooltip, mapView) {
    override fun onClose() {

    }

    override fun onOpen(item: Any) {
        val marker = item as Marker
        val infoWindowData = marker.relatedObject as Cabang
        val tvNamaLokasi = mView.tvNamaLokasi
        val tvAlamat = mView.tvAlamat
        val imageClose = mView.imageClose

        tvNamaLokasi.text = infoWindowData.nama
        tvAlamat.text = infoWindowData.vicinity
        imageClose.setOnClickListener{
            marker.closeInfoWindow()
        }
    }
}