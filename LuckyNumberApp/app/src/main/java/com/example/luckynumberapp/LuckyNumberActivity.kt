package com.example.luckynumberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class LuckyNumberActivity : AppCompatActivity() {
    var userName:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_number)
        val btn = findViewById<Button>(R.id.share_button)
        val textView = findViewById<TextView>(R.id.lucky_number)
        userName = intent.getStringExtra("name")
        val random:Int = generateRandomNumber()
        textView.setText(""+random)
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                shareData(userName, random);
            }

        })
    }

    private fun shareData(userName: String?, random: Int) {
        //implicit intents
        intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT,userName+" got lucky today")
        intent.putExtra(Intent.EXTRA_TEXT, "His lucky number is "+random.toString())
        startActivity(Intent.createChooser(intent,"Choose a platform"))
    }

    fun generateRandomNumber(): Int {
        val upperLimit = 1000
        var randomNumber: Int = Random().nextInt(upperLimit)
        return randomNumber
    }

}