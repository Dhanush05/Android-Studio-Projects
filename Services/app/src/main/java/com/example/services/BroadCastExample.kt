package com.example.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastExample: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val airplaneMode: Boolean = p1!!.getBooleanExtra("state",false)
        if(airplaneMode){
            Toast.makeText(p0,"Airplane mode is switched on",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(p0,"Device is not in airplane mode",Toast.LENGTH_LONG).show()
        }

    }
}