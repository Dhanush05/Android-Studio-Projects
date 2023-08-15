package com.example.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ClassicService : Service(){
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Service", "Classic Service is created ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Classic Service is Started ")
        Log.d("Service thread", Thread.currentThread().name)
        //stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("Service", "Classic Service is Destroyed ")
        super.onDestroy()
    }

}