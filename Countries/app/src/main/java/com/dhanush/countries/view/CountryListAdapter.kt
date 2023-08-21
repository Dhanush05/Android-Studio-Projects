package com.dhanush.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhanush.countries.R
import com.dhanush.countries.model.Country
import com.dhanush.countries.util.getProgressDrawable
import com.dhanush.countries.util.loadImage

class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {
    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById<TextView>(R.id.name)
        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val capitalName = view.findViewById<TextView>(R.id.capital)
        private val progressDrawable = getProgressDrawable(view.context)
//        private val  viewContext = view.context
        fun bind(country: Country){
//            Glide.with(viewContext).load(country.flag).into(imageView)
            countryName.text = country.countryName
            imageView.loadImage(country.flag,progressDrawable) //spinner that will show up when image is loadingx`
            capitalName.text = country.capital

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