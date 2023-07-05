package com.example.implicitintent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com"))
            startActivity(intent)
        }
    }
}