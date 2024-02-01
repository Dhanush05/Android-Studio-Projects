package com.dhanush.runningapp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dhanush.runningapp.R
import com.dhanush.runningapp.other.TrackingUtility
import com.dhanush.runningapp.other.constants.ACTION_PAUSE_SERVICE
import com.dhanush.runningapp.other.constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.dhanush.runningapp.other.constants.ACTION_START_OR_RESUME_SERVICE
import com.dhanush.runningapp.other.constants.ACTION_STOP_SERVICE
import com.dhanush.runningapp.other.constants.NOTIFICATION_CHANNEL_ID
import com.dhanush.runningapp.other.constants.NOTIFICATION_CHANNEL_NAME
import com.dhanush.runningapp.other.constants.NOTIFICATION_ID
import com.dhanush.runningapp.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber
class TrackingService: LifecycleService() {

    private val serviceScope = CoroutineScope(Dispatchers.Default+ SupervisorJob())
    var isFirstRun  = true
    //todo:" fused location provider "
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<Polylines>()
    }

    private fun postInitialValues(){
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
    }
    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        isTracking.observe(this, Observer {
            updateLocationTracking(it)
        })
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START_OR_RESUME_SERVICE ->{
                if(isFirstRun){
                    Toast.makeText(this,"Started or resumed service",Toast.LENGTH_SHORT).show()
                    startForegroundService()
                    isFirstRun = false
                }
                else{
                    Toast.makeText(this," Resuming service", Toast.LENGTH_SHORT).show()
                    startForegroundService()
                }

            }
            ACTION_PAUSE_SERVICE ->{
                Timber.d("Paused service")
                Toast.makeText(this, "Paused service", Toast.LENGTH_SHORT).show()
            }
            ACTION_STOP_SERVICE ->{
                Timber.d("Stopped service")
                Toast.makeText(this,"Stopped service", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun startForegroundService(){
        //add empty lines before starting tracking
        addEmptyPolylines()
        isTracking.postValue(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running App")
            .setContentText("00:00:00") //initial time will be updated later
            .setContentIntent(getMainActivityPendingIntent())
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }
    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java)
            .also
            {
                it.action = ACTION_SHOW_TRACKING_FRAGMENT
            },
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE )
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun addEmptyPolylines() = pathPoints.value?.apply{
        add(mutableListOf())
        pathPoints.postValue(this)
    }?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private fun addPathPoints(location: Location?){
        location?.let{
            val pos = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply{
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    val locationCallBack = object: LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if(isTracking.value!!){
                result?.locations?.let{
                    for(loc in it){
                        addPathPoints(loc)
                        Timber.d("Latitude = " + loc.latitude + ", Longitude = " + loc.longitude)
                    }
                }
            }
            else{
                //todo "implement else when tracking is off"

            }
        }

    }

    //function to update location tracking when switching between isTracking true or false.
    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean){
        //todo "implement this function using tracking utility permission check and LocationRequest()
        if(isTracking){
            if(TrackingUtility.hasLocationPermissions(this)) {
                val request = LocationRequest.Builder(5000L)
                    .setMinUpdateIntervalMillis(2000L)
                    .setPriority(PRIORITY_HIGH_ACCURACY)
                    .build()
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallBack,
                    Looper.getMainLooper()
                )
            }
        }else{
            fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // Ensure the scope is cancelled when the service is destroyed
    }



}

typealias PolyLine = MutableList<LatLng>
typealias Polylines = MutableList<PolyLine>