package com.example.luckynumberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var name:String = editText.text.toString()
                intent = Intent(applicationContext, LuckyNumberActivity::class.java)
                intent.putExtra("name",name)
                //Toast.makeText(this@MainActivity,"msg: "+name,Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        })
    }
}