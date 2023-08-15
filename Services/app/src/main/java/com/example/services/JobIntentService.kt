package com.example.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class JobIntentService: JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        Log.d("Service","Job intent service is started")
        Log.d("Service thread", Thread.currentThread().name)
    }

    companion object{
        fun myBackgroundService(context: Context,intent: Intent){
            enqueueWork(context,com.example.services.JobIntentService::class.java,193,intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Service","Job Intent service is destroyed")
    }

}