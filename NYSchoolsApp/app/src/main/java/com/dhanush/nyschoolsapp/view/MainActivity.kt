package com.dhanush.nyschoolsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhanush.nyschoolsapp.R
import com.dhanush.nyschoolsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var schoolsAdapter = SchoolsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}