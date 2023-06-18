package com.example.firstapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textView = findViewById<TextView>(R.id.textView)
        var btn = findViewById<Button>(R.id.button)
        var editText = findViewById<EditText>(R.id.editText)
        btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                Toast.makeText(this@MainActivity, "Hello "+editText.text, Toast.LENGTH_LONG).show()
            }
        })
    }


}