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
//        var textView = findViewById<TextView>(R.id.textview)
//        textView.setTextSize(40f)
        var editTextView = findViewById<EditText>(R.id.editTextDemo)
        var textEntered = editTextView.text
        Toast.makeText(this, textEntered, Toast.LENGTH_SHORT).show()
        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                Toast.makeText(this@MainActivity, "CLicked", Toast.LENGTH_LONG).show()
            }

        })



    }


}