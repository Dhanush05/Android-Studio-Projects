package com.dhanush.nyschoolsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dhanush.nyschoolsapp.R
import com.dhanush.nyschoolsapp.databinding.ActivityDetailsBinding
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import com.dhanush.nyschoolsapp.viewmodel.DetailsViewModel
import com.dhanush.nyschoolsapp.viewmodel.ViewModelFactory
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {
    lateinit var viewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    lateinit var title:String
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DaggerApiComponent.create().inject(this)
        val intent = intent
        val schoolName = intent.getStringExtra("schoolName")
        title = schoolName!!
        val schoolDbn = intent.getStringExtra("schoolDbn")
        binding.title.text = title
//        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel = ViewModelProvider(this,viewModelFactory)[DetailsViewModel::class.java]
//        Toast.makeText(this@DetailsActivity,schoolName+": "+schoolDbn,Toast.LENGTH_SHORT).show()
        viewModel.fetchSchoolDetails(schoolDbn!!)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.schooldetails.observe(this){
            it?.let {
                binding.mathScore.visibility = View.VISIBLE
                binding.mathScore.text = it.mathScore
                binding.readingScore.visibility = View.VISIBLE
                binding.readingScore.text = it.readingScore
                binding.writingScore.apply {
                    visibility = View.VISIBLE
                    text = it.writingScore
                }

            }
        }
        viewModel.loadError.observe(this){
            if(it){
                Toast.makeText(this@DetailsActivity,"Load error - Retry",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.noData.observe(this){
            if(it){
                Toast.makeText(this@DetailsActivity,"No data available for "+title,Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}