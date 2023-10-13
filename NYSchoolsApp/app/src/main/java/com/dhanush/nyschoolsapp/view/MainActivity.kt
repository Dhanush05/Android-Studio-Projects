package com.dhanush.nyschoolsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanush.nyschoolsapp.R
import com.dhanush.nyschoolsapp.databinding.ActivityMainBinding
import com.dhanush.nyschoolsapp.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var schoolsAdapter = SchoolsListAdapter(arrayListOf())
    lateinit var viewModel : ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.refresh()
        binding.listview.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = schoolsAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.schools.observe(this,{
            it?.let{
                schoolsAdapter.updateSchools(it)
            }
        })
    }
}