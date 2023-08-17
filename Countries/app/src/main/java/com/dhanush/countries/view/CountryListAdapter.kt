package com.dhanush.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.countries.R
import com.dhanush.countries.data.Country

class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {
    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById<TextView>(R.id.name)
        fun bind(country: Country){
            countryName.text = country.countryName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CountryViewHolder (
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_country, parent, false)
    )

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}