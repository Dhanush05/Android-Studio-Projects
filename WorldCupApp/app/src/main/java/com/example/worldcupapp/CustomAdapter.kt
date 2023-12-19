package com.example.worldcupapp

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(var data:List<CountryModelClass>, private val context:Context) : ArrayAdapter<CountryModelClass>(context,R.layout.my_list_item,data) {
    class ViewHolder{
        lateinit var txtCountry: TextView
        lateinit var txtCupWins: TextView
        lateinit var flagImg:ImageView
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val dataModel = getItem(position)
        var viewHolder: ViewHolder
        var convertView = convertView
        val result:View
        if(convertView == null){
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.my_list_item,parent,false)
            viewHolder.txtCountry = convertView.findViewById<TextView>(R.id.country_name)
            viewHolder.txtCupWins = convertView.findViewById<TextView>(R.id.win_count)
            viewHolder.flagImg = convertView.findViewById<ImageView>(R.id.imageView)
            result = convertView
            convertView.setTag(viewHolder)
        }
        else{
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        dataModel?.let {
            viewHolder.txtCountry.text = dataModel.country_name
            viewHolder.txtCupWins.text = dataModel.cup_win_count
            viewHolder.flagImg.setImageResource(dataModel.flag_img)
        }
        return result!!
    }
}