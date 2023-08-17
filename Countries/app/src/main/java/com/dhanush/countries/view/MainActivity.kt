package com.dhanush.countries.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.countries.R
import com.dhanush.countries.databinding.ActivityMainBinding
import com.dhanush.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countriesAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries->
            countries?.let {
                countriesAdapter.updateCountries(it)
            }
        })
        viewModel.countryLoadError.observe(this, Observer{isError->
            isError?.let{binding.listError.visibility = if(it) View.VISIBLE else View.GONE}
        })
        viewModel.loading.observe(this, Observer{isLoading->
            isLoading?.let{
                binding.loadingView.visibility= if(it) View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        })

    }
}