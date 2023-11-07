 package com.dhanush.cloudservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.widget.ImageButton
import android.widget.PopupMenu
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

 class MainActivity : AppCompatActivity( ), OnMapReadyCallback {
     private var gmap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val mapOptions: ImageButton = findViewById(R.id.optionsMenu)
        val popupMenu = PopupMenu(this,mapOptions)
        popupMenu.menuInflater.inflate(R.menu.map_options,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            changeMap(it.itemId)
            true
        }
    }

     private fun changeMap(itemId: Int) {

     }


     override fun onMapReady(map: GoogleMap) {
         gmap = map
     }
 }