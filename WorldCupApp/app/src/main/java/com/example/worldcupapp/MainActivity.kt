package com.example.worldcupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var listView:ListView
    private lateinit var adapter: CustomAdapter
    var dataModels = ArrayList<CountryModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        dataModels.add(CountryModelClass("brazil", "5", R.drawable.brazil))
        dataModels.add(CountryModelClass("Germany", "4", R.drawable.germany))
        dataModels.add(CountryModelClass("France", "2", R.drawable.france))
        dataModels.add(CountryModelClass("Spain", "1", R.drawable.spain))
        dataModels.add(CountryModelClass("England", "1", R.drawable.unitedkingdom))
        dataModels.add(CountryModelClass("United States", "0", R.drawable.unitedstates))
        dataModels.add(CountryModelClass("Saudi Arabia", "0", R.drawable.saudiarabia))

        adapter = CustomAdapter(dataModels,applicationContext)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position, _ ->
            val message = "Country: ${adapter.getItem(position)?.country_name} \n World Cup wins: ${adapter.getItem(position)?.cup_win_count}"
            Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
        }
//        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                // Your code here to handle item click event
//            }
//        })

    }
}