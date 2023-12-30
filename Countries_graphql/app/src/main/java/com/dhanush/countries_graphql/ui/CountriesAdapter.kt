package com.dhanush.countries_graphql.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.countries_graphql.databinding.CountryListItemBinding
import com.dhanush.countries_graphql.domain.SimpleCountry

class CountriesAdapter(private val countries: List<SimpleCountry>): RecyclerView.Adapter<CountriesAdapter.countryViewModel>(){
    class countryViewModel(private val binding: CountryListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(country: SimpleCountry){
            binding.countryName.text = country.name
            binding.countryCode.text = country.code
            binding.capital.text = country.capital
            binding.countryEmoji.text = country.emoji
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): countryViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater,parent, false)
        return countryViewModel(binding)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: countryViewModel, position: Int) {
        holder.bind(countries[position])
    }
}