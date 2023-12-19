package com.example.vaccinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class MainActivity : AppCompatActivity() {
    //1. adapter view: recycler view
    lateinit var recyclerView:RecyclerView
    //2.data source
    var myListData =  ArrayList<VaccineModel>()
    //3. create adapter
    lateinit var adapter:MyRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        myListData.add(VaccineModel("Hepatitis B Vaccine", R.drawable.vaccine1))
        myListData.add(VaccineModel("Tetanus Vaccine", R.drawable.vaccine4))
        myListData.add(VaccineModel("Pneumococcal Vaccine", R.drawable.vaccine5))
        myListData.add(VaccineModel("Rotavirus Vaccine", R.drawable.vaccine6))
        myListData.add(VaccineModel("Measles Vaccine", R.drawable.vaccine7))
        myListData.add(VaccineModel("Cholera Vaccine", R.drawable.vaccine8))
        myListData.add(VaccineModel("Covid-19 Vaccine", R.drawable.vaccine9))

        adapter = MyRecyclerAdapter(myListData)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

}