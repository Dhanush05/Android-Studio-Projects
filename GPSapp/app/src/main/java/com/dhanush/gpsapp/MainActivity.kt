package com.dhanush.gpsapp

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dhanush.gpsapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest.Builder
    private val PERMISSIONS_FINE_LOCATION = 99
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationRequest  = LocationRequest.Builder(30000)
//        locationRequest.setIntervalMillis(30*1000)
        locationRequest.setMinUpdateIntervalMillis(5*1000)
        locationRequest.setPriority(PRIORITY_BALANCED_POWER_ACCURACY)
        binding.swGps.setOnClickListener{
             if(binding.swGps.isChecked){
                 locationRequest.setPriority(PRIORITY_HIGH_ACCURACY)
                 binding.tvSensor.text = "Using GPS sensor"
             }
            else{
                locationRequest.setPriority(PRIORITY_BALANCED_POWER_ACCURACY)
                 binding.tvSensor.text = "Using tower + WiFi"
             }

        }
        updateGps()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, //we assigned 99 here for fine location. can be anything
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSIONS_FINE_LOCATION->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGps()
                }else{
                    Toast.makeText(this@MainActivity,"This app requires permission to access fine location", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }

    private fun updateGps(){
        //get permission, get current location, update UI
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this@MainActivity)
        val permission = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        if(permission == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener {
                //we got permissions
                updateUI(it)
            }
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),PERMISSIONS_FINE_LOCATION)
            }
        }

    }

    private fun updateUI(it: Location?) {
        binding.tvLat.setText(it?.latitude.toString())
        binding.tvLon.setText(it?.longitude.toString())
        binding.tvAccuracy.setText(it?.accuracy.toString())
        if (it != null) {
            binding.tvAltitude.setText(it.altitude.toString())
        }else{
            binding.tvAltitude.setText("Unavailable")
        }
        binding.tvSpeed.setText(it?.speed.toString())
    }


}