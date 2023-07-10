package com.example.frenchteacher

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun sayTheColor(view: View){
        var btn_clicked = view as Button
        val mediaController = MediaPlayer.create(this, resources.getIdentifier(btn_clicked.tag.toString(),"raw",packageName))
        mediaController.start()
    }
}