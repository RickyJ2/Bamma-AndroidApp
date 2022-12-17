@file:Suppress("DEPRECATION")

package com.example.sewakendaraan.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.entity.CustomInfoWindow
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.Cabang
import com.example.sewakendaraan.viewModel.TentangKamiViewModel
import kotlinx.android.synthetic.main.activity_tentang_kami.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem

class TentangKamiActivity : AppCompatActivity() {
    private lateinit var mTentangKamiViewModel: TentangKamiViewModel
    private lateinit var mapController: MapController
    private lateinit var overlayItem: ArrayList<OverlayItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_kami)

        mTentangKamiViewModel = ViewModelProvider(this)[TentangKamiViewModel::class.java]

        //Observe Msg
        mTentangKamiViewModel.msg.observe(this@TentangKamiActivity){
            if(it != ""){
                Toast.makeText(this@TentangKamiActivity, it, Toast.LENGTH_SHORT).show()
            }
        }

        Configuration.getInstance().load(this@TentangKamiActivity, PreferenceManager.getDefaultSharedPreferences(this@TentangKamiActivity))
        val geoPoint = GeoPoint(-7.78165, 110.414497)

        mapView.setMultiTouchControls(true)
        mapView.controller.animateTo(geoPoint)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController = mapView.controller as MapController
        mapController.setCenter(geoPoint)
        mapController.zoomTo(15)

        mTentangKamiViewModel.setProgressBar(View.VISIBLE)
        mTentangKamiViewModel.getCabang()
        mTentangKamiViewModel.code.observe(this@TentangKamiActivity){
            if(it == 200){
                initMarker(mTentangKamiViewModel.cabangList.value!!)
            }
            if(it != null){
                mTentangKamiViewModel.setProgressBar(View.INVISIBLE)
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initMarker(CabangList: List<Cabang>){
        for(i in CabangList.indices){
            overlayItem = ArrayList()
            overlayItem.add(
                OverlayItem(
                    CabangList[i].nama, CabangList[i].vicinity, GeoPoint(
                        CabangList[i].lat, CabangList[i].lng
                    )
                )
            )
            val info = Cabang( 0, CabangList[i].nama, CabangList[i].vicinity, 0.0, 0.0)

            val marker = Marker(mapView)
            marker.icon = resources.getDrawable(R.drawable.ic_baseline_location_on_24)
            marker.position = GeoPoint(CabangList[i].lat, CabangList[i].lng)
            marker.relatedObject = info
            marker.infoWindow = CustomInfoWindow(mapView)
            marker.setOnMarkerClickListener{ item, _ ->
                item.showInfoWindow()
                true
            }
            mapView.overlays.add(marker)
            mapView.invalidate()
        }
    }
}