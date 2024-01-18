package com.dhanush.runningapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.dhanush.runningapp.R
import com.dhanush.runningapp.other.constants.ACTION_PAUSE_SERVICE
import com.dhanush.runningapp.other.constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.dhanush.runningapp.other.constants.ACTION_START_OR_RESUME_SERVICE
import com.dhanush.runningapp.other.constants.ACTION_STOP_SERVICE
import com.dhanush.runningapp.other.constants.NOTIFICATION_CHANNEL_ID
import com.dhanush.runningapp.other.constants.NOTIFICATION_CHANNEL_NAME
import com.dhanush.runningapp.other.constants.NOTIFICATION_ID
import com.dhanush.runningapp.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber

class TrackingService: LifecycleService() {
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
    private val serviceScope = CoroutineScope(Dispatchers.Default+ SupervisorJob())
    var isFirstRun  = true
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
                }

            }
            ACTION_PAUSE_SERVICE ->{
                Timber.d("Paused service")
            }
            ACTION_STOP_SERVICE ->{
                Timber.d("Stopped service")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun startForegroundService(){
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
        FLAG_UPDATE_CURRENT)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // Ensure the scope is cancelled when the service is destroyed
    }

}