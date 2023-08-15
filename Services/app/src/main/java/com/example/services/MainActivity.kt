package com.example.services

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var startClassicService: Button
    lateinit var startJobIntentService: Button
    lateinit var stopClassicService: Button
    var br = BroadCastExample()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startClassicService = findViewById(R.id.startClassic)
        startJobIntentService = findViewById(R.id.startJobIntentService)
        stopClassicService = findViewById(R.id.stopClassic)
        startClassicService.setOnClickListener {
            val intent = Intent(this@MainActivity, ClassicService::class.java)
            startService(intent)
        }
        stopClassicService.setOnClickListener {
            val intent = Intent(this@MainActivity, ClassicService::class.java)
            stopService(intent)
        }
        startJobIntentService.setOnClickListener {
            val intent = Intent(this@MainActivity,JobIntentService::class.java)
            JobIntentService.myBackgroundService(this@MainActivity,intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        this.registerReceiver(br,filter)
    }

    override fun onStop() {
        super.onStop()
        this.unregisterReceiver(br)
    }
}