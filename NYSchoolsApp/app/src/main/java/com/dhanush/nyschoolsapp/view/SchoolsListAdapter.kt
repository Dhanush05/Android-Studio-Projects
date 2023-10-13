package com.dhanush.nyschoolsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.nyschoolsapp.R
import com.dhanush.nyschoolsapp.databinding.ListItemBinding
import com.dhanush.nyschoolsapp.model.School

class SchoolsListAdapter(var schools:ArrayList<School>) : RecyclerView.Adapter<SchoolsListAdapter.SchoolViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(school: School)
    }

    var onItemClickListener: OnItemClickListener? = null
    fun updateSchools(newSchools: List<School>){
        schools.clear()
        schools.addAll(newSchools)
        notifyDataSetChanged()
    }
    class SchoolViewHolder(v: View, val onItemClickListener: OnItemClickListener): RecyclerView.ViewHolder(v){
        fun bind(school: School) {
            binding.schoolname.text = school.schoolName
            binding.dbnnumber.text = school.dbn
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(school)
            }
        }

        private var binding  = ListItemBinding.bind(v)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  SchoolViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false),onItemClickListener!!
    )

    override fun getItemCount(): Int {
        return schools.size
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(schools[position])
    }
}