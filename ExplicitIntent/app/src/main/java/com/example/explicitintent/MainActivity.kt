package com.example.explicitintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                var i:Intent = Intent(applicationContext,Activity2::class.java)
                startActivity(i)
            }
        })

    }
}