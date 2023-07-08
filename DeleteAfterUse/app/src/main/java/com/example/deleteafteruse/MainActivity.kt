package com.example.deleteafteruse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.sql.Time


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cbox1 = findViewById<CheckBox>(R.id.checkBox)
        val cbox2 = findViewById<CheckBox>(R.id.checkBox2)
        val btn = findViewById<Button>(R.id.button)
        val radiobtn = findViewById<RadioButton>(R.id.radioButton)
        val radiogrp = findViewById<RadioGroup>(R.id.radioGroup)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val courses = arrayOf("C++","Java","Kotlin","Data Structures")
        val ad = ArrayAdapter(this,android.R.layout.simple_spinner_item,courses)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = ad
        spinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@MainActivity, "You selected: "+courses[p2], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
//        val timePicker = findViewById<TimePicker>(R.id.timePicker)
//        timePicker.setIs24HourView(true);
        val btn2 = findViewById<Button>(R.id.button2)
        btn2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
//                val frag = TimePickerFragment()
                TimePickerFragment().show(supportFragmentManager,"Pick Time now:")
            }
        })
        val btn3 = findViewById<Button>(R.id.button3)
        btn3.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                DatePickerFragment().show(supportFragmentManager,"Pick Date")
            }

        })
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.setProgress(10,true)

        btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
//                var currentTime = "Time: "+timePicker.hour+" : "+timePicker.minute
                if(cbox1.isChecked){
                    Toast.makeText(this@MainActivity,"Cheese is checked",Toast.LENGTH_SHORT).show()
                }

                if(cbox2.isChecked){
                    Toast.makeText(this@MainActivity,"Tomato is checked", Toast.LENGTH_SHORT).show()
                }
                progressBar.incrementProgressBy(10)

            }

        })
        radiogrp.setOnCheckedChangeListener { radioGroup, checked ->
            val rbtn = findViewById<RadioButton>(checked)
            Toast.makeText(this@MainActivity, "Selected: " + rbtn.text, Toast.LENGTH_SHORT).show()
        }
//        radiogrp.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
//            override fun onCheckedChanged(p0: RadioGroup?, checked: Int) {
//                val rbtn = findViewById<RadioButton>(checked)
//                Toast.makeText(this@MainActivity, "Selected: " + rbtn.text, Toast.LENGTH_SHORT).show()
//            }
//        })





    }
}