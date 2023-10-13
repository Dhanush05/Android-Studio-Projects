package com.dhanush.nyschoolsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dhanush.nyschoolsapp.R
import com.dhanush.nyschoolsapp.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity() {
    lateinit var viewModel: DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val intent = intent
        val schoolName = intent.getStringExtra("schoolName")
        val schoolDbn = intent.getStringExtra("schoolDbn")
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

//        Toast.makeText(this@DetailsActivity,schoolName+": "+schoolDbn,Toast.LENGTH_SHORT).show()
        viewModel.fetchSchoolDetails(schoolDbn!!)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.schooldetails.observe(this){
            findViewById<TextView>(R.id.reading_value).text = it.readingScore
            Toast.makeText(this@DetailsActivity, it.mathScore
                ,Toast.LENGTH_SHORT).show()
        }
    }
}