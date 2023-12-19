package com.example.vaccinesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //1. data source
    private ArrayList<VaccineModel> listData;
    public MyAdapter(ArrayList<VaccineModel> listData) {
        this.listData = listData;
    }
    //2. view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
        }
    }
    //3.implement the methods
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.my_list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final VaccineModel myListData = listData.get(position);
        holder.textView.setText(myListData.getTitle());
        holder.imageView.setImageResource(listData.get(position).getImage());
    }

    @Override
    public int getItemCount() {

        return listData.size();
    }
}
