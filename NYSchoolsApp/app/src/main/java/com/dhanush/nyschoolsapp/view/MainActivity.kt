package com.dhanush.nyschoolsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanush.nyschoolsapp.databinding.ActivityMainBinding
import com.dhanush.nyschoolsapp.di.DaggerApiComponent
import com.dhanush.nyschoolsapp.model.School
import com.dhanush.nyschoolsapp.viewmodel.ListViewModel
import com.dhanush.nyschoolsapp.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var schoolsAdapter = SchoolsListAdapter(arrayListOf())
    lateinit var viewModel : ListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DaggerApiComponent.create().inject(this)
//        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]
        viewModel.refresh()
        schoolsAdapter.onItemClickListener = object : SchoolsListAdapter.OnItemClickListener {
            override fun onItemClick(school: School) {
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra("schoolName", school.schoolName)
                intent.putExtra("schoolDbn", school.dbn)
                startActivity(intent)
            }
        }
        binding.listview.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = schoolsAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.schools.observe(this) {
            it?.let {
                schoolsAdapter.updateSchools(it)
            }
        }
    }

}