package com.example.videoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoView = findViewById(R.id.videoView)
//        /Users/dhanush/Documents/Udemy/Android Studio Projects/VideoPlayer/app/src/main/res/raw/testvideo.mp4
        videoView.setVideoPath("android.resource://"+packageName+"/"+R.raw.mountains)
        var mediaController:MediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
//        videoView.requestFocus()
        //videoView.start() //automatically starts
        val video2 = findViewById<VideoView>(R.id.videoView2)
        val Uri = Uri.parse("https://static.videezy.com/system/resources/previews/000/006/879/original/Lab38.mp4")
        video2.setVideoURI(Uri)
        val mc2 = MediaController(this)
        mc2.setAnchorView(video2)
        video2.setMediaController(mc2)
        video2.start()
    }
}