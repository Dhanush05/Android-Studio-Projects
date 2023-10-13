package com.dhanush.nyschoolsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dhanush.nyschoolsapp.R

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val intent = intent
        val schoolName = intent.getStringExtra("schoolName")
        val schoolDbn = intent.getStringExtra("schoolDbn")
        Toast.makeText(this@DetailsActivity,schoolName+": "+schoolDbn,Toast.LENGTH_SHORT).show()

    }
}