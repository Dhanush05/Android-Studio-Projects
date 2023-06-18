package com.example.firstapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var counterVal = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.button)
        var textview = findViewById<TextView>(R.id.textView)
        btn.setOnClickListener (object: View.OnClickListener{
            override fun onClick(view: View?) {
                Toast.makeText(this@MainActivity," HEllo CLikcked",Toast.LENGTH_SHORT).show()
                textview.text = ""+increaseCounter()
            }
        } )
    }
    fun increaseCounter(): Int {
        return ++counterVal
    }

}