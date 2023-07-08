package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Runnable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var playButton:Button; lateinit var pauseButton:Button; lateinit var forwardButton:Button; lateinit var rewindButton:Button
    lateinit var time_text:TextView; lateinit var title:TextView
    lateinit var seekBar: SeekBar
    lateinit var mediaPlayer:MediaPlayer

    var startTime:Double = 0.0
    var finalTime:Double = 0.0
    var forwardTime = 10000
    var rewindTime = 10000
    var oneTimeOnly=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val songTitle = findViewById<TextView>(R.id.song_title)

        playButton=findViewById(R.id.playButton)
        pauseButton=findViewById(R.id.pauseButton)
        forwardButton = findViewById(R.id.forwardButton)
        rewindButton = findViewById(R.id.rewindButton)
        time_text = findViewById(R.id.timeLeft)
        title = findViewById(R.id.textView)
        seekBar = findViewById(R.id.seekBar)
        mediaPlayer = MediaPlayer.create(this,R.raw.interstellar)
        seekBar.isClickable=false
        songTitle.setText(resources.getIdentifier("interstellar","raw",packageName))
        //button functionalities
        playButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                PlayMusic()
            }
        })
        pauseButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                mediaPlayer.pause()
            }
        })

        forwardButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                if(startTime+forwardTime<=finalTime){
                    startTime+=forwardTime
                    mediaPlayer.seekTo(startTime.toInt())
                }
                else{
                    Toast.makeText(this@MainActivity,"Can't forward",Toast.LENGTH_SHORT).show()
                }
            }
        })
        rewindButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                if(startTime-rewindTime>0){
                    startTime-=forwardTime
                    mediaPlayer.seekTo(startTime.toInt())
                }
                else{
                    startTime = 0.0
                    mediaPlayer.seekTo(startTime.toInt())
                    Toast.makeText(this@MainActivity,"Can't rewind completely",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
//    var handler=Handler()
    //deprecated constructor

    private fun PlayMusic() {
        mediaPlayer.start();
        finalTime = mediaPlayer.duration.toDouble()
        startTime = mediaPlayer.currentPosition.toDouble()
        if(oneTimeOnly==0){
            seekBar.max = finalTime.toInt()
            oneTimeOnly =1
        }
        time_text.text = ""+TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())+" min, "+(TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong())-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())))+"sec"
        seekBar.progress = startTime.toInt()
        Handler(Looper.getMainLooper()).postDelayed(UpdateTime,100)
        //handler.postDelayed(UpdateTime,100)

    }
    //create the runnable
    var UpdateTime = object:Runnable{
        override fun run() {
            startTime = mediaPlayer.currentPosition.toDouble()
            time_text.text = ""+TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())+"min, "+(TimeUnit.MILLISECONDS.toSeconds(startTime.toLong())-
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())))+"sec"
            seekBar.progress = startTime.toInt()
            Handler(Looper.getMainLooper()).postDelayed(this,100)
//            handler.postDelayed(this, 100)

        }
    }




}