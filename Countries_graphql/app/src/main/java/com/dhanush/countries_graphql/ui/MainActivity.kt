package com.dhanush.countries_graphql.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.countries_graphql.R
import com.dhanush.countries_graphql.databinding.ActivityMainBinding
import com.dhanush.countries_graphql.presentation.CountriesViewModel
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel  : CountriesViewModel
    private lateinit var adapter: CountriesAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel  = ViewModelProvider(this).get(CountriesViewModel::class.java)
        viewModel.state.onEach { state->
            adapter = CountriesAdapter(state.countries)
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.adapter = adapter
        }
    }
}