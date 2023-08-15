package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var count = 0
    var name:String? = null
    var msg: String? = null
    var isChecked: Boolean? = null
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        val userName = mainBinding.nameID
        val userMessage = mainBinding.summaryID
        val counter = mainBinding.button
        val remember = mainBinding.checkBox
        mainBinding.button.setOnClickListener {
            count++
            mainBinding.button.setText(""+count)

        }
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }
    fun saveData(){
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        name = mainBinding.nameID.text.toString()
        msg = mainBinding.summaryID.text.toString()
        isChecked = mainBinding.checkBox.isChecked
        val editor = sharedPreferences.edit()
        editor.putString("name",name)
        editor.putString("msg",msg )
        editor.putInt("count",count)
        editor.putBoolean("remember", isChecked!!)
        editor.apply()
        Toast.makeText(this@MainActivity,"Data is saved",Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        retreiveData()
    }
    fun retreiveData(){
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        mainBinding.nameID.setText(sharedPreferences.getString("name","Name not found"))
        mainBinding.summaryID.setText(sharedPreferences.getString("msg","no msg found"))
        mainBinding.button.setText(""+sharedPreferences.getInt("count",0))
        mainBinding.checkBox.isChecked = sharedPreferences.getBoolean("remember",false)!!
    }
}