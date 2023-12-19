package com.example.vaccinesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyRecyclerAdapter(var listData:ArrayList<VaccineModel>): RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {
    //1.data source in constructor
    //2. create view holder
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
    //3. Implement methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItem  = inflater.inflate(R.layout.my_list_item,parent,false)
        val viewHolder = MyViewHolder(listItem)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myListData = listData.get(position)
        holder.textView.text = myListData.title
        holder.imageView.setImageResource(myListData.image)
    }


}